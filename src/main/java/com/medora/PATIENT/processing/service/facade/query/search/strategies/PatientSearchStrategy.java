package com.medora.PATIENT.processing.service.facade.query.search.strategies;

import com.medora.PATIENT.common.enums.PatientSearchContext;
import com.medora.PATIENT.processing.models.Patient;
import jakarta.validation.constraints.NotNull;

import java.util.Optional;

public interface PatientSearchStrategy {

    Optional<Patient> searchPatient(@NotNull Patient patient);

    boolean supports(@NotNull Patient patient, @NotNull PatientSearchContext context);
}
