package com.medora.PATIENT.common.mappers;

import com.medora.PATIENT.persistence.entity.PatientJpaEntity;
import com.medora.PATIENT.processing.models.Patient;
import com.medora.PATIENT.web.DTOs.REQUEST.PatientDeletionRequest;
import com.medora.PATIENT.web.DTOs.REQUEST.PatientRegistrationRequest;
import com.medora.PATIENT.web.DTOs.RESPONSES.PatientDeletionResponse;
import com.medora.PATIENT.web.DTOs.RESPONSES.PatientRegistrationResponse;
import org.mapstruct.*;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface PatientMapper {

    Patient toModel(PatientJpaEntity entity);

    PatientJpaEntity toEntity(Patient model);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromModel(Patient model, @MappingTarget PatientJpaEntity entity);

    Patient mapPatientRegistrationRequestToModel(PatientRegistrationRequest patientRegistrationRequest);
    PatientRegistrationResponse mapModelToPatientRegistrationResponse(Patient model);

    Patient mapPatientDeletionRequestToModel(PatientDeletionRequest patientDeletionRequest);
    PatientDeletionResponse mapModelToPatientDeletionResponse(Patient model);
}
