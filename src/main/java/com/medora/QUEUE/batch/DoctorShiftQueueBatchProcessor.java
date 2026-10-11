package com.medora.QUEUE.batch;

import com.medora.DOCTOR.persistence.entity.DoctorJpaEntity;
import com.medora.DOCTOR.persistence.entity.DoctorScheduleJpaEntity;
import com.medora.QUEUE.common.enums.QueueStatus;
import com.medora.QUEUE.persistence.entity.DoctorShiftQueueJpaEntity;
import com.medora.QUEUE.persistence.repository.DoctorShiftQueueRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DoctorShiftQueueBatchProcessor implements ItemProcessor<DoctorJpaEntity, List<DoctorShiftQueueJpaEntity>> {

    private final DoctorShiftQueueRepository doctorShiftQueueRepository;

    @Override
    public @Nullable List<DoctorShiftQueueJpaEntity> process(DoctorJpaEntity doctorJpaEntity) throws Exception {
        if (doctorJpaEntity == null || doctorJpaEntity.getDoctorSchedules().isEmpty())
            return List.of();

        int daysToGenerate = 7;
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(daysToGenerate - 1);

        List<DoctorShiftQueueJpaEntity> existingQueues = doctorShiftQueueRepository
                .findByDoctorIdAndShiftDateBetween(doctorJpaEntity.getId(), startDate, endDate);

        Set<String> existingQueueKeys = existingQueues.stream()
                .map(q -> q.getShiftDate().toString() + "#" + q.getStartTime().toString())
                .collect(Collectors.toSet());

        List<DoctorShiftQueueJpaEntity> queuesToSave = new ArrayList<>();

        for (int i = 0; i < daysToGenerate; i++) {
            LocalDate targetDate = startDate.plusDays(i);
            DayOfWeek currentDayOfWeek = targetDate.getDayOfWeek();

            for (DoctorScheduleJpaEntity scheduleEntity : doctorJpaEntity.getDoctorSchedules()) {
                if (!Boolean.TRUE.equals(scheduleEntity.getActive())) {
                    continue;
                }

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
                            .maxCapacity(scheduleEntity.getMaxCapacity() != null ? scheduleEntity.getMaxCapacity() : 50)
                            .bookedTokens(0)
                            .status(QueueStatus.OPEN)
                            .build();

                    queuesToSave.add(shiftQueue);
                }
            }
        }

        return queuesToSave;
    }
}
