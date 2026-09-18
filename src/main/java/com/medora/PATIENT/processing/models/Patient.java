package com.medora.PATIENT.processing.models;

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
public class Patient {

    private Long id;
    private String firstName;
    private String lastName;
    private String contactNumber;
    private String emailAddress;
    private LocalDate dateOfBirth;
    private Gender gender;

}
