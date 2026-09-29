package com.medora.QUEUE.processing.services.impl;

import com.medora.DOCTOR.processing.models.Doctor;
import com.medora.QUEUE.processing.services.DoctorShiftQueueServiceFacade;
import com.medora.QUEUE.processing.services.facade.management.DoctorShiftQueueManagementService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DoctorShiftQueueServiceFacadeImpl implements DoctorShiftQueueServiceFacade {

    private final DoctorShiftQueueManagementService doctorShiftQueueManagementService;

    @Override
    public void createShiftQueuesForNDays(@NonNull Doctor doctor, int n) {
        doctorShiftQueueManagementService.createDoctorShiftQueuesForNDays(doctor, n);
    }
}
