package com.medora.PATIENT.processing.service.facade.query.search.strategies.impl;


import com.medora.PATIENT.common.enums.PatientSearchContext;
import com.medora.PATIENT.common.mappers.PatientMapper;
import com.medora.PATIENT.persistence.repository.PatientRepository;
import com.medora.PATIENT.processing.models.Patient;
import com.medora.PATIENT.processing.service.facade.query.search.strategies.PatientSearchStrategy;
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

    @Override
    public boolean supports(Patient patient, PatientSearchContext context) {
        boolean hasRequiredFields = patient.getContactNumber() != null && patient.getEmailAddress() != null;

        return hasRequiredFields && context.equals(PatientSearchContext.SINGLE_EMAIL);
    }
}
