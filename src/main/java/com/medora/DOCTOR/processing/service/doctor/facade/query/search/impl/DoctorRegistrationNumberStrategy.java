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

@Component("doctorRegistrationNumberStrategy")
@RequiredArgsConstructor
public class DoctorRegistrationNumberStrategy implements DoctorSearchStrategy {

    // ********************************************************************
    // * In order to search for Doctor for general-purpose fetching.      *
    // * will make use of the below fields:                               *
    // *    1. DoctorRegistrationNumber                                   *
    // *    2. MedicalRegistrationNumber                                  *
    // *    3. PrimaryMobileNumber                                        *
    // *    3. PrimaryEmailAddress                                        *
    // *  Note: All above said field will be used for searching (Index)   *
    // ********************************************************************

    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;


    @Override
    public Optional<Doctor> searchDoctor(Doctor doctor, boolean loadWithSchedule) {
        Optional<Doctor> registeredDoctor;
        ContactInformation contactInfo = doctor.getContactInformation();
        if (!loadWithSchedule) {
            registeredDoctor = doctorRepository.findFirstByDoctorRegistrationNumberOrMedicalRegistrationNumberOrContactInformation_PrimaryMobileNumberOrContactInformation_PrimaryEmailAddress(
                    doctor.getDoctorRegistrationNumber(),
                    doctor.getMedicalRegistrationNumber(),
                    contactInfo != null ? contactInfo.getPrimaryMobileNumber() : null,
                    contactInfo != null ? contactInfo.getPrimaryEmailAddress() : null
            ).map(doctorMapper::toModel);
            return registeredDoctor;
        }
        registeredDoctor = doctorRepository.findFirstWithScheduleByDoctorRegistrationNumberOrMedicalRegistrationNumberOrContactInformation_PrimaryMobileNumberOrContactInformation_PrimaryEmailAddress(
                doctor.getDoctorRegistrationNumber(),
                doctor.getMedicalRegistrationNumber(),
                contactInfo != null ? contactInfo.getPrimaryMobileNumber() : null,
                contactInfo != null ? contactInfo.getPrimaryEmailAddress() : null
        ).map(doctorMapper::toModel);

        return registeredDoctor;
    }

    @Override
    public boolean supports(Doctor doctor, SearchContext searchContext) {
        boolean hasRequiredFields = doctor.getDoctorRegistrationNumber() != null
                && doctor.getMedicalRegistrationNumber() != null
                && doctor.getContactInformation() != null
                && doctor.getContactInformation().getPrimaryMobileNumber() != null
                && doctor.getContactInformation().getPrimaryEmailAddress() != null;

        return hasRequiredFields || searchContext.equals(SearchContext.SINGLE_FETCH);
    }
}
