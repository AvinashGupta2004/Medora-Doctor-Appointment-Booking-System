package com.medora.DOCTOR.web.DTOs.REQUEST;

import com.medora.common.models.ContactInformation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorDeletionRequest {

    private ContactInformation contactInformation;
    private String specialization;
    private String location;
}
