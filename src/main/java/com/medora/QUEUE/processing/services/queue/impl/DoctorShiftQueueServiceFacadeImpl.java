package com.medora.QUEUE.processing.services.impl;

import com.medora.DOCTOR.processing.models.Doctor;
import com.medora.QUEUE.processing.models.DoctorShiftQueue;
import com.medora.QUEUE.processing.services.DoctorShiftQueueServiceFacade;
import com.medora.QUEUE.processing.services.facade.management.DoctorShiftQueueManagementService;
import com.medora.QUEUE.processing.services.facade.query.DoctorShiftQueueQueryService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorShiftQueueServiceFacadeImpl implements DoctorShiftQueueServiceFacade {

    private final DoctorShiftQueueManagementService doctorShiftQueueManagementService;
    private final DoctorShiftQueueQueryService doctorShiftQueueQueryService;

    @Override
    public Integer allocateToken(@NonNull DoctorShiftQueue doctorShiftQueue) {
        return 0;
    }

    @Override
    public List<DoctorShiftQueue> getAllShiftQueuesForDate(@NonNull DoctorShiftQueue doctorShiftQueue) {
        return doctorShiftQueueQueryService.getAllShiftQueuesForDate(doctorShiftQueue);
    }

    @Override
    public DoctorShiftQueue checkAvailability(@NonNull DoctorShiftQueue doctorShiftQueue) {
        return doctorShiftQueueQueryService.checkAvailability(doctorShiftQueue);
    }
}
