package com.medora.APPOINTMENT.processing.services.impl;

import com.medora.APPOINTMENT.processing.models.Appointment;
import com.medora.APPOINTMENT.processing.services.AppointmentServiceFacade;
import com.medora.APPOINTMENT.processing.services.facade.management.AppointmentManagementService;
import com.medora.APPOINTMENT.processing.services.facade.query.AppointmentQueryService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
@Validated
public class AppointmentServiceFacadeImpl implements AppointmentServiceFacade {

    private final AppointmentManagementService appointmentManagementService;
    private final AppointmentQueryService appointmentQueryService;

    @Override
    public Appointment bookAppointment(@NonNull Appointment appointment) {
        return appointmentManagementService.bookAppointment(appointment);
    }

    @Override
    public void cancelAppointment(@NonNull Appointment appointment) {
        appointmentManagementService.cancelAppointment(appointment);
    }

    @Override
    public void markAsCompleted(@NonNull Appointment appointment) {
        appointmentManagementService.markAsCompleted(appointment);
    }

    @Override
    public void markAsCheckedIn(@NonNull Appointment appointment) {
        appointmentManagementService.markAsCheckedIn(appointment);
    }

    @Override
    public void markAsNoShow(@NonNull Appointment appointment) {
        appointmentManagementService.markAsNoShow(appointment);
    }

    @Override
    public Appointment findAppointment(@NonNull Appointment appointment) {
        return null;
    }
}
