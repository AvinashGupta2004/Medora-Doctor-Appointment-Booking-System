package com.medora.QUEUE.processing.models;

import com.medora.QUEUE.common.enums.QueueStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorShiftQueue {

    private Long id;
    private Long doctorId;
    private Long doctorRegistrationNumber;
    private Long scheduleId;
    private LocalDate shiftDate;
    private LocalTime startTime;
    private LocalTime endTime;

    @Builder.Default
    private Integer maxCapacity = 0;

    @Builder.Default
    private Integer totalActiveBookings = 0;

    @Builder.Default
    private Integer lastTokenNumberIssued = 0;

    @Builder.Default
    private QueueStatus status = QueueStatus.OPEN;
}
