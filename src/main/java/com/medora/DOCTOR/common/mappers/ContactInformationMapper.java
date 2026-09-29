package com.medora.DOCTOR.common.mappers;

import com.medora.DOCTOR.web.DTOs.REQUEST.DoctorRegistrationRequest;
import com.medora.common.models.ContactInformation;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface ContactInformationMapper {

    ContactInformation toContactInformation(DoctorRegistrationRequest request);
}
