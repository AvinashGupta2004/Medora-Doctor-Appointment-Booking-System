package com.medora.APPOINTMENT.common.exceptions;

public class DuplicateAppointmentCreationRequestException extends RuntimeException {
    public DuplicateAppointmentCreationRequestException(String message) {
        super(message);
    }
}
