package com.medora.PATIENT.web.DTOs.REQUEST;

import com.medora.PATIENT.common.enums.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientRegistrationRequest {

    @NotBlank(message = "First Name cannot be left Empty!")
    private String firstName;
    private String lastName;

    @NotNull(message = "Date of Birth is required!")
    private LocalDate dateOfBirth;

    @NotNull
    @Builder.Default
    private Gender gender = Gender.MALE;

    @Pattern(regexp = "^[0-9]{10}$", message = "Invalid Indian Contact Number format!")
    @NotBlank(message = "First Name cannot be left Empty!")
    private String contactNumber;

    @Email(message = "Invalid Email Address Format!")
    private String emailAddress;
}
