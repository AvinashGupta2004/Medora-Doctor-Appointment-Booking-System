package com.medora.DOCTOR.web.DTOs.REQUEST;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorRegistrationRequest {

    private String firstName;
    private String lastName;
    private String medicalLicenseNumber;
    private String specialization;
    private String location;
}
