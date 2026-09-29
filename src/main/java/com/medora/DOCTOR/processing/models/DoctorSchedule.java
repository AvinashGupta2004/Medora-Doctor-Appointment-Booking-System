package com.medora.DOCTOR.processing.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jspecify.annotations.NonNull;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Comparator;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorSchedule implements Comparable<DoctorSchedule>{

    private Long id;
    private Long doctorId;
    private Long doctorRegistrationNumber;
    private DayOfWeek dayOfWeek;
    private LocalTime shiftStartTime;
    private LocalTime shiftEndTime;
    private Integer maxCapacity;

    @Builder.Default
    private Boolean active = true;

    @Override
    public int compareTo(@NonNull DoctorSchedule schedule) {
        return Comparator.comparing(DoctorSchedule::getDayOfWeek)
                .thenComparing(DoctorSchedule::getShiftStartTime)
                .thenComparing(DoctorSchedule::getShiftEndTime)
                .compare(this, schedule);
    }
}
