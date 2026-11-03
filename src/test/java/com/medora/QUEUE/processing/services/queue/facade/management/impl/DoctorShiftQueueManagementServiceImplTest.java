package com.medora.QUEUE.processing.services.queue.facade.management.impl;

import com.medora.DOCTOR.common.mappers.DoctorMapper;
import com.medora.DOCTOR.persistence.repository.DoctorRepository;
import com.medora.QUEUE.common.enums.QueueStatus;
import com.medora.QUEUE.common.exceptions.BookingNotAllowedException;
import com.medora.QUEUE.common.exceptions.MaxBookingsReachedException;
import com.medora.QUEUE.persistence.entity.DoctorShiftQueueJpaEntity;
import com.medora.QUEUE.persistence.repository.DoctorShiftQueueRepository;
import com.medora.QUEUE.processing.models.DoctorShiftQueue;
import com.medora.QUEUE.processing.services.queue.facade.query.DoctorShiftQueueQueryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DoctorShiftQueueManagementServiceImplTest {

    @Mock
    private DoctorShiftQueueQueryService shiftQueueQueryService;

    @Mock
    private DoctorShiftQueueRepository doctorShiftQueueRepository;

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private DoctorMapper doctorMapper;

    @InjectMocks
    private DoctorShiftQueueManagementServiceImpl service;

    private DoctorShiftQueue requestedQueue;
    private DoctorShiftQueue availableQueue;
    private DoctorShiftQueueJpaEntity queueEntity;

    @BeforeEach
    void setUp() {
        requestedQueue = DoctorShiftQueue.builder()
                .doctorRegistrationNumber(12345L)
                .build();

        availableQueue = DoctorShiftQueue.builder()
                .id(100L)
                .maxCapacity(10)
                .totalActiveBookings(5)
                .status(QueueStatus.OPEN)
                .build();

        queueEntity = DoctorShiftQueueJpaEntity.builder()
                .id(100L)
                .maxCapacity(10)
                .totalActiveBookings(5)
                .status(QueueStatus.OPEN)
                .build();
    }

    @Test
    void shouldAllocateNextTokenWhenQueueHasCapacity() {
        when(shiftQueueQueryService.checkAvailability(requestedQueue))
                .thenReturn(availableQueue);

        when(doctorShiftQueueRepository
                .findByIdWithPessimisticLock(100L))
                .thenReturn(Optional.of(queueEntity));

        when(doctorShiftQueueRepository.save(queueEntity))
                .thenReturn(queueEntity);

        Integer token = service.allocateToken(requestedQueue);

        assertEquals(6, token);
        assertEquals(6, queueEntity.getTotalActiveBookings());
        assertEquals(QueueStatus.OPEN, queueEntity.getStatus());

        verify(doctorShiftQueueRepository)
                .findByIdWithPessimisticLock(100L);

        verify(doctorShiftQueueRepository).save(queueEntity);
    }

    @Test
    void shouldMarkQueueFullWhenLastTokenIsAllocated() {
        queueEntity.setTotalActiveBookings(9);
        queueEntity.setMaxCapacity(10);

        when(shiftQueueQueryService.checkAvailability(requestedQueue))
                .thenReturn(availableQueue);

        when(doctorShiftQueueRepository
                .findByIdWithPessimisticLock(100L))
                .thenReturn(Optional.of(queueEntity));

        when(doctorShiftQueueRepository.save(queueEntity))
                .thenReturn(queueEntity);

        Integer token = service.allocateToken(requestedQueue);

        assertEquals(10, token);
        assertEquals(10, queueEntity.getTotalActiveBookings());
        assertEquals(QueueStatus.FULL, queueEntity.getStatus());

        verify(doctorShiftQueueRepository).save(queueEntity);
    }

    @Test
    void shouldRejectBookingWhenQueueIsNotOpen() {
        queueEntity.setStatus(QueueStatus.CLOSED);

        when(shiftQueueQueryService.checkAvailability(requestedQueue))
                .thenReturn(availableQueue);

        when(doctorShiftQueueRepository
                .findByIdWithPessimisticLock(100L))
                .thenReturn(Optional.of(queueEntity));

        assertThrows(
                BookingNotAllowedException.class,
                () -> service.allocateToken(requestedQueue)
        );

        verify(doctorShiftQueueRepository, never())
                .save(any(DoctorShiftQueueJpaEntity.class));
    }

    @Test
    void shouldRejectBookingWhenCapacityIsAlreadyReached() {
        // Simulate an inconsistent queue: OPEN but at capacity.
        queueEntity.setTotalActiveBookings(10);
        queueEntity.setMaxCapacity(10);

        when(shiftQueueQueryService.checkAvailability(requestedQueue))
                .thenReturn(availableQueue);

        when(doctorShiftQueueRepository
                .findByIdWithPessimisticLock(100L))
                .thenReturn(Optional.of(queueEntity));

        assertThrows(
                MaxBookingsReachedException.class,
                () -> service.allocateToken(requestedQueue)
        );

        verify(doctorShiftQueueRepository).save(queueEntity);
    }
}
