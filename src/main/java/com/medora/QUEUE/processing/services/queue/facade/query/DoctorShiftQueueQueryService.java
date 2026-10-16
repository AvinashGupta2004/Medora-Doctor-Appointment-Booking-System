package com.medora.QUEUE.processing.services.facade.query;

import com.medora.QUEUE.processing.models.DoctorShiftQueue;
import org.springframework.stereotype.Service;

import java.util.List;

public interface DoctorShiftQueueQueryService {

    DoctorShiftQueue checkAvailability(DoctorShiftQueue shiftQueue);

    DoctorShiftQueue getShiftQueue(DoctorShiftQueue shiftQueue);

    List<DoctorShiftQueue> getAllShiftQueuesForDate(DoctorShiftQueue shiftQueue);
}
