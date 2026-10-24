package com.medora.QUEUE.processing.services.queue.facade.query;

import com.medora.QUEUE.processing.models.DoctorShiftQueue;

import java.util.List;

public interface DoctorShiftQueueQueryService {

    DoctorShiftQueue checkAvailability(DoctorShiftQueue shiftQueue);

    DoctorShiftQueue getShiftQueue(DoctorShiftQueue shiftQueue);

    List<DoctorShiftQueue> getAllShiftQueuesForDate(DoctorShiftQueue shiftQueue);
}
