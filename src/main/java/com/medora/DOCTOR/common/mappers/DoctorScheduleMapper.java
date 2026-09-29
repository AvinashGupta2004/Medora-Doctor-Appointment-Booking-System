package com.medora.DOCTOR.common.mappers;

import com.medora.DOCTOR.persistence.entity.DoctorJpaEntity;
import com.medora.DOCTOR.persistence.entity.DoctorScheduleJpaEntity;
import com.medora.DOCTOR.processing.models.DoctorSchedule;
import com.medora.DOCTOR.web.DTOs.REQUEST.DoctorScheduleRequest;
import org.mapstruct.*;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface DoctorScheduleMapper {

    public final String DOCTOR_REGISTRATION_NUMBER_PREFIX = "D-";

    @Mapping(target = "doctorId", source = "doctor.id")
    DoctorSchedule toModel(DoctorScheduleJpaEntity entity);

    @Mapping(target = "doctor", source = "doctorId", qualifiedByName = "mapDoctorIdToEntity")
    DoctorScheduleJpaEntity toEntity(DoctorSchedule doctorSchedule);

    DoctorSchedule mapDoctorScheduleRequestToModel(DoctorScheduleRequest doctorScheduleRequest);

    @Named("mapDoctorIdToEntity")
    default DoctorJpaEntity mapDoctorIdToEntity(Long doctorId) {
        if (doctorId == null) return null;
        DoctorJpaEntity doctorJpaEntity = new DoctorJpaEntity();
        doctorJpaEntity.setId(doctorId);
        return doctorJpaEntity;
    }

    default Long toLong(String registrationNumber) {
        if (registrationNumber == null || registrationNumber.isBlank()) {
            return null;
        }
        if (!registrationNumber.startsWith(DOCTOR_REGISTRATION_NUMBER_PREFIX)) {
            throw new IllegalArgumentException("Invalid registration number: " + registrationNumber);
        }

        return Long.parseLong(
                registrationNumber.substring(DOCTOR_REGISTRATION_NUMBER_PREFIX.length())
        );
    }

    default String toRegistrationNumber(Long registrationNumber) {
        return registrationNumber == null ? null : DOCTOR_REGISTRATION_NUMBER_PREFIX + String.valueOf(registrationNumber);
    }
}
