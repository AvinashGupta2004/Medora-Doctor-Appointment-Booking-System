package com.medora.QUEUE.processing.services.facade.query;

import com.medora.QUEUE.processing.models.DoctorShiftQueue;
import org.springframework.stereotype.Service;

@Service
public interface DoctorShiftQueueQueryService {

    DoctorShiftQueue checkAvailability(DoctorShiftQueue shiftQueue);
}
