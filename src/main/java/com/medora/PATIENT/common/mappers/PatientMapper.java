package com.medora.PATIENT.common.mappers;

import com.medora.PATIENT.persistence.entity.PatientJpaEntity;
import com.medora.PATIENT.processing.models.Patient;
import com.medora.PATIENT.web.DTOs.REQUEST.PatientDeletionRequest;
import com.medora.PATIENT.web.DTOs.REQUEST.PatientRegistrationRequest;
import com.medora.PATIENT.web.DTOs.RESPONSE.PatientDeletionResponse;
import com.medora.PATIENT.web.DTOs.RESPONSE.PatientRegistrationResponse;
import org.mapstruct.*;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface PatientMapper {

    public final String PATIENT_REGISTRATION_NUMBER_PREFIX = "P-";

    Patient toModel(PatientJpaEntity entity);

    PatientJpaEntity toEntity(Patient model);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromModel(Patient model, @MappingTarget PatientJpaEntity entity);

    Patient mapPatientRegistrationRequestToModel(PatientRegistrationRequest patientRegistrationRequest);
    PatientRegistrationResponse mapModelToPatientRegistrationResponse(Patient model);

    Patient mapPatientDeletionRequestToModel(PatientDeletionRequest patientDeletionRequest);
    PatientDeletionResponse mapModelToPatientDeletionResponse(Patient model);

    default Long toId(String registrationNumber) {
        if (registrationNumber == null || registrationNumber.isBlank()) {
            return null;
        }
        if (!registrationNumber.startsWith(PATIENT_REGISTRATION_NUMBER_PREFIX)) {
            throw new IllegalArgumentException("Invalid registration number: " + registrationNumber);
        }

        return Long.parseLong(
                registrationNumber.substring(PATIENT_REGISTRATION_NUMBER_PREFIX.length())
        );
    }

    default String toRegistrationNumber(Long registrationNumber) {
        return registrationNumber == null ? null : PATIENT_REGISTRATION_NUMBER_PREFIX + String.valueOf(registrationNumber);
    }
}
