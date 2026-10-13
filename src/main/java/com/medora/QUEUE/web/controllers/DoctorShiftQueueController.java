package com.medora.QUEUE.web.controllers;

import com.medora.QUEUE.web.DTOs.REQUEST.DoctorShiftQueueAvailabilityRequest;
import com.medora.QUEUE.web.DTOs.RESPONSE.DoctorShiftQueueAvailabilityResponse;
import com.medora.QUEUE.web.services.QueueService;
import com.medora.common.utils.ControllerResponseTemplate;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/queues")
@RequiredArgsConstructor
@Validated
public class DoctorShiftQueueController {

    private final QueueService queueService;

    @GetMapping
    public ResponseEntity<?> getAllShiftQueuesByDate(@Valid @RequestBody DoctorShiftQueueAvailabilityRequest request) {
        List<DoctorShiftQueueAvailabilityResponse> response = queueService.getAllShiftQueuesForDate(request);
        HttpStatus status = HttpStatus.OK;

        return ResponseEntity
                .status(status)
                .body(ControllerResponseTemplate.builder()
                        .status(status)
                        .message("SHIFT QUEUES AVAILABLE!")
                        .data(response)
                        .build()
                );
    }

    @GetMapping("/availability")
    public ResponseEntity<?> checkAvailability(@Valid @RequestBody DoctorShiftQueueAvailabilityRequest request) {
        DoctorShiftQueueAvailabilityResponse response = queueService.checkAvailability(request);
        HttpStatus status = HttpStatus.OK;

        return ResponseEntity
                .status(status)
                .body(ControllerResponseTemplate.builder()
                        .status(status)
                        .message("SHIFT QUEUES AVAILABLE!")
                        .data(response)
                        .build()
                );
    }
}
