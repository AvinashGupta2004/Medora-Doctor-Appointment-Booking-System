package com.medora.APPOINTMENT.web.DTOs.REQUEST;

import jakarta.validation.constraints.NotBlank;
import lombok.NonNull;
import java.time.LocalDate;
import java.time.LocalTime;

public record AppointmentCreationRequest(
    @NotBlank String doctorRegistrationNumber,
    @NonNull LocalDate shiftDate,
    @NonNull LocalTime startTime,
    @NotBlank String patientRegistrationNumber,
    @NotBlank String patientContactNumber // this is to be deleted, and patient search strategy has to be modified!
) {
}
