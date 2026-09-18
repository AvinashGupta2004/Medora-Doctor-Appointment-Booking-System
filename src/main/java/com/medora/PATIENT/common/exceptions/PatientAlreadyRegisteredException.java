package com.medora.PATIENT.common.exceptions;

public class PatientAlreadyRegisteredException extends RuntimeException {
    public PatientAlreadyRegisteredException(String message) {
        super(message);
    }
}
