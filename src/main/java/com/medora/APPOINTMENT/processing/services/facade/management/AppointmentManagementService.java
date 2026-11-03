package com.medora.APPOINTMENT.processing.services.facade.management;

import com.medora.APPOINTMENT.processing.models.Appointment;
import lombok.NonNull;

public interface AppointmentManagementService {

    Appointment bookAppointment(@NonNull Appointment appointment);

    void cancelAppointment(@NonNull Appointment appointment);

    void markAsCompleted(@NonNull Appointment appointment);

    void markAsCheckedIn(@NonNull Appointment appointment);

    void markAsNoShow(@NonNull Appointment appointment);

    String generateUniqueAppointmentReferenceNumber();
}
