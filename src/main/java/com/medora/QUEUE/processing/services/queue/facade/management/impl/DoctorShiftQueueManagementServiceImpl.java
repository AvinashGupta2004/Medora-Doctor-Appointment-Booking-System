package com.medora.QUEUE.processing.services.queue.facade.management.impl;

import com.medora.QUEUE.common.enums.QueueStatus;
import com.medora.QUEUE.common.exceptions.BookingNotAllowedException;
import com.medora.QUEUE.common.exceptions.MaxBookingsReachedException;
import com.medora.QUEUE.persistence.entity.DoctorShiftQueueJpaEntity;
import com.medora.QUEUE.persistence.repository.DoctorShiftQueueRepository;
import com.medora.QUEUE.processing.models.DoctorShiftQueue;
import com.medora.QUEUE.processing.services.queue.facade.management.DoctorShiftQueueManagementService;
import com.medora.QUEUE.processing.services.queue.facade.query.DoctorShiftQueueQueryService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Validated
@Transactional
public class DoctorShiftQueueManagementServiceImpl implements DoctorShiftQueueManagementService {

    private final DoctorShiftQueueQueryService shiftQueueQueryService;
    private final DoctorShiftQueueRepository doctorShiftQueueRepository;
    private final EntityManager entityManager;

    @Override
    public Integer allocateToken(@NonNull DoctorShiftQueue shiftQueue) {
        // Work Order:
        // 1. Validate the queue and fetch its JPA entity.
        // 2. Lock the queue row to prevent concurrent overbooking.
        // 3. Verify that bookings are allowed and capacity remains.
        // 4. Allocate the next token.
        // 5. Update queue status if capacity is reached.
        // 6. Save the idempotency record and queue changes.
        // 7. Return the allocated token.

        // 1. Fetch the Doctor Shift Queue
        DoctorShiftQueue availableQueue = shiftQueueQueryService.getShiftQueue(shiftQueue);

        // 2. Fetch the queue jpa entity with pessimistic locking!
        DoctorShiftQueueJpaEntity fetchedEntity = doctorShiftQueueRepository
                .findByIdWithPessimisticLock(availableQueue.getId())
                .orElseThrow(() -> new IllegalStateException("Doctor Shift Queue not found!!"));

        entityManager.refresh(fetchedEntity, LockModeType.PESSIMISTIC_WRITE);
        // 3. Ensure bookings are allowed.
        if (fetchedEntity.getStatus() != QueueStatus.OPEN)
            throw new BookingNotAllowedException("No more bookings allowed!");

        int totalActiveBookings = fetchedEntity.getTotalActiveBookings();
        int lastTokenNumberIssued = fetchedEntity.getLastTokenNumberIssued();
        int maxCapacity = fetchedEntity.getMaxCapacity();

        if (totalActiveBookings >= maxCapacity) {
            fetchedEntity.setStatus(QueueStatus.FULL);
            doctorShiftQueueRepository.saveAndFlush(fetchedEntity);
            throw new MaxBookingsReachedException("No more bookings Allowed! Maximum bookings reached!");
        }

        // 4. Safely allocate the next token
        int nextAllocatedToken = lastTokenNumberIssued + 1;
        totalActiveBookings += 1;
        fetchedEntity.setTotalActiveBookings(totalActiveBookings);

        // 5. Update the queue status if capacity is reached
        if (fetchedEntity.getTotalActiveBookings().equals(fetchedEntity.getMaxCapacity())) {
            fetchedEntity.setStatus(QueueStatus.FULL);
        }

        // 6. Updating the modified entity
        doctorShiftQueueRepository.save(fetchedEntity);

        // 7. Returning the new allocated token.
        return nextAllocatedToken;
    }
}
