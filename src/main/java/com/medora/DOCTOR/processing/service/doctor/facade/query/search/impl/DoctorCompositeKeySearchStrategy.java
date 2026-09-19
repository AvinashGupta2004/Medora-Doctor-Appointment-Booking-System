package com.medora.DOCTOR.processing.service.doctor.facade.query.search.impl;

import com.medora.DOCTOR.common.mappers.DoctorMapper;
import com.medora.DOCTOR.persistence.entity.DoctorJpaEntity;
import com.medora.DOCTOR.persistence.repository.DoctorRepository;
import com.medora.DOCTOR.processing.models.Doctor;
import com.medora.DOCTOR.processing.service.doctor.facade.query.search.DoctorSearchStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DoctorCompositeKeySearchStrategy implements DoctorSearchStrategy {

    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;

    @Override
    public Optional<Doctor> searchDoctor(Doctor doctor) {
        Optional<DoctorJpaEntity> optionalDoctorEntity = doctorRepository
                .findDoctorJpaEntityByDoctorRegistrationNumberOrMedicalLicenseNumberOrSpecializationOrLocationOrFirstNameOrLastName(
                        doctor.getDoctorRegistrationNumber(),
                        doctor.getMedicalLicenseNumber(),
                        doctor.getSpecialization(),
                        doctor.getLocation(),
                        doctor.getFirstName(),
                        doctor.getLastName()
                );
        return optionalDoctorEntity.map(doctorMapper::toModel);
    }
}
