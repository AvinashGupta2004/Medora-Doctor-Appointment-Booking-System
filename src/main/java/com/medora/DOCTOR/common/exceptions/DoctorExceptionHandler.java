package com.medora.DOCTOR.common.exceptions;

import com.medora.common.exception.RESPONSE.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class DoctorExceptionHandler {

    @ExceptionHandler(DoctorAlreadyRegisteredException.class)
    public ResponseEntity<?> handleDoctorAlreadyRegisteredException(DoctorAlreadyRegisteredException e) {
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

    @ExceptionHandler(DoctorNotRegisteredException.class)
    public ResponseEntity<?> handleDoctorNotRegisteredException(DoctorNotRegisteredException e) {
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
