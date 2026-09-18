package com.medora.PATIENT.processing.service.impl;

import com.medora.PATIENT.processing.models.Patient;
import com.medora.PATIENT.processing.service.PatientServiceFacade;
import com.medora.PATIENT.processing.service.facade.management.PatientManagementService;
import com.medora.PATIENT.processing.service.facade.query.PatientQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PatientServiceFacadeImpl implements PatientServiceFacade {

    private final PatientManagementService patientManagementService;
    private final PatientQueryService patientQueryService;

    @Override
    public Patient registerPatient(Patient patient) {
        return patientManagementService.register(patient);
    }

    @Override
    public void removePatient(Patient patient) {
        patientManagementService.remove(patient);
    }
}
