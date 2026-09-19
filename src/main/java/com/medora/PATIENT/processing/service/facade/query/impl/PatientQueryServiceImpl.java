package com.medora.PATIENT.processing.service.facade.query.impl;

import com.medora.PATIENT.common.exceptions.PatientNotRegisteredException;
import com.medora.PATIENT.common.mappers.PatientMapper;
import com.medora.PATIENT.persistence.repository.PatientRepository;
import com.medora.PATIENT.processing.models.Patient;
import com.medora.PATIENT.processing.service.facade.query.PatientQueryService;
import com.medora.PATIENT.processing.service.facade.query.search.PatientSearchStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PatientQueryServiceImpl implements PatientQueryService {

    private final PatientRepository patientRepository;
    private final PatientSearchStrategy patientSearchStrategy;
    private final PatientMapper patientMapper;

    @Override
    public boolean isPatientRegistered(Patient patient) {
        return patientSearchStrategy.searchPatient(patient).isPresent();
    }

    public Patient getPatient(Patient patient) {
        Optional<Patient> optionalPatient = patientSearchStrategy.searchPatient(patient);
        if (optionalPatient.isEmpty())
            throw new PatientNotRegisteredException("No patient registered with provided details!");

        return optionalPatient.get();
    }
}
