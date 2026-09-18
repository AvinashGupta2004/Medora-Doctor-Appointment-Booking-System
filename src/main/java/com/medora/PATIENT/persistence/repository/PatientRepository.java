package com.medora.PATIENT.persistence.repository;

import com.medora.PATIENT.persistence.entity.PatientJpaEntity;
import com.medora.PATIENT.processing.models.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<PatientJpaEntity, Long> {

    Optional<PatientJpaEntity> findByContactNumber(String contactNumber);


    Optional<PatientJpaEntity> findPatientJpaEntityByContactNumberOrDateOfBirthOrFirstNameOrLastName(
            String contactNumber, LocalDate dateOfBirth, String firstName, String lastName
    );
}
