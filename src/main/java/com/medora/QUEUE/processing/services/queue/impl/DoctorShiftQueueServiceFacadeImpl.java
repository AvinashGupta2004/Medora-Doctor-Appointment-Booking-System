package com.medora.QUEUE.processing.services.queue.impl;

import com.medora.QUEUE.processing.models.DoctorShiftQueue;
import com.medora.QUEUE.processing.services.queue.DoctorShiftQueueServiceFacade;
import com.medora.QUEUE.processing.services.queue.facade.management.DoctorShiftQueueManagementService;
import com.medora.QUEUE.processing.services.queue.facade.query.DoctorShiftQueueQueryService;
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
        return doctorShiftQueueManagementService.allocateToken(doctorShiftQueue);
    }

    @Override
    public List<DoctorShiftQueue> getAllShiftQueuesForDate(@NonNull DoctorShiftQueue doctorShiftQueue) {
        return doctorShiftQueueQueryService.getAllShiftQueuesForDate(doctorShiftQueue);
    }

    @Override
    public DoctorShiftQueue getDoctorShiftQueue(@NonNull DoctorShiftQueue doctorShiftQueue) {
        return doctorShiftQueueQueryService.getShiftQueue(doctorShiftQueue);
    }

    @Override
    public DoctorShiftQueue checkAvailability(@NonNull DoctorShiftQueue doctorShiftQueue) {
        return doctorShiftQueueQueryService.checkAvailability(doctorShiftQueue);
    }
}
