package com.medora.APPOINTMENT.persistence.entity;

import com.medora.APPOINTMENT.common.enums.AppointmentStatus;
import com.medora.DOCTOR.persistence.entity.DoctorJpaEntity;
import com.medora.PATIENT.persistence.entity.PatientJpaEntity;
import com.medora.QUEUE.persistence.entity.DoctorShiftQueueJpaEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "com.medora.appointments",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_queue_token",
                columnNames = {"queue_id", "token_number"}
        )
)
public class AppointmentJpaEntity {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    @Column(name = "appointment_id", nullable = false, updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "queue_id", nullable = false, foreignKey = @ForeignKey(name = "fk_appointment_queue"))
    private DoctorShiftQueueJpaEntity queue;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "patient_id", nullable = false, foreignKey = @ForeignKey(name = "fk_appointment_patient"))
    private PatientJpaEntity patient;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "doctor_id", nullable = false, foreignKey = @ForeignKey(name = "fk_appointment_doctor"))
    private DoctorJpaEntity doctor;

    @Column(name = "token_number", nullable = false)
    private Integer tokenNumber;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(
            name = "status",
            nullable = false
    )
    private AppointmentStatus status = AppointmentStatus.BOOKED;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
