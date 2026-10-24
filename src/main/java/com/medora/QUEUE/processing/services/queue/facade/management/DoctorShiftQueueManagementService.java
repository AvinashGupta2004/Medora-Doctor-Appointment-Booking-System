package com.medora.QUEUE.processing.services.queue.facade.management;

import com.medora.QUEUE.processing.models.DoctorShiftQueue;
import lombok.NonNull;

import java.util.UUID;

public interface DoctorShiftQueueManagementService {

    Integer allocateToken(@NonNull DoctorShiftQueue shiftQueue);
}
