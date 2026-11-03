package com.medora.APPOINTMENT.persistence.entity;

import com.medora.APPOINTMENT.common.enums.AppointmentStatus;
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
        name = "com.medora.appointment_idempotency",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_idempotency",
                columnNames = {"idempotency_key", "arn"}
        )
)
public class AppointmentIdempotencyJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "idempotency_key", nullable = false, unique = true)
    private String idempotencyKey;

    @Column(name = "arn", nullable = false, unique = true)
    private String appointmentReferenceNumber;

    @Builder.Default
    @Column(name = "status", nullable = false)
    private AppointmentStatus status = AppointmentStatus.PENDING;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
