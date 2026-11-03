package com.medora.QUEUE.processing.services.queue.facade.query.search.strategies.impl;

import com.medora.DOCTOR.common.mappers.DoctorMapper;
import com.medora.QUEUE.common.enums.DoctorShiftQueueSearchContext;
import com.medora.QUEUE.common.mappers.DoctorShiftQueueMapper;
import com.medora.QUEUE.persistence.entity.DoctorShiftQueueJpaEntity;
import com.medora.QUEUE.persistence.repository.DoctorShiftQueueRepository;
import com.medora.QUEUE.processing.models.DoctorShiftQueue;
import com.medora.QUEUE.processing.services.queue.facade.query.search.strategies.DoctorShiftQueueSearchStrategy;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Validated
public class CustomKeyDoctorShiftQueueSearchStrategy implements DoctorShiftQueueSearchStrategy {

    private final DoctorShiftQueueRepository doctorShiftQueueRepository;
    private final DoctorMapper doctorMapper;
    private final DoctorShiftQueueMapper doctorShiftQueueMapper;

    @Override
    public Optional<DoctorShiftQueue> searchDoctorShiftQueue(@NonNull DoctorShiftQueue doctorShiftQueue) {
        if (doctorShiftQueue.getDoctorId() == null || doctorShiftQueue.getShiftDate() == null || doctorShiftQueue.getStartTime() == null)
            return Optional.empty();

        Optional<DoctorShiftQueueJpaEntity> fetchedShiftQueueOptional = doctorShiftQueueRepository
                .findByDoctorIdAndShiftDateAndStartTime(
                        doctorShiftQueue.getDoctorId(),
                        doctorShiftQueue.getShiftDate(),
                        doctorShiftQueue.getStartTime()
                );

        return fetchedShiftQueueOptional.map(doctorShiftQueueMapper::toModel);
    }

    @Override
    public List<DoctorShiftQueue> searchDoctorShiftQueues(@NonNull DoctorShiftQueue doctorShiftQueue) {
        if (doctorShiftQueue.getDoctorId() == null || doctorShiftQueue.getShiftDate() == null || doctorShiftQueue.getStartTime() == null)
            return List.of();

        Optional<DoctorShiftQueueJpaEntity> fetchedShiftQueueOptional = doctorShiftQueueRepository
                .findByDoctorIdAndShiftDateAndStartTime(
                        doctorShiftQueue.getDoctorId(),
                        doctorShiftQueue.getShiftDate(),
                        doctorShiftQueue.getStartTime()
                );

        return fetchedShiftQueueOptional.map(doctorShiftQueueJpaEntity -> List.of(doctorShiftQueueMapper.toModel(doctorShiftQueueJpaEntity))).orElseGet(List::of);
    }

    @Override
    public boolean supports(@NonNull DoctorShiftQueue doctorShiftQueue, DoctorShiftQueueSearchContext searchContext) {
        boolean hasRequiredFields = doctorShiftQueue.getDoctorId() != null && doctorShiftQueue.getShiftDate() != null && doctorShiftQueue.getStartTime() != null;

        return hasRequiredFields && searchContext.equals(DoctorShiftQueueSearchContext.SINGLE_DOCTOR_SHIFT_TIME_WINDOW);
    }
}
