package com.medora.QUEUE.processing.services.queue.facade.query.search.strategies.impl;

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
public class QueueIdSearchStrategy implements DoctorShiftQueueSearchStrategy {
    private final DoctorShiftQueueRepository doctorShiftQueueRepository;
    private final DoctorShiftQueueMapper doctorShiftQueueMapper;

    @Override
    public Optional<DoctorShiftQueue> searchDoctorShiftQueue(@NonNull DoctorShiftQueue doctorShiftQueue) {
        if (doctorShiftQueue.getId() == null)
            return Optional.empty();

        Optional<DoctorShiftQueueJpaEntity> fetchedDoctorShiftQueueOptional = doctorShiftQueueRepository.findById(doctorShiftQueue.getId());

        return fetchedDoctorShiftQueueOptional.map(doctorShiftQueueMapper::toModel);
    }

    @Override
    public List<DoctorShiftQueue> searchDoctorShiftQueues(@NonNull DoctorShiftQueue doctorShiftQueue) {
        if (doctorShiftQueue.getId() == null)
            return List.of();

        Optional<DoctorShiftQueueJpaEntity> fetchedShiftQueueOptional = doctorShiftQueueRepository
                .findById(
                        doctorShiftQueue.getId()
                );

        return fetchedShiftQueueOptional.map(doctorShiftQueueJpaEntity -> List.of(doctorShiftQueueMapper.toModel(doctorShiftQueueJpaEntity))).orElseGet(List::of);
    }

    @Override
    public boolean supports(@NonNull DoctorShiftQueue doctorShiftQueue, DoctorShiftQueueSearchContext searchContext) {
        boolean hasRequiredFields = doctorShiftQueue.getId() != null;

        return hasRequiredFields && searchContext.equals(DoctorShiftQueueSearchContext.SINGLE_QUEUE_ID);
    }
}
