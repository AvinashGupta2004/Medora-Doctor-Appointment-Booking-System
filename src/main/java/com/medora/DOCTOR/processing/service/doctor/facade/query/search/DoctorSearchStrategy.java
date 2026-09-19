package com.medora.DOCTOR.processing.service.doctor.facade.query.search;

import com.medora.DOCTOR.processing.models.Doctor;
import jakarta.validation.constraints.NotNull;

import java.util.Optional;

public interface DoctorSearchStrategy {

    Optional<Doctor> searchDoctor(@NotNull Doctor doctor);
}
