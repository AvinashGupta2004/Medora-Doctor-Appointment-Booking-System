package com.medora.DOCTOR.processing.service.doctor.facade.query;

import com.medora.DOCTOR.processing.models.Doctor;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

@Service
public interface DoctorQueryService {

    boolean isDoctorRegistered(@NotNull Doctor doctor);
    Doctor getDoctor(@NotNull Doctor doctor, boolean loadWithSchedule);
}
