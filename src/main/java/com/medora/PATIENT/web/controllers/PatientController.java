package com.medora.PATIENT.web.controllers;

import com.medora.common.utils.ControllerResponseTemplate;
import com.medora.PATIENT.web.DTOs.REQUEST.PatientDeletionRequest;
import com.medora.PATIENT.web.DTOs.REQUEST.PatientRegistrationRequest;
import com.medora.PATIENT.web.DTOs.RESPONSE.PatientRegistrationResponse;
import com.medora.PATIENT.web.services.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
@Validated
public class PatientController {

    private final PatientService patientService;

    @PostMapping
    public ResponseEntity<?> registerPatient(
            @Valid @RequestBody PatientRegistrationRequest patientRegistrationRequest
    ) {
        HttpStatus status = HttpStatus.OK;
        PatientRegistrationResponse response = patientService.registerNewPatient(patientRegistrationRequest);

        return ResponseEntity
                .status(status)
                .body(ControllerResponseTemplate.builder()
                        .status(status)
                        .message("Patient registered Successfully!")
                        .data(response)
                        .build()
                );
    }

    @DeleteMapping
    public ResponseEntity<?> deletePatient(
            @Valid @RequestBody PatientDeletionRequest patientDeletionRequest
    ) {
        patientService.removePatient(patientDeletionRequest);
        HttpStatus status = HttpStatus.OK;

        return ResponseEntity
                .status(status)
                .body(ControllerResponseTemplate.builder()
                        .status(status)
                        .message("Patient deleted Successfully!")
                        .data(null)
                        .build()
                );
    }
}
