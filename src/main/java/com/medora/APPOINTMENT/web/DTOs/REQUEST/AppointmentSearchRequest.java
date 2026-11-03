package com.medora.APPOINTMENT.web.DTOs.REQUEST;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import java.time.LocalDate;
import java.time.LocalTime;

public record AppointmentSearchRequest(
    @NotNull String appointmentReferenceNumber
) {
}
