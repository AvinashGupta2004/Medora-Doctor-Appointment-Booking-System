package com.medora.DOCTOR.web.DTOs.RESPONSE;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorScheduleResponse {

    @NotBlank(message = "Doctor Registration Number cannot be left Empty!")
    @Pattern(regexp = "^D-[0-9]+$", message = "Invalid Doctor Registration Number!")
    private String doctorRegistrationNumber;

    @NotNull(message = "Day of Week is required!")
    private DayOfWeek dayOfWeek;

    @NotNull(message = "Shift Start Time is required!")
    private LocalTime shiftStartTime;

    @NotNull(message = "Shift End Time is required!")
    private LocalTime shiftEndTime;

    @NotNull(message = "Max Capacity of Patient's in given Shift is required!")
    @Min(value = 0, message = "Max Capacity of Patients should be at least 0!")
    private Integer maxCapacity;
}
