package com.medora.PATIENT.processing.service.facade.query.search;

import com.medora.PATIENT.processing.models.Patient;
import jakarta.validation.constraints.NotNull;

import java.util.Optional;

public interface PatientSearchStrategy {

    Optional<Patient> getPatient(@NotNull Patient patient);
}
