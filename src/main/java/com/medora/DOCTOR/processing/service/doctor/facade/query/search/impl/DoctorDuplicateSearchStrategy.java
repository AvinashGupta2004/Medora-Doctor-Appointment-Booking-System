package com.medora.DOCTOR.processing.service.doctor.facade.query.search.impl;

import com.medora.DOCTOR.common.enums.SearchContext;
import com.medora.DOCTOR.common.mappers.DoctorMapper;
import com.medora.DOCTOR.persistence.repository.DoctorRepository;
import com.medora.DOCTOR.processing.models.Doctor;
import com.medora.DOCTOR.processing.service.doctor.facade.query.search.DoctorSearchStrategy;
import com.medora.common.models.ContactInformation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("doctorDuplicateSearchStrategy")
@RequiredArgsConstructor
public class DoctorDuplicateSearchStrategy implements DoctorSearchStrategy {

    // ********************************************************************
    // * In order to search for Doctor to prevent double registration, we *
    // * will make use of the below fields:                               *
    // *    1. MedicalRegistrationNumber                                  *
    // *    2. Primary Email Address                                      *
    // *    3. Primary Mobile Number                                      *
    // *  Note: All above said field will be used for searching (Index)   *
    // ********************************************************************

    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;

    @Override
    public Optional<Doctor> searchDoctor(Doctor doctor, boolean loadWithSchedule) {
        // loadWithSchedule is intentionally ignored as it is of no use during registration.

        ContactInformation newDoctorContactInformation = doctor.getContactInformation();
        return doctorRepository.findFirstByMedicalRegistrationNumberOrContactInformation_PrimaryMobileNumberOrContactInformation_PrimaryEmailAddress(
                doctor.getMedicalRegistrationNumber(),
                newDoctorContactInformation != null ? newDoctorContactInformation.getPrimaryMobileNumber() : null,
                newDoctorContactInformation != null ? newDoctorContactInformation.getPrimaryEmailAddress() : null
        ).map(doctorMapper::toModel);
    }

    @Override
    public boolean supports(Doctor doctor, SearchContext searchContext) {
        boolean hasRequiredFields = doctor.getMedicalRegistrationNumber() != null
                && doctor.getContactInformation() != null
                && doctor.getContactInformation().getPrimaryMobileNumber() != null
                && doctor.getContactInformation().getPrimaryEmailAddress() != null;

        return hasRequiredFields || searchContext.equals(SearchContext.DUPLICATE_CHECK);
    }
}
