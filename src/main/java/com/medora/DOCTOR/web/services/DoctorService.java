package com.medora.DOCTOR.web.services;

import com.medora.DOCTOR.common.mappers.DoctorMapper;
import com.medora.DOCTOR.processing.models.Doctor;
import com.medora.DOCTOR.processing.service.doctor.DoctorServiceFacade;
import com.medora.DOCTOR.web.DTOs.REQUEST.DoctorDeletionRequest;
import com.medora.DOCTOR.web.DTOs.REQUEST.DoctorRegistrationRequest;
import com.medora.DOCTOR.web.DTOs.RESPONSE.DoctorDeletionResponse;
import com.medora.DOCTOR.web.DTOs.RESPONSE.DoctorRegistrationResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
@Validated
public class DoctorService {

    private final DoctorServiceFacade doctorServiceFacade;
    private final DoctorMapper doctorMapper;

    public DoctorRegistrationResponse registerNewDoctor(
            @Valid @RequestBody DoctorRegistrationRequest doctorRegistrationRequest
    ) {
        Doctor doctor = doctorMapper.mapRegistrationRequestToModel(doctorRegistrationRequest);
        Doctor registeredDoctor = doctorServiceFacade.registerDoctor(doctor);
        return doctorMapper.mapModelToRegistrationResponse(registeredDoctor);
    }

    DoctorDeletionResponse deleteDoctor(
            @Valid @RequestBody DoctorDeletionRequest doctorDeletionRequest
    ) {
        Doctor doctor = doctorMapper.mapDeletionRequestToModel(doctorDeletionRequest);
        doctorServiceFacade.removeDoctor(doctor);
        return doctorMapper.mapModelToDeletionResponse(doctor);
    }
}
