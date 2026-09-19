package com.medora.PATIENT.processing.service.facade.query.search.impl;


import com.medora.PATIENT.common.mappers.PatientMapper;
import com.medora.PATIENT.persistence.repository.PatientRepository;
import com.medora.PATIENT.processing.models.Patient;
import com.medora.PATIENT.processing.service.facade.query.search.PatientSearchStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Primary
@Component
@RequiredArgsConstructor
public class PatientCompositeKeySearchStrategy implements PatientSearchStrategy{

    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;

    @Override
    public Optional<Patient> searchPatient(Patient patient) {
        return patientRepository
                .findPatientJpaEntityByPatientRegistrationNumberAndContactNumberOrDateOfBirthOrFirstNameOrLastName(
                        patient.getPatientRegistrationNumber(),
                        patient.getContactNumber(),
                        patient.getDateOfBirth(),
                        patient.getFirstName(),
                        patient.getLastName()
                ).map(patientMapper::toModel);
    }
}
