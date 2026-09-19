package com.medora.DOCTOR.persistence.repository;

import com.medora.DOCTOR.persistence.entity.DoctorJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<DoctorJpaEntity, Long> {

    Optional<DoctorJpaEntity> findByMedicalLicenseNumber(String medicalLicenseNumber);

    Optional<DoctorJpaEntity> findDoctorJpaEntityByDoctorRegistrationNumberOrMedicalLicenseNumberOrSpecializationOrLocationOrFirstNameOrLastName(Long doctorRegistrationNumber, String medicalLicenseNumber, String specialization, String location, String firstName, String lastName);

    List<DoctorJpaEntity> lastName(String lastName);

    boolean existsDoctorJpaEntityByDoctorRegistrationNumber(Long registrationNumber);
}
