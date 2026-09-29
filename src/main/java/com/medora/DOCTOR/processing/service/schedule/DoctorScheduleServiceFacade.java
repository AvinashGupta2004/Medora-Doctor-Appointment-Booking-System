package com.medora.DOCTOR.processing.service.schedule;

import com.medora.DOCTOR.processing.models.Doctor;
import com.medora.DOCTOR.processing.models.DoctorSchedule;
import com.medora.DOCTOR.web.DTOs.REQUEST.DoctorScheduleRequest;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
public interface DoctorScheduleServiceFacade {

    List<DoctorSchedule> getDoctorSchedules(@NotNull Doctor doctor);

    void updateDoctorSchedule(@NotNull List<DoctorSchedule> doctorSchedules);
}
