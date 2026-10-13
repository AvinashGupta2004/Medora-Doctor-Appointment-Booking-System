package com.medora.QUEUE.processing.services.facade.management;

import com.medora.DOCTOR.processing.models.Doctor;
import com.medora.QUEUE.processing.models.DoctorShiftQueue;
import lombok.NonNull;

public interface DoctorShiftQueueManagementService {

    Integer allocateToken(@NonNull DoctorShiftQueue shiftQueue);
}
