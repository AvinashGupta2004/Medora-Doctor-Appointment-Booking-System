package com.medora.QUEUE.web.services;

import com.medora.DOCTOR.common.mappers.DoctorMapper;
import com.medora.QUEUE.processing.models.DoctorShiftQueue;
import com.medora.QUEUE.processing.services.DoctorShiftQueueServiceFacade;
import com.medora.QUEUE.web.DTOs.REQUEST.DoctorShiftQueueAvailabilityRequest;
import com.medora.QUEUE.web.DTOs.RESPONSE.DoctorShiftQueueAvailabilityResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Slf4j
@Service
@Validated
@RequiredArgsConstructor
public class QueueService {

    private final DoctorMapper doctorMapper;
    private final DoctorShiftQueueServiceFacade doctorShiftQueueServiceFacade;

    public DoctorShiftQueueAvailabilityResponse checkAvailability(@NonNull DoctorShiftQueueAvailabilityRequest request) {
        DoctorShiftQueue shiftQueue = DoctorShiftQueue.builder()
                .doctorRegistrationNumber(
                        doctorMapper.toLong(request.doctorRegistrationNumber())
                )
                .shiftDate(request.shiftDate())
                .startTime(request.startTime())
                .build();

        DoctorShiftQueue fetchedShiftQueue = doctorShiftQueueServiceFacade.checkAvailability(shiftQueue);

        return new DoctorShiftQueueAvailabilityResponse(
                doctorMapper.toRegistrationNumber(fetchedShiftQueue.getDoctorRegistrationNumber()),
                fetchedShiftQueue.getShiftDate(),
                fetchedShiftQueue.getStartTime(),
                fetchedShiftQueue.getEndTime(),
                fetchedShiftQueue.getMaxCapacity() - fetchedShiftQueue.getBookedTokens(),
                fetchedShiftQueue.getStatus()
        );
    }

    public List<DoctorShiftQueueAvailabilityResponse> getAllShiftQueuesForDate(@NonNull DoctorShiftQueueAvailabilityRequest request) {
        DoctorShiftQueue shiftQueue = DoctorShiftQueue.builder()
                .doctorRegistrationNumber(
                        doctorMapper.toLong(request.doctorRegistrationNumber())
                )
                .shiftDate(request.shiftDate())
                .build();

        return doctorShiftQueueServiceFacade.getAllShiftQueuesForDate(shiftQueue).stream().map(queue -> new DoctorShiftQueueAvailabilityResponse(
                doctorMapper.toRegistrationNumber(queue.getDoctorRegistrationNumber()),
                queue.getShiftDate(),
                queue.getStartTime(),
                queue.getEndTime(),
                queue.getMaxCapacity() - queue.getBookedTokens(),
                queue.getStatus()
        )).toList();
    }
}
