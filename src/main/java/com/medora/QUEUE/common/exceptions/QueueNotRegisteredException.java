package com.medora.QUEUE.common.exceptions;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
public class QueueNotRegisteredException extends RuntimeException {
    private final String doctorRegistrationNumber;
    private final LocalDate shiftDate;
    private final LocalTime startTime;

    public QueueNotRegisteredException(String message, String doctorRegistrationNumber, LocalDate shiftDate, LocalTime startTime) {
        super(message);
        this.doctorRegistrationNumber = doctorRegistrationNumber;
        this.shiftDate = shiftDate;
        this.startTime = startTime;
    }
}
