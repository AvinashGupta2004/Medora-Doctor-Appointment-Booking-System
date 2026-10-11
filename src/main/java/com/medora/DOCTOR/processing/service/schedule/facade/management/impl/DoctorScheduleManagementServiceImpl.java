package com.medora.DOCTOR.processing.service.schedule.facade.management.impl;

import com.medora.DOCTOR.common.exceptions.DoctorNotRegisteredException;
import com.medora.DOCTOR.common.mappers.DoctorMapper;
import com.medora.DOCTOR.common.mappers.DoctorScheduleMapper;
import com.medora.DOCTOR.persistence.entity.DoctorJpaEntity;
import com.medora.DOCTOR.persistence.entity.DoctorScheduleJpaEntity;
import com.medora.DOCTOR.persistence.repository.DoctorRepository;
import com.medora.DOCTOR.persistence.repository.DoctorScheduleRepository;
import com.medora.DOCTOR.processing.models.Doctor;
import com.medora.DOCTOR.processing.models.DoctorSchedule;
import com.medora.DOCTOR.processing.service.doctor.DoctorServiceFacade;
import com.medora.DOCTOR.processing.service.doctor.facade.management.DoctorManagementService;
import com.medora.DOCTOR.processing.service.doctor.facade.query.DoctorQueryService;
import com.medora.DOCTOR.processing.service.schedule.facade.management.DoctorScheduleManagementService;
import com.medora.DOCTOR.web.services.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.*;

@Component
@RequiredArgsConstructor
public class DoctorScheduleManagementServiceImpl implements DoctorScheduleManagementService {

    private final DoctorScheduleRepository doctorScheduleRepository;
    private final DoctorMapper doctorMapper;
    private final DoctorScheduleMapper doctorScheduleMapper;
    private final DoctorServiceFacade doctorServiceFacade;
    private final DoctorRepository doctorRepository;

    @Override
    @Transactional
    public void updateSchedule(List<DoctorSchedule> doctorSchedules) {
        if (doctorSchedules == null || doctorSchedules.isEmpty()) {
            throw new IllegalArgumentException("Doctor Schedules List cannot be Empty!");
        }

        // MERGE INTERVALS ALGORITHM IS NEEDED OVER HERE IN ORDER TO PREVENT DUPLICATES
        List<DoctorSchedule> sortedMergedSchedules = mergeOverlappingSchedule(doctorSchedules);

        Long registrationNumber = sortedMergedSchedules.getFirst().getDoctorRegistrationNumber();
        DoctorJpaEntity targetDoctor = doctorRepository.findFirstByDoctorRegistrationNumberOrMedicalRegistrationNumberOrContactInformation_PrimaryMobileNumberOrContactInformation_PrimaryEmailAddress(
                registrationNumber,
                null,
                null,
                null
        ).orElseThrow(() -> new DoctorNotRegisteredException("Doctor Not Registered!"));

        targetDoctor.getDoctorSchedules().clear();
        doctorRepository.flush();

        for (DoctorSchedule request : sortedMergedSchedules) {
            DoctorScheduleJpaEntity doctorScheduleJpaEntity = doctorScheduleMapper.toEntity(request);
            doctorScheduleJpaEntity.setDoctor(targetDoctor);
            targetDoctor.getDoctorSchedules().add(doctorScheduleJpaEntity);
        }

        doctorRepository.save(targetDoctor);
    }

    private List<DoctorSchedule> mergeOverlappingSchedule(List<DoctorSchedule> schedules) {
        if (schedules == null || schedules.isEmpty())
            return new ArrayList<>();

        List<DoctorSchedule> finalSchedules = new ArrayList<>();

        Comparator<DoctorSchedule> scheduleComparator = Comparator
                .comparing(DoctorSchedule::getDayOfWeek)
                .thenComparing(DoctorSchedule::getShiftStartTime);

        schedules.sort(scheduleComparator);

        for (DoctorSchedule schedule: schedules) {

            if (finalSchedules.isEmpty()) {
                finalSchedules.add(schedule);
                continue;
            }

            DoctorSchedule lastMergedSchedule = finalSchedules.getLast();
            boolean sameDay = schedule.getDayOfWeek().equals(lastMergedSchedule.getDayOfWeek());
            boolean overlapping = sameDay &&
                    schedule.getShiftStartTime().isBefore(lastMergedSchedule.getShiftEndTime());

            if (overlapping) {
                if (schedule.getShiftEndTime().isAfter(lastMergedSchedule.getShiftEndTime())) {
                    lastMergedSchedule.setShiftEndTime(schedule.getShiftEndTime());
                }
                // Optionally merge max capacity if needed:
                lastMergedSchedule.setMaxCapacity(
                        Math.max(lastMergedSchedule.getMaxCapacity(), schedule.getMaxCapacity())
                );
            }
            else {
                finalSchedules.add(schedule);
            }
        }

        return finalSchedules;
    }
}
