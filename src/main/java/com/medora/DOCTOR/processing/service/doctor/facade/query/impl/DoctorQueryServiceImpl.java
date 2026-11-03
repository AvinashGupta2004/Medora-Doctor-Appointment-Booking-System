package com.medora.DOCTOR.processing.service.doctor.facade.query.impl;

import com.medora.DOCTOR.common.enums.DoctorSearchContext;
import com.medora.DOCTOR.common.exceptions.DoctorNotRegisteredException;
import com.medora.DOCTOR.processing.models.Doctor;
import com.medora.DOCTOR.processing.service.doctor.facade.query.DoctorQueryService;
import com.medora.DOCTOR.processing.service.doctor.facade.query.search.DoctorSearchStrategy;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DoctorQueryServiceImpl implements DoctorQueryService {

    private final List<DoctorSearchStrategy> searchStrategies;

    @Override
    public boolean isDoctorRegistered(Doctor doctor) {
        return resolveStrategy(doctor, DoctorSearchContext.DUPLICATE_CHECK)
                .searchDoctor(doctor, false)
                .isPresent();
    }

    @Override
    public Doctor getDoctor(Doctor doctor, boolean loadWithSchedule) {
        Optional<Doctor> optionalDoctor = resolveStrategy(doctor, DoctorSearchContext.SINGLE_FETCH)
        .searchDoctor(doctor, loadWithSchedule);
        if (optionalDoctor.isEmpty())
            throw new DoctorNotRegisteredException("No Doctor registered with provided details!");

        return optionalDoctor.get();
    }

    private DoctorSearchStrategy resolveStrategy(@NotNull Doctor doctor, DoctorSearchContext doctorSearchContext) {
        return searchStrategies.stream()
                .filter(s -> s.supports(doctor, doctorSearchContext))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No search strategy compatible for given data!"));
    }
}
