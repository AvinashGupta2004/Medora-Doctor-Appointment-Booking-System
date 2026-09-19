package com.medora.PATIENT.web.services;

import com.medora.PATIENT.common.mappers.PatientMapper;
import com.medora.PATIENT.processing.models.Patient;
import com.medora.PATIENT.processing.service.PatientServiceFacade;
import com.medora.PATIENT.web.DTOs.REQUEST.PatientDeletionRequest;
import com.medora.PATIENT.web.DTOs.REQUEST.PatientRegistrationRequest;
import com.medora.PATIENT.web.DTOs.RESPONSE.PatientRegistrationResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
@Validated
public class PatientService {

    private final PatientServiceFacade patientServiceFacade;
    private final PatientMapper patientMapper;

    public PatientRegistrationResponse registerNewPatient(
            @Valid @RequestBody PatientRegistrationRequest patientRegistrationRequest
    ) {
        Patient patient = patientMapper
                .mapPatientRegistrationRequestToModel(patientRegistrationRequest);

        Patient registeredPatient = patientServiceFacade.registerPatient(patient);

        return patientMapper
                .mapModelToPatientRegistrationResponse(registeredPatient);
    }

    public void removePatient(
            @Valid @RequestBody PatientDeletionRequest patientDeletionRequest
    ) {
        Patient patient = patientMapper.mapPatientDeletionRequestToModel(patientDeletionRequest);
        patientServiceFacade.removePatient(patient);
    }
}
