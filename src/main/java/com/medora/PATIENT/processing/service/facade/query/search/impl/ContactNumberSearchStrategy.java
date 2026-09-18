package com.medora.PATIENT.processing.service.facade.query.search.impl;

import com.medora.PATIENT.common.mappers.PatientMapper;
import com.medora.PATIENT.persistence.entity.PatientJpaEntity;
import com.medora.PATIENT.persistence.repository.PatientRepository;
import com.medora.PATIENT.processing.models.Patient;
import com.medora.PATIENT.processing.service.facade.query.search.PatientSearchStrategy;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ContactNumberSearchStrategy implements PatientSearchStrategy {

    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;

    @Override
    public Optional<Patient> getPatient(@NotNull Patient patient) {
        Optional<PatientJpaEntity> optionalPatient = patientRepository
                .findByContactNumber(patient.getContactNumber());

        return optionalPatient.map(patientMapper::toModel);
    }
}
