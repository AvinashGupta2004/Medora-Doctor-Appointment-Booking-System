package com.medora.PATIENT.processing.service.facade.query;

import com.medora.PATIENT.processing.models.Patient;
import org.springframework.stereotype.Service;

@Service
public interface PatientQueryService {

    boolean isPatientRegistered(Patient patient);

    Patient getPatient(Patient patient);
}
