package com.medora.QUEUE.processing.services;

import com.medora.DOCTOR.processing.models.Doctor;
import org.jspecify.annotations.NonNull;

public interface DoctorShiftQueueServiceFacade {

    void createShiftQueuesForNDays(@NonNull Doctor doctor, int n);
}
