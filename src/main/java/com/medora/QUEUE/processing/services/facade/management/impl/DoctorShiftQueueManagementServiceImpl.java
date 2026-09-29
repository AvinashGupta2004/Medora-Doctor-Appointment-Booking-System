package com.medora.QUEUE.processing.services.facade.management.impl;

import com.medora.DOCTOR.processing.models.Doctor;
import com.medora.QUEUE.processing.services.facade.management.DoctorShiftQueueManagementService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DoctorShiftQueueManagementServiceImpl implements DoctorShiftQueueManagementService {

    @Override
    public void createDoctorShiftQueuesForNDays(@NonNull Doctor doctor, int n) {

    }
}
