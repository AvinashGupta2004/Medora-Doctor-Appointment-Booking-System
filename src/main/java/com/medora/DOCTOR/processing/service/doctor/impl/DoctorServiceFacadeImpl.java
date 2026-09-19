package com.medora.DOCTOR.processing.service.doctor.impl;

import com.medora.DOCTOR.processing.models.Doctor;
import com.medora.DOCTOR.processing.service.doctor.DoctorServiceFacade;
import com.medora.DOCTOR.processing.service.doctor.facade.management.DoctorManagementService;
import com.medora.DOCTOR.processing.service.doctor.facade.query.DoctorQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DoctorServiceFacadeImpl implements DoctorServiceFacade {

    private final DoctorManagementService doctorManagementService;
    private final DoctorQueryService doctorQueryService;

    @Override
    public Doctor registerDoctor(Doctor doctor) {
        return doctorManagementService.register(doctor);
    }

    @Override
    public void removeDoctor(Doctor doctor) {
        doctorManagementService.remove(doctor);
    }
}
