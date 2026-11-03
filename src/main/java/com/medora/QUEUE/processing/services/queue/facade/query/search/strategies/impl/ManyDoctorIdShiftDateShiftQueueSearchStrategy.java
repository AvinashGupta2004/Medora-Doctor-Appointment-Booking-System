package com.medora.QUEUE.processing.services.queue.facade.query.search.strategies.impl;

import com.medora.QUEUE.common.enums.DoctorShiftQueueSearchContext;
import com.medora.QUEUE.processing.models.DoctorShiftQueue;
import com.medora.QUEUE.processing.services.queue.facade.query.search.strategies.DoctorShiftQueueSearchStrategy;
import lombok.NonNull;

import java.util.List;
import java.util.Optional;

public class ManyDoctorIdShiftDateShiftQueueSearchStrategy implements DoctorShiftQueueSearchStrategy {

    @Override
    public Optional<DoctorShiftQueue> searchDoctorShiftQueue(@NonNull DoctorShiftQueue doctorShiftQueue) {
        return Optional.empty();
    }

    @Override
    public List<DoctorShiftQueue> searchDoctorShiftQueues(@NonNull DoctorShiftQueue doctorShiftQueue) {
        return List.of();
    }

    @Override
    public boolean supports(@NonNull DoctorShiftQueue doctorShiftQueue, DoctorShiftQueueSearchContext searchContext) {
        boolean hasRequiredFields = doctorShiftQueue.getDoctorId() != null && doctorShiftQueue.getShiftDate() != null;
        return hasRequiredFields && searchContext.equals(DoctorShiftQueueSearchContext.MANY_DOCTOR_SHIFT_DATE);
    }
}
