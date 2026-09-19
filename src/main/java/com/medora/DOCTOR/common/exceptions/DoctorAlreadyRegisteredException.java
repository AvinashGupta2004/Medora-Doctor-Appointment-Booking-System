package com.medora.DOCTOR.common.exceptions;

public class DoctorAlreadyRegisteredException extends RuntimeException {
    public DoctorAlreadyRegisteredException(String message) {
        super(message);
    }
}
