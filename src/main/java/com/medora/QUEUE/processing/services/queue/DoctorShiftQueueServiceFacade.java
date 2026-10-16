package com.medora.QUEUE.processing.services;

import com.medora.QUEUE.processing.models.DoctorShiftQueue;
import lombok.NonNull;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public interface DoctorShiftQueueServiceFacade {

    DoctorShiftQueue checkAvailability(@NonNull DoctorShiftQueue doctorShiftQueue);

    Integer allocateToken(@NonNull DoctorShiftQueue doctorShiftQueue);

    List<DoctorShiftQueue> getAllShiftQueuesForDate(@NonNull DoctorShiftQueue doctorShiftQueue);
}
