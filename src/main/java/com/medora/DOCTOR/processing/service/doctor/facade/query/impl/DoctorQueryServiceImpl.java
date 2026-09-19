package com.medora.DOCTOR.processing.service.doctor.facade.query.impl;

import com.medora.DOCTOR.common.exceptions.DoctorNotRegisteredException;
import com.medora.DOCTOR.processing.models.Doctor;
import com.medora.DOCTOR.processing.service.doctor.facade.query.DoctorQueryService;
import com.medora.DOCTOR.processing.service.doctor.facade.query.search.DoctorSearchStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DoctorQueryServiceImpl implements DoctorQueryService {

    private final DoctorSearchStrategy doctorSearchStrategy;

    @Override
    public boolean isDoctorRegistered(Doctor doctor) {
        return doctorSearchStrategy.searchDoctor(doctor).isPresent();
    }

    @Override
    public Doctor getDoctor(Doctor doctor) {
        Optional<Doctor> optionalDoctor = doctorSearchStrategy.searchDoctor(doctor);
        if (optionalDoctor.isEmpty())
            throw new DoctorNotRegisteredException("No Doctor registered with provided details!");

        return optionalDoctor.get();
    }
}
