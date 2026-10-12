package com.medora.QUEUE.processing.models;

import com.medora.APPOINTMENT.common.exceptions.MedoraDomainException;
import com.medora.DOCTOR.common.enums.ShiftStatus;
import com.medora.QUEUE.common.enums.QueueStatus;
import com.medora.QUEUE.common.exceptions.BookingNotAllowedException;
import com.medora.QUEUE.common.exceptions.MaxBookingsReachedException;
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
    private Integer maxCapacity;

    @Builder.Default
    private Integer bookedTokens = 0;

    @Builder.Default
    private QueueStatus status = QueueStatus.OPEN;
}
