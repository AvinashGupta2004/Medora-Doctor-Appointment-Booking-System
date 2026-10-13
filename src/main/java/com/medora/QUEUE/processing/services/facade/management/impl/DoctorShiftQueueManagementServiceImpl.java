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
import com.medora.QUEUE.processing.models.DoctorShiftQueue;
import com.medora.QUEUE.processing.services.facade.management.DoctorShiftQueueManagementService;
import com.medora.QUEUE.processing.services.facade.query.DoctorShiftQueueQueryService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Validated
@Transactional
public class DoctorShiftQueueManagementServiceImpl implements DoctorShiftQueueManagementService {

    private final DoctorShiftQueueQueryService shiftQueueQueryService;
    private final DoctorShiftQueueRepository doctorShiftQueueRepository;
    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;

    @Override
    public Integer allocateToken(@NonNull DoctorShiftQueue shiftQueue) {
        // Work Order:
        // 1. Check Availability for given details.
        // 2. If Available, fetch the Doctor Shift Queue Jpa Entity
        // 3.
        return 0;
    }
}
