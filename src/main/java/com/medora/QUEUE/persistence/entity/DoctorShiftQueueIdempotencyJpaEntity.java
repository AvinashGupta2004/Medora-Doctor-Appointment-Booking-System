package com.medora.QUEUE.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "com.medora.doctor_shift_queue_idempotency",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_queue_idempotency_key",
                        columnNames = {"idempotency_key"}
                )
        },
        indexes = {
                @Index(
                        name = "idx_queue_idempotency_queue_id",
                        columnList = "queue_id"
                )
        }
)
public class DoctorShiftQueueIdempotencyJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "doctor_shift_queue_idempotency_id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "idempotency_key", nullable = false, updatable = false, length = 36)
    private String idempotencyKey;

    @Column(name = "request_hash", nullable = false, updatable = false, length = 64)
    private String requestHash;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "queue_id",
            nullable = false,
            foreignKey = @ForeignKey(
                name = "fk_queue_idempotency_queue"
            )
    )
    private DoctorShiftQueueJpaEntity queue;

    @Column(
            name = "allocated_token",
            nullable = false
    )
    private Integer allocatedToken;

    @Column(
            name = "created_at",
            nullable = false,
            updatable = false
    )
    @CreationTimestamp
    private LocalDateTime createdAt;
}
