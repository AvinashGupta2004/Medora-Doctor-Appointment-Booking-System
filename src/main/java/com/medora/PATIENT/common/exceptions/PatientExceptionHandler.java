package com.medora.PATIENT.common.exceptions;

import com.medora.PATIENT.common.exceptions.RESPONSE.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class PatientExceptionHandler {

    @ExceptionHandler(PatientAlreadyRegisteredException.class)
    public ResponseEntity<?> handlePatientAlreadyRegisteredException(PatientAlreadyRegisteredException e) {
        HttpStatus status = HttpStatus.CONFLICT;

        return ResponseEntity
                .status(status)
                .body(ErrorResponse.builder()
                        .status(status)
                        .message(e.getMessage())
                        .data(null)
                        .build()
                );
    }

    @ExceptionHandler(PatientNotRegisteredException.class)
    public ResponseEntity<?> handlePatientNotRegisteredException(PatientNotRegisteredException e) {
        HttpStatus status = HttpStatus.CONFLICT;

        return ResponseEntity
                .status(status)
                .body(ErrorResponse.builder()
                        .status(status)
                        .message(e.getMessage())
                        .data(null)
                        .build()
                );
    }
}
