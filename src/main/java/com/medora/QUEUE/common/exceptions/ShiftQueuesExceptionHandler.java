package com.medora.QUEUE.common.exceptions;

import com.medora.common.utils.ControllerResponseTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Properties;

@RestControllerAdvice
public class ShiftQueuesExceptionHandler {

    @ExceptionHandler(QueueNotRegisteredException.class)
    public ResponseEntity<?> handleQueueNotRegisteredException(QueueNotRegisteredException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        return ResponseEntity
                .status(status)
                .body(ControllerResponseTemplate.builder()
                        .status(status)
                        .message("Queue is not registered for Doctor {" + e.getDoctorRegistrationNumber() + "}, for given Shift Date {"+ e.getShiftDate()+"}, for given Start Time {"+e.getStartTime()+"}")
                        .data(null)
                        .build()
                );
    }
}
