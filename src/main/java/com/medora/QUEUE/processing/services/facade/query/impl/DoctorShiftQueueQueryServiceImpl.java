package com.medora.QUEUE.processing.services.facade.query.impl;

import com.medora.DOCTOR.common.mappers.DoctorMapper;
import com.medora.DOCTOR.processing.models.Doctor;
import com.medora.DOCTOR.processing.service.doctor.DoctorServiceFacade;
import com.medora.QUEUE.common.enums.QueueStatus;
import com.medora.QUEUE.common.exceptions.NoQueuesAvailableException;
import com.medora.QUEUE.common.exceptions.QueueNotRegisteredException;
import com.medora.QUEUE.persistence.entity.DoctorShiftQueueJpaEntity;
import com.medora.QUEUE.persistence.repository.DoctorShiftQueueRepository;
import com.medora.QUEUE.processing.models.DoctorShiftQueue;
import com.medora.QUEUE.processing.services.facade.query.DoctorShiftQueueQueryService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Validated
public class DoctorShiftQueueQueryServiceImpl implements DoctorShiftQueueQueryService {

    private final DoctorMapper doctorMapper;
    private final DoctorServiceFacade doctorServiceFacade;
    private final DoctorShiftQueueRepository doctorShiftQueueRepository;

    @Override
    public DoctorShiftQueue checkAvailability(@NonNull DoctorShiftQueue shiftQueue) {
        Doctor doctor = doctorServiceFacade.getDoctor(
                Doctor.builder()
                    .doctorRegistrationNumber(shiftQueue.getDoctorRegistrationNumber())
                    .build(),
                false
        );

        shiftQueue.setDoctorRegistrationNumber(doctor.getDoctorRegistrationNumber());
        shiftQueue.setDoctorId(doctor.getId());

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

        int currentAvailability = fetchedShiftQueue.getMaxCapacity() - fetchedShiftQueue.getBookedTokens();

        if (currentAvailability <= 0) {
            fetchedShiftQueue.setStatus(QueueStatus.CLOSED);
            throw new NoQueuesAvailableException("No Available Queues found!");
        }

        return DoctorShiftQueue.builder()
                .doctorId(fetchedShiftQueue.getDoctor().getId())
                .doctorRegistrationNumber(fetchedShiftQueue.getDoctor().getDoctorRegistrationNumber())
                .scheduleId(fetchedShiftQueue.getDoctorSchedule().getId())
                .shiftDate(fetchedShiftQueue.getShiftDate())
                .startTime(fetchedShiftQueue.getStartTime())
                .endTime(fetchedShiftQueue.getEndTime())
                .maxCapacity(fetchedShiftQueue.getMaxCapacity())
                .bookedTokens(fetchedShiftQueue.getBookedTokens())
                .status(fetchedShiftQueue.getStatus())
                .build();

    }
}
