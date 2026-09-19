package com.medora.DOCTOR.persistence.entity;

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
        name = "com.medora.doctors",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_medical_license", columnNames = {"medical_license_number"})
        },
        indexes = {
                @Index(
                        name = "idx_reg_number_license_specialization_location_fname_lname",
                        columnList = "doctor_reg_number, medical_license_number, specialization, location, first_name, last_name"
                )
        }
)
public class DoctorJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            name = "first_name",
            nullable = false
    )
    private String firstName;

    @Column(
            name = "last_name",
            nullable = false
    )
    private String lastName;

    @Column(
            name = "doctor_reg_number",
            nullable = false,
            unique = true
    )
    private Long doctorRegistrationNumber;

    @Column(
            name = "medical_license_number",
            nullable = false,
            length = 50
    )
    private String medicalLicenseNumber;

    @Column(
            name = "specialization",
            nullable = false
    )
    private String specialization;

    @Column(
            name = "location",
            nullable = false
    )
    private String location;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
