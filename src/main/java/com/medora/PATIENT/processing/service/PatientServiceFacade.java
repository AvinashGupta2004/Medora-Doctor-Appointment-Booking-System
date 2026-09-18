package com.medora.PATIENT.processing.service;

import com.medora.PATIENT.processing.models.Patient;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface PatientServiceFacade {

    Patient registerPatient(@NotNull Patient patient);
    void removePatient(@NotNull Patient patient);
}
