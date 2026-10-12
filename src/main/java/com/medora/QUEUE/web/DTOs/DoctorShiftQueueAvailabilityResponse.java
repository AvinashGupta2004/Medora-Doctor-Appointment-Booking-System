package com.medora.QUEUE.web.DTOs;

import com.medora.QUEUE.common.enums.QueueStatus;
import lombok.NonNull;
import java.time.LocalDate;
import java.time.LocalTime;

public record DoctorShiftQueueAvailabilityResponse(
        @NonNull String doctorRegistrationNumber,
        LocalDate shiftDate,
        LocalTime startTime,
        LocalTime endTime,
        Integer currentAvailability,
        QueueStatus status
) {

}
