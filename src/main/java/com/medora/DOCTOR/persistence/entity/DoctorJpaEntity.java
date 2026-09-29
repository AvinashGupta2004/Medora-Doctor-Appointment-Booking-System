package com.medora.DOCTOR.persistence.entity;

import com.medora.common.models.ContactInformation;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "com.medora.doctors",
        indexes = {
               @Index(
                       name = "idx_medical_reg_number_primary_mobile_number_primary_email_addr",
                       columnList = "medical_reg_number, primary_mobile_number, primary_email_addr"
               ),
                @Index(
                        name = "idx_doctor_reg_number",
                        columnList = "doctor_reg_number"
                )
        }
)
public class DoctorJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "doctor_reg_number", nullable = false, unique = true)
    private Long doctorRegistrationNumber;

    @Column(name = "medical_reg_number", nullable = false, length = 50)
    private String medicalRegistrationNumber;

    @Embedded
    private ContactInformation contactInformation;

    @Column(name = "specialization", nullable = false)
    private String specialization;

    @Column(name = "location", nullable = false)
    private String location;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "doctor")
    @Builder.Default
    private List<DoctorScheduleJpaEntity> doctorSchedules = new ArrayList<>();

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
