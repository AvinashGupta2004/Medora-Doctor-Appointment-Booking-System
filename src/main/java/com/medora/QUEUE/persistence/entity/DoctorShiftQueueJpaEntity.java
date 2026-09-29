package com.medora.QUEUE.persistence.entity;

import com.medora.DOCTOR.persistence.entity.DoctorJpaEntity;
import com.medora.DOCTOR.persistence.entity.DoctorScheduleJpaEntity;
import com.medora.QUEUE.common.enums.QueueStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "com.medora.doctor_shift_queues",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_doctor_date_shift_start",
                        columnNames = {"doctor_id", "shift_date", "start_time"}
                )
        }
)
public class DoctorShiftQueueJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "doctor_id", nullable = false, foreignKey = @ForeignKey(name = "fk_queue_doctor"))
    private DoctorJpaEntity doctor;

    @ManyToOne(
            fetch = FetchType.LAZY,
            optional = false
    )
    @JoinColumn(
            name = "doctor_schedule_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_queue_doctor_schedule")
    )
    private DoctorScheduleJpaEntity doctorSchedule;

    @Column(name = "shift_date", nullable = false)
    private LocalDate shiftDate;

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    @Column(name = "max_capacity", nullable = false)
    private Integer maxCapacity;

    @Builder.Default
    @Column(name = "booked_tokens", nullable = false)
    private Integer bookedTokens = 0;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private QueueStatus status = QueueStatus.OPEN;
}
