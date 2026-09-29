package com.medora.DOCTOR.processing.service.schedule.facade.management;

import com.medora.DOCTOR.processing.models.DoctorSchedule;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DoctorScheduleManagementService {

    void updateSchedule(@NotNull List<DoctorSchedule> doctorSchedules);
}
