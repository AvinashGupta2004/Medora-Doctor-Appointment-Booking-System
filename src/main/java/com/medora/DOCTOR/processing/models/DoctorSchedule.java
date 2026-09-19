package com.medora.DOCTOR.processing.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorSchedule {

    private Long id;
    private Long doctorId;
    private DayOfWeek dayOfWeek;
    private LocalTime shiftStartTime;
    private LocalTime shiftEndTime;
    private Integer maxCapacity;

    @Builder.Default
    private Boolean active = true;
}
