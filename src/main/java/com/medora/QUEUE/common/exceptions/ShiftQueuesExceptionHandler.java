package com.medora.QUEUE.common.exceptions;

import com.medora.common.utils.ControllerResponseTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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

    @ExceptionHandler(NoQueuesAvailableException.class)
    public ResponseEntity<?> handleNoQueuesAvailableException(NoQueuesAvailableException e) {
        HttpStatus status = HttpStatus.NOT_FOUND;

        return ResponseEntity
                .status(status)
                .body(ControllerResponseTemplate.builder()
                        .status(status)
                        .data(null)
                        .message(e.getMessage())
                        .build());
    }
}
