package com.medora.DOCTOR.processing.service.schedule.impl;

import com.medora.DOCTOR.processing.models.Doctor;
import com.medora.DOCTOR.processing.models.DoctorSchedule;
import com.medora.DOCTOR.processing.service.schedule.DoctorScheduleServiceFacade;
import com.medora.DOCTOR.processing.service.schedule.facade.management.DoctorScheduleManagementService;
import com.medora.DOCTOR.processing.service.schedule.facade.query.DoctorScheduleQueryService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Component
@RequiredArgsConstructor
@Validated
public class DoctorScheduleServiceFacadeImpl implements DoctorScheduleServiceFacade {

    private final DoctorScheduleManagementService doctorScheduleManagementService;
    private final DoctorScheduleQueryService doctorScheduleQueryService;

    @Override
    public List<DoctorSchedule> getDoctorSchedules(@NotNull Doctor doctor) {
        return doctorScheduleQueryService.getDoctorSchedules(doctor);
    }

    @Override
    public void updateDoctorSchedule(List<DoctorSchedule> doctorSchedules) {
        doctorScheduleManagementService.updateSchedule(doctorSchedules);
    }
}
