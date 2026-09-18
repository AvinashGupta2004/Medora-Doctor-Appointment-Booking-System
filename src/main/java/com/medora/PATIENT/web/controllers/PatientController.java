package com.medora.PATIENT.web.controllers;

import com.medora.PATIENT.web.DTOs.REQUEST.PatientDeletionRequest;
import com.medora.PATIENT.web.DTOs.REQUEST.PatientRegistrationRequest;
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
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(patientService.registerNewPatient(patientRegistrationRequest));
    }

    @DeleteMapping
    public ResponseEntity<?> deletePatient(
            @Valid @RequestBody PatientDeletionRequest patientDeletionRequest
    ) {
        patientService.removePatient(patientDeletionRequest);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Patient deleted Successfully!");
    }
}
