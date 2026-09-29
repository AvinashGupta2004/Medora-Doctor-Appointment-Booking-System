package com.medora.DOCTOR.processing.service.schedule.facade.query;

import com.medora.DOCTOR.processing.models.Doctor;
import com.medora.DOCTOR.processing.models.DoctorSchedule;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DoctorScheduleQueryService {

    List<DoctorSchedule> getDoctorSchedules(Doctor doctor);
}
