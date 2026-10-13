package com.medora.QUEUE.processing.services.facade.query.impl;

import com.medora.DOCTOR.common.mappers.DoctorMapper;
import com.medora.DOCTOR.processing.models.Doctor;
import com.medora.DOCTOR.processing.service.doctor.DoctorServiceFacade;
import com.medora.QUEUE.common.enums.QueueStatus;
import com.medora.QUEUE.common.exceptions.NoQueuesAvailableException;
import com.medora.QUEUE.common.exceptions.QueueNotRegisteredException;
import com.medora.QUEUE.common.mappers.DoctorShiftQueueMapper;
import com.medora.QUEUE.persistence.entity.DoctorShiftQueueJpaEntity;
import com.medora.QUEUE.persistence.repository.DoctorShiftQueueRepository;
import com.medora.QUEUE.processing.models.DoctorShiftQueue;
import com.medora.QUEUE.processing.services.facade.query.DoctorShiftQueueQueryService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Validated
@Transactional(readOnly = true)
public class DoctorShiftQueueQueryServiceImpl implements DoctorShiftQueueQueryService {

    private final DoctorMapper doctorMapper;
    private final DoctorServiceFacade doctorServiceFacade;
    private final DoctorShiftQueueRepository doctorShiftQueueRepository;
    private final DoctorShiftQueueMapper doctorShiftQueueMapper;

    @Override
    public DoctorShiftQueue checkAvailability(@NonNull DoctorShiftQueue shiftQueue) {
        DoctorShiftQueue fetchedShiftQueue = this.getShiftQueue(shiftQueue);

        int currentAvailability = fetchedShiftQueue.getMaxCapacity() - fetchedShiftQueue.getBookedTokens();

        if (currentAvailability <= 0) {
            fetchedShiftQueue.setStatus(QueueStatus.CLOSED);
            throw new NoQueuesAvailableException("No Available Queues found!");
        }

        return fetchedShiftQueue;
    }

    @Override
    public DoctorShiftQueue getShiftQueue(DoctorShiftQueue shiftQueue) {
        Doctor doctor = doctorServiceFacade.getDoctor(
                Doctor.builder()
                        .doctorRegistrationNumber(shiftQueue.getDoctorRegistrationNumber())
                        .build(),
                false
        );

        shiftQueue.setDoctorId(doctor.getId());
        shiftQueue.setDoctorRegistrationNumber(doctor.getDoctorRegistrationNumber());

        DoctorShiftQueueJpaEntity fetchedShiftQueue = doctorShiftQueueRepository
                .findByDoctorIdAndShiftDateAndStartTime(
                        shiftQueue.getDoctorId(),
                        shiftQueue.getShiftDate(),
                        shiftQueue.getStartTime()
                )
                .orElseThrow(() -> new QueueNotRegisteredException(
                        "Doctor Shift Queue is not registered for given parameters!",
                        doctorMapper.toRegistrationNumber(shiftQueue.getDoctorRegistrationNumber()),
                        shiftQueue.getShiftDate(),
                        shiftQueue.getStartTime()
                ));

        return doctorShiftQueueMapper.toModel(fetchedShiftQueue);
//        return DoctorShiftQueue.builder()
//                .id(fetchedShiftQueue.getId())
//                .doctorId(fetchedShiftQueue.getDoctor().getId())
//                .scheduleId(fetchedShiftQueue.getDoctorSchedule().getId())
//                .doctorRegistrationNumber(fetchedShiftQueue.getDoctor().getDoctorRegistrationNumber())
//                .shiftDate(fetchedShiftQueue.getShiftDate())
//                .startTime(fetchedShiftQueue.getStartTime())
//                .endTime(fetchedShiftQueue.getEndTime())
//                .maxCapacity(fetchedShiftQueue.getMaxCapacity())
//                .bookedTokens(fetchedShiftQueue.getBookedTokens())
//                .status(fetchedShiftQueue.getStatus())
//                .build();
    }

    @Override
    public List<DoctorShiftQueue> getAllShiftQueuesForDate(DoctorShiftQueue shiftQueue) {
        Doctor doctor = doctorServiceFacade.getDoctor(
                Doctor.builder()
                        .doctorRegistrationNumber(shiftQueue.getDoctorRegistrationNumber())
                        .build(),
                false
        );

        shiftQueue.setDoctorId(doctor.getId());
        shiftQueue.setDoctorRegistrationNumber(doctor.getDoctorRegistrationNumber());

        List<DoctorShiftQueueJpaEntity> fetchedShiftQueueJpaEntities = doctorShiftQueueRepository
                .findByDoctorIdAndShiftDate(
                        shiftQueue.getDoctorId(),
                        shiftQueue.getShiftDate()
                );

        if (fetchedShiftQueueJpaEntities.isEmpty())
            throw new QueueNotRegisteredException(
                    "Doctor Shift Queue is not registered for given parameters!",
                    doctorMapper.toRegistrationNumber(shiftQueue.getDoctorRegistrationNumber()),
                    shiftQueue.getShiftDate(),
                    shiftQueue.getStartTime()
            );

        return fetchedShiftQueueJpaEntities.stream().map(doctorShiftQueueMapper::toModel).toList();
    }
}
