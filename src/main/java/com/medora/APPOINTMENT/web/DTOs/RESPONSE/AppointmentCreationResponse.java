package com.medora.APPOINTMENT.web.DTOs.RESPONSE;

import com.medora.APPOINTMENT.common.enums.AppointmentStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.NonNull;
import java.time.LocalDate;
import java.time.LocalTime;

public record AppointmentCreationResponse(
        @NotBlank String arn,
        @NotBlank String doctorRegistrationNumber,
        @NonNull LocalDate shiftDate,
        @NonNull LocalTime startTime,
        @NonNull Integer tokenNumber,
        @NotBlank String patientRegistrationNumber,
        @NonNull AppointmentStatus status
        ) {
}
