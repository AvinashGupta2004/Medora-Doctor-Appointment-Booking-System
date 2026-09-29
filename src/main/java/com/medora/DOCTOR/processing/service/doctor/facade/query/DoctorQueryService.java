package com.medora.DOCTOR.processing.service.doctor.facade.query;

import com.medora.DOCTOR.common.enums.SearchContext;
import com.medora.DOCTOR.processing.models.Doctor;
import com.medora.DOCTOR.processing.service.doctor.facade.query.search.DoctorSearchStrategy;
import com.medora.PATIENT.processing.models.Patient;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DoctorQueryService {

    boolean isDoctorRegistered(@NotNull Doctor doctor);
    Doctor getDoctor(@NotNull Doctor doctor, boolean loadWithSchedule);
}
