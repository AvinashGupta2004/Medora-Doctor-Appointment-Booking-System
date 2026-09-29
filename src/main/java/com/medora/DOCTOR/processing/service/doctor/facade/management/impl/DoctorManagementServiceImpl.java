package com.medora.DOCTOR.processing.service.doctor.facade.management.impl;

import com.medora.DOCTOR.common.exceptions.DoctorAlreadyRegisteredException;
import com.medora.DOCTOR.common.exceptions.DoctorNotRegisteredException;
import com.medora.DOCTOR.common.mappers.DoctorMapper;
import com.medora.DOCTOR.persistence.entity.DoctorJpaEntity;
import com.medora.DOCTOR.persistence.repository.DoctorRepository;
import com.medora.DOCTOR.processing.models.Doctor;
import com.medora.DOCTOR.processing.service.doctor.facade.management.DoctorManagementService;
import com.medora.DOCTOR.processing.service.doctor.facade.query.DoctorQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Component
@RequiredArgsConstructor
public class DoctorManagementServiceImpl implements DoctorManagementService {

    private final DoctorRepository doctorRepository;
    private final DoctorQueryService doctorQueryService;
    private final DoctorMapper doctorMapper;

    @Override
    @Transactional
    public Doctor register(Doctor doctor) {
        if (!doctorQueryService.isDoctorRegistered(doctor)) {
            DoctorJpaEntity doctorJpaEntity = doctorMapper.toEntity(doctor);
            doctorJpaEntity.setDoctorRegistrationNumber(generateDoctorRegistrationNumber());
            doctorRepository.save(doctorJpaEntity);
            return doctorMapper.toModel(doctorJpaEntity);
        }
        throw new DoctorAlreadyRegisteredException("Doctor already registered!");
    }

    @Override
    public void remove(Doctor doctor) {
        Doctor registeredDoctor = doctorQueryService.getDoctor(doctor, false);
        if (registeredDoctor == null) {
            throw new DoctorNotRegisteredException("Doctor not registered!");
        }
        DoctorJpaEntity doctorJpaEntity = doctorMapper.toEntity(registeredDoctor);
        doctorRepository.delete(doctorJpaEntity);
    }

    private Long generateDoctorRegistrationNumber() {
        Long registrationNumber;
        Random random = new Random();
        do {
            registrationNumber = 10000000000L + random.nextLong(90000000000L);
        }
        while (doctorRepository.existsDoctorJpaEntityByDoctorRegistrationNumber(registrationNumber));
        return registrationNumber;
    }
}
