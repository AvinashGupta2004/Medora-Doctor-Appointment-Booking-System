package com.medora.QUEUE.processing.services.queue.facade.management.impl;

import com.medora.DOCTOR.persistence.entity.DoctorJpaEntity;
import com.medora.DOCTOR.persistence.entity.DoctorScheduleJpaEntity;
import com.medora.QUEUE.common.enums.QueueStatus;
import com.medora.QUEUE.common.exceptions.BookingNotAllowedException;
import com.medora.QUEUE.persistence.entity.DoctorShiftQueueJpaEntity;
import com.medora.QUEUE.processing.models.DoctorShiftQueue;
import com.medora.QUEUE.processing.services.queue.facade.management.DoctorShiftQueueManagementService;
import com.medora.common.models.ContactInformation;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
class DoctorShiftQueueConcurrencyIntegrationTest {

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    private DoctorShiftQueueManagementService queueManagementService;

    @Autowired
    private EntityManager entityManager;

    private TransactionTemplate transactionTemplate;

    private final int maxCapacity = 10;
    private final int initialBookedTokens = 9;

    private final Long doctorRegistrationNumber = 79655023970L;
    private final LocalDate shiftDate = LocalDate.of(2026, 10, 14);
    private final LocalTime startTime = LocalTime.of(8, 0);

    @BeforeEach
    void setUp() {
        transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.executeWithoutResult(status -> {

            entityManager.createQuery("""
            delete from DoctorShiftQueueJpaEntity q
            where q.doctor.doctorRegistrationNumber = :registration
            """)
                    .setParameter("registration", doctorRegistrationNumber)
                    .executeUpdate();

            entityManager.createQuery("""
            delete from DoctorScheduleJpaEntity s
            where s.doctor.doctorRegistrationNumber = :registration
            """)
                    .setParameter("registration", doctorRegistrationNumber)
                    .executeUpdate();

            entityManager.createQuery("""
            delete from DoctorJpaEntity d
            where d.doctorRegistrationNumber = :registration
            """)
                    .setParameter("registration", doctorRegistrationNumber)
                    .executeUpdate();

            // Existing doctor, schedule aur queue creation yahin continue hogi.
        });
    }

    @Test
    void allocateToken_shouldNotExceedCapacity_underConcurrentRequests()
            throws Exception {

        int numberOfRequests = 20;

        ExecutorService executor =
                Executors.newFixedThreadPool(numberOfRequests);

        CountDownLatch ready = new CountDownLatch(numberOfRequests);
        CountDownLatch start = new CountDownLatch(1);

        List<Future<Integer>> futures = new ArrayList<>();

        try {
            for (int i = 0; i < numberOfRequests; i++) {
                futures.add(executor.submit(() -> {
                    ready.countDown();
                    start.await();

                    DoctorShiftQueue request = DoctorShiftQueue.builder()
                            .doctorRegistrationNumber(doctorRegistrationNumber)
                            .shiftDate(shiftDate)
                            .startTime(startTime)
                            .build();

                    return queueManagementService.allocateToken(request);
                }));
            }

            assertTrue(
                    ready.await(10, TimeUnit.SECONDS),
                    "Worker threads did not become ready in time"
            );

            start.countDown();

            int successfulAllocations = 0;
            int rejectedRequests = 0;
            List<Throwable> unexpectedErrors = new ArrayList<>();

            for (Future<Integer> future : futures) {
                try {
                    Integer token = future.get(20, TimeUnit.SECONDS);

                    assertEquals(10, token);
                    successfulAllocations++;

                } catch (ExecutionException exception) {
                    Throwable cause = exception.getCause();

                    if (cause instanceof BookingNotAllowedException) {
                        rejectedRequests++;
                    } else {
                        unexpectedErrors.add(cause);
                    }
                }
            }

            assertTrue(
                    unexpectedErrors.isEmpty(),
                    "Unexpected errors: " + unexpectedErrors
            );

            assertEquals(
                    1,
                    successfulAllocations,
                    "Only one request should receive the final token"
            );

            assertEquals(
                    numberOfRequests - 1,
                    rejectedRequests,
                    "All other requests should be rejected"
            );

        } finally {
            start.countDown();
            executor.shutdownNow();
        }

        // Read the final database state in a separate transaction.
        Object[] finalState = transactionTemplate.execute(status ->
                entityManager.createQuery("""
                        select q.bookedTokens, q.status
                        from DoctorShiftQueueJpaEntity q
                        where q.doctor.doctorRegistrationNumber = :registration
                          and q.shiftDate = :shiftDate
                          and q.startTime = :startTime
                        """, Object[].class)
                        .setParameter("registration", doctorRegistrationNumber)
                        .setParameter("shiftDate", shiftDate)
                        .setParameter("startTime", startTime)
                        .getSingleResult()
        );

        assertNotNull(finalState);
        assertEquals(maxCapacity, finalState[0]);
        assertEquals(QueueStatus.FULL, finalState[1]);
    }
}