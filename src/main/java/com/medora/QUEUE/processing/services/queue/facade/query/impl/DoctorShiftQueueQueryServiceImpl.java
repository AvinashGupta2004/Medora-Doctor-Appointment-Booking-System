package com.medora.QUEUE.processing.services.queue.facade.query.impl;

import com.medora.DOCTOR.common.mappers.DoctorMapper;
import com.medora.DOCTOR.processing.models.Doctor;
import com.medora.DOCTOR.processing.service.doctor.DoctorServiceFacade;
import com.medora.QUEUE.common.enums.DoctorShiftQueueSearchContext;
import com.medora.QUEUE.common.enums.QueueStatus;
import com.medora.QUEUE.common.exceptions.NoQueuesAvailableException;
import com.medora.QUEUE.common.exceptions.NoSuitableStrategyFoundException;
import com.medora.QUEUE.common.exceptions.QueueNotRegisteredException;
import com.medora.QUEUE.common.mappers.DoctorShiftQueueMapper;
import com.medora.QUEUE.persistence.entity.DoctorShiftQueueJpaEntity;
import com.medora.QUEUE.persistence.repository.DoctorShiftQueueRepository;
import com.medora.QUEUE.processing.models.DoctorShiftQueue;
import com.medora.QUEUE.processing.services.queue.facade.query.DoctorShiftQueueQueryService;
import com.medora.QUEUE.processing.services.queue.facade.query.search.DoctorShiftQueueSearchRegistry;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import java.util.List;

@Service
@RequiredArgsConstructor
@Validated
@Transactional(readOnly = true)
public class DoctorShiftQueueQueryServiceImpl implements DoctorShiftQueueQueryService {

    private final DoctorMapper doctorMapper;
    private final DoctorServiceFacade doctorServiceFacade;
    private final DoctorShiftQueueSearchRegistry queueSearchRegistry;

    @Override
    public DoctorShiftQueue checkAvailability(@NonNull DoctorShiftQueue shiftQueue) {
        DoctorShiftQueue fetchedShiftQueue = this.getShiftQueue(shiftQueue);

        int currentAvailability = fetchedShiftQueue.getMaxCapacity() - fetchedShiftQueue.getTotalActiveBookings();

        if (currentAvailability <= 0) {
            fetchedShiftQueue.setStatus(QueueStatus.CLOSED);
            throw new NoQueuesAvailableException("No Available Queues found!");
        }

        return fetchedShiftQueue;
    }

    @Override
    public DoctorShiftQueue getShiftQueue(@NonNull DoctorShiftQueue shiftQueue) {
        if (shiftQueue.getId() != null) {
            return queueSearchRegistry.search(shiftQueue, DoctorShiftQueueSearchContext.SINGLE_QUEUE_ID)
                    .getFirst()
                    .orElseThrow(() -> new NoQueuesAvailableException("No Queue found for given parameters!"));
        }
        Doctor doctor = doctorServiceFacade.getDoctor(
                Doctor.builder()
                        .doctorRegistrationNumber(shiftQueue.getDoctorRegistrationNumber())
                        .build(),
                false
        );

        shiftQueue.setDoctorId(doctor.getId());
        shiftQueue.setDoctorRegistrationNumber(doctor.getDoctorRegistrationNumber());

        return queueSearchRegistry
                .search(shiftQueue, DoctorShiftQueueSearchContext.SINGLE_DOCTOR_SHIFT_TIME_WINDOW)
                .getFirst()
                .orElseThrow(() -> new NoQueuesAvailableException("No Queue found for given parameters!"));
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

        return queueSearchRegistry
                .search(shiftQueue, DoctorShiftQueueSearchContext.MANY_DOCTOR_SHIFT_DATE)
                .getItems();
    }
}
