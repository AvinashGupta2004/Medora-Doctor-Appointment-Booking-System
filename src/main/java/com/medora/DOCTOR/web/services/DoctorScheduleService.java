package com.medora.DOCTOR.web.services;

import com.medora.DOCTOR.common.mappers.DoctorScheduleMapper;
import com.medora.DOCTOR.processing.service.schedule.DoctorScheduleServiceFacade;
import com.medora.DOCTOR.web.DTOs.REQUEST.DoctorScheduleRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorScheduleService {

    private final DoctorScheduleServiceFacade doctorScheduleServiceFacade;
    private final DoctorScheduleMapper doctorScheduleMapper;

    public void updateSchedule(@NotNull List<@Valid DoctorScheduleRequest> doctorScheduleRequests) {
        doctorScheduleServiceFacade.updateDoctorSchedule(
                doctorScheduleRequests
                        .stream()
                        .map(doctorScheduleMapper::mapDoctorScheduleRequestToModel)
                        .collect(Collectors.toList())
        );
    }
}
