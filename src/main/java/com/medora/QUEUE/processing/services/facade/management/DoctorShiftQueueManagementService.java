package com.medora.QUEUE.processing.services.facade.management;

import com.medora.DOCTOR.processing.models.Doctor;
import lombok.NonNull;

public interface DoctorShiftQueueManagementService {

    void createDoctorShiftQueuesForNDays(@NonNull Doctor doctor, int n);
}
