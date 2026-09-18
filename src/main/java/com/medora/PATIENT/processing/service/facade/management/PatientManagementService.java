package com.medora.PATIENT.processing.service.facade.management;

import com.medora.PATIENT.processing.models.Patient;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

@Service
public interface PatientManagementService {

    Patient register(@NotNull Patient patient);

    void remove(@NotNull Patient patient);
}
