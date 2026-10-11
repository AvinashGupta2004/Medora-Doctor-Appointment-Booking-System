package com.medora.QUEUE.processing.services.facade.management.impl;

import com.medora.DOCTOR.common.exceptions.DoctorNotRegisteredException;
import com.medora.DOCTOR.common.mappers.DoctorMapper;
import com.medora.DOCTOR.persistence.entity.DoctorJpaEntity;
import com.medora.DOCTOR.persistence.entity.DoctorScheduleJpaEntity;
import com.medora.DOCTOR.persistence.repository.DoctorRepository;
import com.medora.DOCTOR.processing.models.Doctor;
import com.medora.QUEUE.common.enums.QueueStatus;
import com.medora.QUEUE.persistence.entity.DoctorShiftQueueJpaEntity;
import com.medora.QUEUE.persistence.repository.DoctorShiftQueueRepository;
import com.medora.QUEUE.processing.services.facade.management.DoctorShiftQueueManagementService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorShiftQueueManagementServiceImpl implements DoctorShiftQueueManagementService {

    private final DoctorShiftQueueRepository doctorShiftQueueRepository;
    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;

    @Override
    public void createDoctorShiftQueuesForNDays(@NonNull Doctor doctor, int n) {

        DoctorJpaEntity doctorJpaEntity = doctorRepository.findDoctorJpaEntityByDoctorRegistrationNumberWithSchedules(
                doctor.getDoctorRegistrationNumber()
        ).orElseThrow(() -> new DoctorNotRegisteredException("Doctor not registered!!"));

        if (doctorJpaEntity.getDoctorSchedules() == null || doctorJpaEntity.getDoctorSchedules().isEmpty()) {
            return;
        }

        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(n - 1);

        List<DoctorShiftQueueJpaEntity> existingQueues = doctorShiftQueueRepository
                .findByDoctorIdAndShiftDateBetween(doctorJpaEntity.getId(), startDate, endDate);

        Set<String> existingQueueKeys = existingQueues.stream()
                .map(q -> q.getShiftDate().toString() + "#" + q.getStartTime().toString())
                .collect(Collectors.toSet());

        List<DoctorShiftQueueJpaEntity> queuesToSave = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            LocalDate targetDate = startDate.plusDays(i);
            DayOfWeek currentDayOfWeek = targetDate.getDayOfWeek();

            for (DoctorScheduleJpaEntity scheduleEntity : doctorJpaEntity.getDoctorSchedules()) {

                if (scheduleEntity.getDayOfWeek().equals(currentDayOfWeek)) {

                    String uniqueKey = targetDate.toString() + "#" + scheduleEntity.getShiftStartTime().toString();

                    if (existingQueueKeys.contains(uniqueKey)) {
                        continue;
                    }

                    DoctorShiftQueueJpaEntity shiftQueue = DoctorShiftQueueJpaEntity.builder()
                            .doctor(doctorJpaEntity)
                            .doctorSchedule(scheduleEntity)
                            .shiftDate(targetDate)
                            .startTime(scheduleEntity.getShiftStartTime())
                            .endTime(scheduleEntity.getShiftEndTime())
                            .maxCapacity(scheduleEntity.getMaxCapacity() != null ? scheduleEntity.getMaxCapacity() : 50) // Default capacity agar null ho
                            .bookedTokens(0)
                            .status(QueueStatus.OPEN)
                            .build();

                    queuesToSave.add(shiftQueue);
                }
            }
        }

        if (!queuesToSave.isEmpty()) {
            doctorShiftQueueRepository.saveAll(queuesToSave);
        }
    }
}
