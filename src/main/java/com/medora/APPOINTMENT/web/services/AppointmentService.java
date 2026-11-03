package com.medora.APPOINTMENT.web.services;

import com.medora.APPOINTMENT.common.mappers.AppointmentMapper;
import com.medora.APPOINTMENT.processing.models.Appointment;
import com.medora.APPOINTMENT.processing.services.AppointmentServiceFacade;
import com.medora.APPOINTMENT.web.DTOs.REQUEST.AppointmentCreationRequest;
import com.medora.APPOINTMENT.web.DTOs.REQUEST.AppointmentSearchRequest;
import com.medora.APPOINTMENT.web.DTOs.RESPONSE.AppointmentCreationResponse;
import com.medora.DOCTOR.common.mappers.DoctorMapper;
import com.medora.DOCTOR.processing.models.Doctor;
import com.medora.DOCTOR.processing.service.doctor.DoctorServiceFacade;
import com.medora.PATIENT.common.mappers.PatientMapper;
import com.medora.PATIENT.processing.models.Patient;
import com.medora.PATIENT.processing.service.PatientServiceFacade;
import com.medora.QUEUE.processing.models.DoctorShiftQueue;
import com.medora.QUEUE.processing.services.queue.DoctorShiftQueueServiceFacade;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Locale;

@Service
@RequiredArgsConstructor
@Validated
public class AppointmentService {

    private final PatientMapper patientMapper;
    private final DoctorMapper doctorMapper;
    private final AppointmentMapper appointmentMapper;
    private final AppointmentServiceFacade appointmentServiceFacade;
    private final DoctorShiftQueueServiceFacade doctorShiftQueueServiceFacade;
    private final PatientServiceFacade patientServiceFacade;

    public AppointmentCreationResponse bookNewAppointment(@NonNull AppointmentCreationRequest request) {
        Long doctorRegistrationNumber = doctorMapper.toLong(request.doctorRegistrationNumber());
        Long patientRegistrationNumber = patientMapper.toId(request.patientRegistrationNumber());

        DoctorShiftQueue fetchedQueue = doctorShiftQueueServiceFacade
            .getDoctorShiftQueue(DoctorShiftQueue
                .builder()
                .doctorRegistrationNumber(doctorRegistrationNumber)
                .shiftDate(request.shiftDate())
                .startTime(request.startTime())
                .build()
            );

        Patient fetchedPatient = patientServiceFacade
                .getPatient(Patient
                        .builder()
                        .patientRegistrationNumber(patientRegistrationNumber)
                        .contactNumber(request.patientContactNumber())
                        .build()
                );

        Appointment appointment = appointmentServiceFacade.bookAppointment(
            appointmentMapper.toModel(request, fetchedQueue, fetchedPatient)
        );

        return new AppointmentCreationResponse(
            appointment.getAppointmentReferenceNumber(),
            request.doctorRegistrationNumber(),
            fetchedQueue.getShiftDate(),
            fetchedQueue.getStartTime(),
            appointment.getTokenNumber(),
            request.patientRegistrationNumber(),
            appointment.getStatus()
        );
    }

    public void cancelAppointment(@NonNull AppointmentSearchRequest request) {
        Appointment appointment = Appointment.builder()
                .appointmentReferenceNumber(request.appointmentReferenceNumber())
                .build();

        appointmentServiceFacade.cancelAppointment(appointment);
    }
}
