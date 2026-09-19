package com.medora.DOCTOR.processing.service.doctor.facade.management;

import com.medora.DOCTOR.processing.models.Doctor;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

@Service
public interface DoctorManagementService {

    Doctor register(@NotNull Doctor doctor);
    void remove(@NotNull Doctor doctor);
}
