package com.medora.QUEUE.web.DTOs.REQUEST;

import lombok.NonNull;
import java.time.LocalDate;
import java.time.LocalTime;

public record DoctorShiftQueueAvailabilityRequest(
        @NonNull String doctorRegistrationNumber,
        LocalDate shiftDate,
        LocalTime startTime
) {

}
