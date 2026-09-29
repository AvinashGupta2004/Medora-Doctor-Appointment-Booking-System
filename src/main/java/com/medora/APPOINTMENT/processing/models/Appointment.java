package com.medora.APPOINTMENT.processing.models;

import com.medora.APPOINTMENT.common.enums.AppointmentStatus;
import com.medora.APPOINTMENT.common.exceptions.MedoraDomainException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {

    private Long id;
    private Long queueId;
    private Long patientId;
    private Long doctorId;
    private Integer tokenNumber;

    @Builder.Default
    private AppointmentStatus status = AppointmentStatus.BOOKED;
    private LocalDateTime createdAt;
}
