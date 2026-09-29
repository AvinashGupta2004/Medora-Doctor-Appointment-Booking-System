package com.medora.DOCTOR.processing.service.schedule.facade.query.impl;

import com.medora.DOCTOR.common.mappers.DoctorMapper;
import com.medora.DOCTOR.common.mappers.DoctorScheduleMapper;
import com.medora.DOCTOR.persistence.entity.DoctorJpaEntity;
import com.medora.DOCTOR.persistence.repository.DoctorScheduleRepository;
import com.medora.DOCTOR.processing.models.Doctor;
import com.medora.DOCTOR.processing.models.DoctorSchedule;
import com.medora.DOCTOR.processing.service.doctor.facade.query.DoctorQueryService;
import com.medora.DOCTOR.processing.service.schedule.facade.query.DoctorScheduleQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DoctorScheduleQueryServiceImpl implements DoctorScheduleQueryService {

    private final DoctorQueryService doctorQueryService;
    private final DoctorScheduleRepository doctorScheduleRepository;
    private final DoctorScheduleMapper doctorScheduleMapper;
    private final DoctorMapper doctorMapper;

    @Override
    public List<DoctorSchedule> getDoctorSchedules(Doctor doctor) {
        Doctor registeredDoctor = doctorQueryService.getDoctor(doctor, true);
        return registeredDoctor.getDoctorSchedules();
    }
}
