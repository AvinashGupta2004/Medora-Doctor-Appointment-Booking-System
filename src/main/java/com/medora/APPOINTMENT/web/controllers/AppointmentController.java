package com.medora.APPOINTMENT.web.controllers;

import com.medora.APPOINTMENT.web.DTOs.REQUEST.AppointmentCreationRequest;
import com.medora.APPOINTMENT.web.DTOs.REQUEST.AppointmentSearchRequest;
import com.medora.APPOINTMENT.web.DTOs.RESPONSE.AppointmentCreationResponse;
import com.medora.APPOINTMENT.web.services.AppointmentService;
import com.medora.common.utils.ControllerResponseTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping
    public ResponseEntity<?> createNewAppointment(@RequestBody AppointmentCreationRequest request) {

        AppointmentCreationResponse response = appointmentService.bookNewAppointment(request);
        HttpStatus status = HttpStatus.OK;

        return ResponseEntity
                .status(status)
                .body(ControllerResponseTemplate
                    .builder()
                    .status(status)
                    .message("Appointment booked Successfully!")
                    .data(response)
                    .build()
                );
    }

    @DeleteMapping
    public ResponseEntity<?> cancelAppointment(@RequestBody AppointmentSearchRequest request) {
        appointmentService.cancelAppointment(request);
        HttpStatus status = HttpStatus.OK;

        return ResponseEntity
                .status(status)
                .body(ControllerResponseTemplate
                        .builder()
                        .status(status)
                        .message("Appointment Cancelled Successfully!")
                        .data(null)
                        .build()
                );
    }
}
