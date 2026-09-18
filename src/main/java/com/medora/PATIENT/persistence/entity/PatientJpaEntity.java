package com.medora.PATIENT.persistence.entity;

import com.medora.PATIENT.common.enums.Gender;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "com.medora.patients",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_patient_email", columnNames = {"email_addr"})
        },
        indexes = {
                @Index(
                        name = "idx_contact_dob_fname_lname",
                        columnList = "contact_number, date_of_birth, first_name, last_name"
                )
        }
)
public class PatientJpaEntity {

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
            nullable = true
    )
    private String lastName;

    @Column(
            name = "email_addr",
            nullable = false
    )
    private String emailAddress;

    @Column(
            name = "contact_number",
            nullable = false
    )
    private String contactNumber;

    @Column(
            name = "date_of_birth",
            nullable = false
    )
    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "gender",
            nullable = false
    )
    private Gender gender;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
