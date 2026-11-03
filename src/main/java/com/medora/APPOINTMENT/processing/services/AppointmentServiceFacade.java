package com.medora.APPOINTMENT.processing.services;

import com.medora.APPOINTMENT.processing.models.Appointment;
import lombok.NonNull;

public interface AppointmentServiceFacade {

    Appointment bookAppointment(@NonNull Appointment appointment);

    void cancelAppointment(@NonNull Appointment appointment);

    void markAsCompleted(@NonNull Appointment appointment);

    void markAsCheckedIn(@NonNull Appointment appointment);

    void markAsNoShow(@NonNull Appointment appointment);

    Appointment findAppointment(@NonNull Appointment appointment);
}
