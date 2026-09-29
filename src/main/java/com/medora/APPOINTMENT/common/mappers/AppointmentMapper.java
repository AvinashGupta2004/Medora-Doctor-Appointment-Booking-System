package com.medora.APPOINTMENT.common.mappers;

import com.medora.APPOINTMENT.persistence.entity.AppointmentJpaEntity;
import com.medora.APPOINTMENT.processing.models.Appointment;
import com.medora.DOCTOR.persistence.entity.DoctorJpaEntity;
import com.medora.PATIENT.persistence.entity.PatientJpaEntity;
import com.medora.QUEUE.persistence.entity.DoctorShiftQueueJpaEntity;
import org.mapstruct.*;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface AppointmentMapper {

    @Mapping(target = "queueId", source = "queue.id")
    @Mapping(target = "patientId", source = "patient.id")
    @Mapping(target = "doctorId", source = "doctor.id")
    Appointment toModel(AppointmentJpaEntity entity);

    @Mapping(target = "queue", source = "queueId", qualifiedByName = "mapQueueIdToEntity")
    @Mapping(target = "patient", source = "patientId", qualifiedByName = "mapPatientIdToEntity")
    @Mapping(target = "doctor", source = "doctorId", qualifiedByName = "mapDoctorIdToEntity")
    AppointmentJpaEntity toEntity(Appointment model);

    @Named("mapQueueIdToEntity")
    default DoctorShiftQueueJpaEntity mapQueueIdToEntity(Long queueId) {
        if (queueId == null) return null;
        DoctorShiftQueueJpaEntity queue = new DoctorShiftQueueJpaEntity();
        queue.setId(queueId);
        return queue;
    }

    @Named("mapPatientIdToEntity")
    default PatientJpaEntity mapPatientIdToEntity(Long patientId) {
        if (patientId == null) return null;
        PatientJpaEntity patient = new PatientJpaEntity();
        patient.setId(patientId);
        return patient;
    }

    @Named("mapDoctorIdToEntity")
    default DoctorJpaEntity mapDoctorIdToEntity(Long doctorId) {
        if (doctorId == null) return null;
        DoctorJpaEntity doctor = new DoctorJpaEntity();
        doctor.setId(doctorId);
        return doctor;
    }
}
