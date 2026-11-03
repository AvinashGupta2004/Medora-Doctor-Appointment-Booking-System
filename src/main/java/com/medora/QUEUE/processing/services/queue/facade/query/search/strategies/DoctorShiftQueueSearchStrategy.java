package com.medora.QUEUE.processing.services.queue.facade.query.search.strategies;

import com.medora.QUEUE.common.enums.DoctorShiftQueueSearchContext;
import com.medora.QUEUE.processing.models.DoctorShiftQueue;
import lombok.NonNull;

import java.util.List;
import java.util.Optional;

public interface DoctorShiftQueueSearchStrategy {

    Optional<DoctorShiftQueue> searchDoctorShiftQueue(@NonNull DoctorShiftQueue doctorShiftQueue);
    List<DoctorShiftQueue> searchDoctorShiftQueues(@NonNull DoctorShiftQueue doctorShiftQueue);
    boolean supports(@NonNull DoctorShiftQueue doctorShiftQueue, DoctorShiftQueueSearchContext searchContext);
}
