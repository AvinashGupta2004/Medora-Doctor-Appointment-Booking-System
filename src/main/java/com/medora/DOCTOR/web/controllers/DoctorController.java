package com.medora.DOCTOR.web.controllers;

import com.medora.DOCTOR.web.DTOs.REQUEST.DoctorDeletionRequest;
import com.medora.DOCTOR.web.DTOs.REQUEST.DoctorRegistrationRequest;
import com.medora.DOCTOR.web.DTOs.RESPONSE.DoctorRegistrationResponse;
import com.medora.DOCTOR.web.services.DoctorService;
import com.medora.common.utils.ControllerResponseTemplate;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/doctors")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;

    @PostMapping
    public ResponseEntity<?> registerNewDoctor(@Valid @RequestBody DoctorRegistrationRequest request) {

        HttpStatus status = HttpStatus.OK;
        DoctorRegistrationResponse doctorRegistrationResponse = doctorService.registerNewDoctor(request);
        return ResponseEntity
                .status(status)
                .body(ControllerResponseTemplate.builder()
                        .status(status)
                        .message("Doctor Registered Successfully!")
                        .data(doctorRegistrationResponse)
                        .build()
                );
    }

    @DeleteMapping
    public ResponseEntity<?> deleteDoctor(@Valid @RequestBody DoctorDeletionRequest request) {
        HttpStatus status = HttpStatus.NO_CONTENT;

        return ResponseEntity
                .status(status)
                .body(ControllerResponseTemplate.builder()
                        .status(status)
                        .message("Doctor Deleted Successfully!")
                        .data(null)
                );
    }
}
