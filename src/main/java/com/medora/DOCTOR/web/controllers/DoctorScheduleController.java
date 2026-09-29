package com.medora.DOCTOR.web.controllers;

import com.medora.DOCTOR.web.DTOs.REQUEST.DoctorScheduleRequest;
import com.medora.DOCTOR.web.services.DoctorScheduleService;
import com.medora.common.utils.ControllerResponseTemplate;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/doctors/schedules")
@RequiredArgsConstructor
public class DoctorScheduleController {

    private final DoctorScheduleService doctorScheduleService;

    @PostMapping
    public ResponseEntity<?> updateSchedule(@RequestBody @NotNull List<@Valid DoctorScheduleRequest> doctorScheduleRequests) {
        doctorScheduleService.updateSchedule(doctorScheduleRequests);
        HttpStatus status = HttpStatus.OK;

        return ResponseEntity
                .status(status)
                .body(ControllerResponseTemplate.builder()
                        .status(status)
                        .message("Doctor Schedule updated Successfully!")
                        .data(null)
                        .build()
                );
    }
}
