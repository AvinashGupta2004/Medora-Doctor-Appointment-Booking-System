package com.medora.DOCTOR.persistence.repository;

import com.medora.DOCTOR.persistence.entity.DoctorJpaEntity;
import com.medora.DOCTOR.processing.models.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<DoctorJpaEntity, Long> {

    Optional<DoctorJpaEntity> findFirstByMedicalRegistrationNumberOrContactInformation_PrimaryMobileNumberOrContactInformation_PrimaryEmailAddress(String medicalRegistrationNumber, String primaryMobileNumber, String primaryEmailAddress);

    Optional<DoctorJpaEntity> findFirstByDoctorRegistrationNumberOrMedicalRegistrationNumberOrContactInformation_PrimaryMobileNumberOrContactInformation_PrimaryEmailAddress(Long doctorRegistrationNumber, String medicalRegistrationNumber, String primaryMobileNumber, String primaryEmailAddress);

    @Query("SELECT doctor FROM DoctorJpaEntity doctor JOIN FETCH doctor.doctorSchedules WHERE doctor.doctorRegistrationNumber = :doctorRegistrationNumber OR doctor.medicalRegistrationNumber = :medicalRegistrationNumber OR doctor.contactInformation.primaryMobileNumber = :primaryMobileNumber OR doctor.contactInformation.primaryEmailAddress = :primaryEmailAddress")
    Optional<DoctorJpaEntity> findFirstWithScheduleByDoctorRegistrationNumberOrMedicalRegistrationNumberOrContactInformation_PrimaryMobileNumberOrContactInformation_PrimaryEmailAddress(Long doctorRegistrationNumber, String medicalRegistrationNumber, String primaryMobileNumber, String primaryEmailAddress);

    @Query("SELECT doctor FROM DoctorJpaEntity doctor JOIN FETCH doctor.doctorSchedules WHERE doctor.doctorRegistrationNumber = :doctorRegistrationNumber OR doctor.medicalRegistrationNumber = :medicalRegistrationNumber OR doctor.specialization = :specialization OR doctor.location = :location OR doctor.contactInformation.firstName = :firstName OR doctor.contactInformation.lastName = :lastName")
    Optional<DoctorJpaEntity> findDoctorJpaEntityByDoctorRegistrationNumberOrMedicalLicenseNumberOrSpecializationOrLocationOrFirstNameOrLastNameWithSchedule(Long doctorRegistrationNumber, String medicalRegistrationNumber, String specialization, String location, String firstName, String lastName);

    @Query("SELECT doctor FROM DoctorJpaEntity doctor JOIN FETCH doctor.doctorSchedules WHERE doctor.doctorRegistrationNumber = :doctorRegistrationNumber")
    Optional<DoctorJpaEntity> findDoctorJpaEntityByDoctorRegistrationNumberWithSchedules(Long doctorRegistrationNumber);

    boolean existsDoctorJpaEntityByDoctorRegistrationNumber(Long registrationNumber);
}
