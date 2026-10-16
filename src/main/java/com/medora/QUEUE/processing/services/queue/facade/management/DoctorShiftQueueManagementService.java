package com.medora.QUEUE.processing.services.facade.management;

import com.medora.DOCTOR.processing.models.Doctor;
import com.medora.QUEUE.processing.models.DoctorShiftQueue;
import lombok.NonNull;

import java.util.UUID;

public interface DoctorShiftQueueManagementService {

    Integer allocateToken(@NonNull DoctorShiftQueue shiftQueue, @NonNull UUID idempotency_key);
}
