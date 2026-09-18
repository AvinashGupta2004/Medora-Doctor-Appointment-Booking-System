package com.medora.PATIENT.web.DTOs.RESPONSES;

import com.medora.PATIENT.common.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientRegistrationResponse {

    private String firstName;
    private String lastName;
    private String contactNumber;

    @Builder.Default
    private Gender gender = Gender.MALE;
    private String emailAddress;
    private LocalDate dateOfBirth;
}
