package com.medora.DOCTOR.common.mappers;

import com.medora.DOCTOR.persistence.entity.DoctorJpaEntity;
import com.medora.DOCTOR.processing.models.Doctor;
import com.medora.DOCTOR.web.DTOs.REQUEST.DoctorDeletionRequest;
import com.medora.DOCTOR.web.DTOs.REQUEST.DoctorRegistrationRequest;
import com.medora.DOCTOR.web.DTOs.RESPONSE.DoctorDeletionResponse;
import com.medora.DOCTOR.web.DTOs.RESPONSE.DoctorRegistrationResponse;
import org.mapstruct.*;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface DoctorMapper {

    public final String DOCTOR_REGISTRATION_NUMBER_PREFIX = "D-";

    Doctor toModel(DoctorJpaEntity entity);

    DoctorJpaEntity toEntity(Doctor doctor);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromModel(Doctor doctor, @MappingTarget DoctorJpaEntity entity);

    DoctorRegistrationResponse mapModelToRegistrationResponse(Doctor doctor);
    Doctor mapRegistrationRequestToModel(DoctorRegistrationRequest doctorRegistrationRequest);

    DoctorDeletionResponse mapModelToDeletionResponse(Doctor doctor);
    Doctor mapDeletionRequestToModel(DoctorDeletionRequest doctorDeletionRequest);


    default Long toId(String registrationNumber) {
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
