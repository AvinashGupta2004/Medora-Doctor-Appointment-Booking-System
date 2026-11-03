package com.medora.APPOINTMENT.processing.services.facade.management.impl;

import com.medora.APPOINTMENT.common.enums.AppointmentStatus;
import com.medora.APPOINTMENT.common.exceptions.AppointmentNotFoundException;
import com.medora.APPOINTMENT.common.exceptions.DuplicateAppointmentCreationRequestException;
import com.medora.APPOINTMENT.common.exceptions.InvalidStateTransitionException;
import com.medora.APPOINTMENT.common.exceptions.TokenNumberNotIssuedException;
import com.medora.APPOINTMENT.common.mappers.AppointmentMapper;
import com.medora.APPOINTMENT.common.utils.IdempotencyKeyGenerator;
import com.medora.APPOINTMENT.persistence.entity.AppointmentIdempotencyJpaEntity;
import com.medora.APPOINTMENT.persistence.entity.AppointmentJpaEntity;
import com.medora.APPOINTMENT.persistence.repository.AppointmentIdempotencyRepository;
import com.medora.APPOINTMENT.persistence.repository.AppointmentRepository;
import com.medora.APPOINTMENT.processing.models.Appointment;
import com.medora.APPOINTMENT.processing.services.facade.management.AppointmentManagementService;
import com.medora.QUEUE.persistence.repository.DoctorShiftQueueRepository;
import com.medora.QUEUE.processing.models.DoctorShiftQueue;
import com.medora.QUEUE.processing.services.queue.DoctorShiftQueueServiceFacade;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.InvalidPropertiesFormatException;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
@Transactional
@Validated
public class AppointmentManagementServiceImpl implements AppointmentManagementService {

    private final AppointmentMapper appointmentMapper;
    private final AppointmentRepository appointmentRepository;
    private final AppointmentIdempotencyRepository appointmentIdempotencyRepository;
    private final DoctorShiftQueueServiceFacade doctorShiftQueueServiceFacade;
    private final DoctorShiftQueueRepository doctorShiftQueueRepository;

    @Override
    public Appointment bookAppointment(@NonNull Appointment appointment) {
        // Work Order:
        // 1. Given appointment parameter will be having the following things:
        //      +---------------+
        //      |  - Queue ID   |
        //      |  - Patient ID | --> Compile the Hash: QUEUE:<queueId>|PATIENT:<patientId> --- SHA-256 ---> Hash to be compared and stored
        //      |  - Doctor ID  |
        //      +---------------+
        // 2. Check whether for the given set of above details, was there any previous request having same Hash Value in Idempotency?
        // 3. If there was any previous request whose status is BOOKED -> return that request as it is using ARN
        // 4. If the previous request is PENDING -> return DuplicateAppointmentCreationRequestsException
        // 5. Else, continue with the procedure
        // 6. Create a new entry in Idempotency with status PENDING
        // 7. Allocate new Token
        // 8. Create a new Appointment entry in Appointment Repository.
        // 9. Save the new Appointment.
        // 10. Update the Idempotency entry from PENDING -> BOOKED.
        // 11. Return the Appointment Entry ( RETURN 200 OK ).
        // 12. In case of Rollback, remove the Idempotency entry.

        if (appointment.getQueueId() == null || appointment.getPatientId() == null || appointment.getDoctorId() == null) {
            throw new IllegalArgumentException("Queue ID, Patient ID and Doctor ID are required!");
        }

        String idempotencyKey = IdempotencyKeyGenerator.generate(appointment.getQueueId(), appointment.getPatientId());

        // Check whether any previous requests exist or not
        Optional<AppointmentIdempotencyJpaEntity> idempotencyJpaEntityOptional = appointmentIdempotencyRepository
                .findAppointmentIdempotencyJpaEntityByIdempotencyKey(idempotencyKey);

        if (idempotencyJpaEntityOptional.isPresent()) {
            AppointmentIdempotencyJpaEntity idempotencyJpaEntity = idempotencyJpaEntityOptional.get();

            if (idempotencyJpaEntity.getStatus().equals(AppointmentStatus.BOOKED)) {
                String savedARN = idempotencyJpaEntity.getAppointmentReferenceNumber();
                Optional<AppointmentJpaEntity> savedBookedAppointment = appointmentRepository
                        .findAppointmentJpaEntityByAppointmentReferenceNumber(savedARN);

                return savedBookedAppointment.map(appointmentMapper::toModel).get();
            } else if (idempotencyJpaEntity.getStatus().equals(AppointmentStatus.PENDING)){
                throw new DuplicateAppointmentCreationRequestException("Appointment for given Patient for given Queue already Exists!");
            } else if (idempotencyJpaEntity.getStatus().equals(AppointmentStatus.CANCELLED)) {
                appointmentIdempotencyRepository.delete(idempotencyJpaEntity);
            }
        }

        // Search the corresponding Doctor Shift Queue
        DoctorShiftQueue shiftQueue = doctorShiftQueueServiceFacade
                .getDoctorShiftQueue(DoctorShiftQueue
                        .builder()
                        .id(appointment.getQueueId())
                        .build()
                );

        // Create a new PENDING Idempotency Entry
        String newARN = generateUniqueAppointmentReferenceNumber();
        AppointmentIdempotencyJpaEntity newIdempotencyEntry = AppointmentIdempotencyJpaEntity.builder()
                .idempotencyKey(idempotencyKey)
                .appointmentReferenceNumber(newARN)
                .status(AppointmentStatus.PENDING)
                .build();

        newIdempotencyEntry = appointmentIdempotencyRepository.save(newIdempotencyEntry);

        // Allocate a new Token (PESSIMISTIC LOCKING APPLIED INTERNALLY)
        Integer newAllocatedToken = doctorShiftQueueServiceFacade.allocateToken(shiftQueue);

        if (newAllocatedToken == null)
            throw new TokenNumberNotIssuedException("No new Token Number was allocated!");

        // Creating a new Appointment entry in Appointment Repository
        AppointmentJpaEntity newAppointmentEntity = appointmentMapper.toEntity(appointment);
        newAppointmentEntity.setIdempotencyKey(idempotencyKey);
        newAppointmentEntity.setAppointmentReferenceNumber(newARN);
        newAppointmentEntity.setTokenNumber(newAllocatedToken);
        newAppointmentEntity.setStatus(AppointmentStatus.BOOKED);

        // Saving the new Appointment Entity
        appointmentRepository.save(newAppointmentEntity);

        // Update the status of Idempotency Entry from PENDING -> BOOKED
        newIdempotencyEntry.setStatus(AppointmentStatus.BOOKED);
        appointmentIdempotencyRepository.save(newIdempotencyEntry);

        // Return the AppointmentEntityModel
        return appointmentMapper.toModel(newAppointmentEntity);
    }

    @Override
    public void cancelAppointment(@NonNull Appointment appointment) {
        if (appointment.getAppointmentReferenceNumber() == null) {
            throw new IllegalArgumentException("Appointment Reference Number is required for cancellation!");
        }

        // 1. Fetch the existing appointment by ARN
        AppointmentJpaEntity appointmentEntity = appointmentRepository
                .findAppointmentJpaEntityByAppointmentReferenceNumber(appointment.getAppointmentReferenceNumber())
                .orElseThrow(() -> new AppointmentNotFoundException("Appointment not found for ARN: " + appointment.getAppointmentReferenceNumber()));

        // 2. If already cancelled, exit early
        if (appointmentEntity.getStatus().equals(AppointmentStatus.CANCELLED)) {
            return;
        }

        // 3. Update appointment status to CANCELLED
        appointmentEntity.setStatus(AppointmentStatus.CANCELLED);
        appointmentRepository.save(appointmentEntity);

        // 4. Clean up the idempotency entry so the slot/key can be reused or handled cleanly
        if (appointmentEntity.getIdempotencyKey() != null) {
            appointmentIdempotencyRepository
                    .findAppointmentIdempotencyJpaEntityByIdempotencyKey(appointmentEntity.getIdempotencyKey())
                    .ifPresent(appointmentIdempotencyRepository::delete);
        }
    }

    @Override
    public void markAsCompleted(@NonNull Appointment appointment) {
        isARNPresent(appointment);
        AppointmentJpaEntity fetchedAppointment = appointmentRepository
                .findAppointmentJpaEntityByAppointmentReferenceNumber(appointment.getAppointmentReferenceNumber())
                .orElseThrow(() -> new AppointmentNotFoundException("Appointment not found!"));

        AppointmentStatus appointmentStatus = fetchedAppointment.getStatus();
        if (appointmentStatus.canTransitionTo(AppointmentStatus.COMPLETED)) {
            fetchedAppointment.setStatus(AppointmentStatus.COMPLETED);
            appointmentRepository.save(fetchedAppointment);
        } else {
            throw new InvalidStateTransitionException("Appointment Transition not allowed!");
        }
    }

    @Override
    public void markAsCheckedIn(@NonNull Appointment appointment) {
        isARNPresent(appointment);
        AppointmentJpaEntity fetchedAppointment = appointmentRepository
                .findAppointmentJpaEntityByAppointmentReferenceNumber(appointment.getAppointmentReferenceNumber())
                .orElseThrow(() -> new AppointmentNotFoundException("Appointment not found!"));

        AppointmentStatus appointmentStatus = fetchedAppointment.getStatus();
        if (appointmentStatus.canTransitionTo(AppointmentStatus.CHECKED_IN)) {
            fetchedAppointment.setStatus(AppointmentStatus.CHECKED_IN);
            appointmentRepository.save(fetchedAppointment);
        } else {
            throw new InvalidStateTransitionException("Appointment Transition not allowed!");
        }
    }

    @Override
    public void markAsNoShow(@NonNull Appointment appointment) {
        isARNPresent(appointment);
        AppointmentJpaEntity fetchedAppointment = appointmentRepository
                .findAppointmentJpaEntityByAppointmentReferenceNumber(appointment.getAppointmentReferenceNumber())
                .orElseThrow(() -> new AppointmentNotFoundException("Appointment not found!"));

        AppointmentStatus appointmentStatus = fetchedAppointment.getStatus();
        if (appointmentStatus.canTransitionTo(AppointmentStatus.NO_SHOW)) {
            fetchedAppointment.setStatus(AppointmentStatus.NO_SHOW);
            appointmentRepository.save(fetchedAppointment);
        } else {
            throw new InvalidStateTransitionException("Appointment Transition not allowed!");
        }
    }

    @Override
    public String generateUniqueAppointmentReferenceNumber() {
        LocalDate date = LocalDate.now();
        // Consider using a UUID or database sequence to avoid collision under concurrency
        return date.format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    private void isARNPresent(@NonNull Appointment appointment) {
        if (appointment.getAppointmentReferenceNumber() == null) {
            throw new AppointmentNotFoundException("Appointment Reference Number is required!");
        }
    }
}
