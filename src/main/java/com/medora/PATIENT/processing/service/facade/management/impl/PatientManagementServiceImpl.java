package com.medora.PATIENT.processing.service.facade.management.impl;

import com.medora.PATIENT.common.exceptions.PatientAlreadyRegisteredException;
import com.medora.PATIENT.common.exceptions.PatientNotRegisteredException;
import com.medora.PATIENT.common.mappers.PatientMapper;
import com.medora.PATIENT.persistence.entity.PatientJpaEntity;
import com.medora.PATIENT.persistence.repository.PatientRepository;
import com.medora.PATIENT.processing.models.Patient;
import com.medora.PATIENT.processing.service.facade.management.PatientManagementService;
import com.medora.PATIENT.processing.service.facade.query.PatientQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class PatientManagementServiceImpl implements PatientManagementService {

    private final PatientMapper patientMapper;
    private final PatientQueryService patientQueryService;
    private final PatientRepository patientRepository;

    @Override
    @Transactional
    public Patient register(Patient patient) {
        if (!patientQueryService.isPatientRegistered(patient)) {
            PatientJpaEntity patientJpaEntity = patientMapper.toEntity(patient);
            patientRepository.save(patientJpaEntity);
            return patientMapper.toModel(patientJpaEntity);
        }

        throw new PatientAlreadyRegisteredException("Patient already registered!");
    }

    @Override
    @Transactional
    public void remove(Patient patient) {
        Patient registeredPatient = patientQueryService.getPatient(patient);
        if (registeredPatient == null) {
            throw new PatientNotRegisteredException("Patient not registered!");
        }
        PatientJpaEntity patientJpaEntity = patientMapper.toEntity(registeredPatient);
        patientRepository.delete(patientJpaEntity);
    }
}
