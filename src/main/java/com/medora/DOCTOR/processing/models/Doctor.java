package com.medora.DOCTOR.processing.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Doctor {

    private Long id;
    private Long doctorRegistrationNumber;
    private String firstName;
    private String lastName;
    private String medicalLicenseNumber;
    private String specialization;
    private String location;
}
