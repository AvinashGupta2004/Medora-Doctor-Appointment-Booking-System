package com.medora.DOCTOR.web.DTOs.RESPONSE;

import com.medora.common.models.ContactInformation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorRegistrationResponse {

    private String doctorRegistrationNumber;
    private String medicalRegistrationNumber;

    @Valid
    private ContactInformation contactInformation;
    private String specialization;
    private String location;
}
