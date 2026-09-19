package com.medora.DOCTOR.processing.service.doctor;

import com.medora.DOCTOR.processing.models.Doctor;
import jakarta.validation.constraints.NotNull;

public interface DoctorServiceFacade {

    Doctor registerDoctor(@NotNull Doctor doctor);

    void removeDoctor(@NotNull Doctor doctor);
}
