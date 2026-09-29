package com.medora.DOCTOR.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "com.medora.doctor_schedules",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_doctor_day_shift_start",
                        columnNames = {"doctor_id", "day_of_week", "shift_start_time"}
                )
        }
)
public class DoctorScheduleJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @ManyToOne(
            fetch = FetchType.LAZY,
            optional = false
    )
    @JoinColumn(
            name = "doctor_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_schedule_doctor")
    )
    private DoctorJpaEntity doctor;

    @Enumerated(EnumType.STRING)
    @Column(name = "day_of_week", nullable = false, length = 15)
    private DayOfWeek dayOfWeek;

    @Column(name = "shift_start_time", nullable = false)
    private LocalTime shiftStartTime;

    @Column(name = "shift_end_time", nullable = false)
    private LocalTime shiftEndTime;

    @Column(name = "max_capacity", nullable = false)
    private Integer maxCapacity;

    @Column(name = "is_active", nullable = false)
    private Boolean active = true;

}
