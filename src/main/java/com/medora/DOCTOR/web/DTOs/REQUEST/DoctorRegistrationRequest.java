package com.medora.DOCTOR.web.DTOs.REQUEST;

import com.medora.common.models.ContactInformation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorRegistrationRequest {

    private String doctorRegistrationNumber;

    @NotBlank(message = "Doctor's Medical Registration Number cannot be left Empty!")
    private String medicalRegistrationNumber;

    @Valid
    @NotNull(message = "Contact Information is required!")
    private ContactInformation contactInformation;
    private String specialization;
    private String location;
}
