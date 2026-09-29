package com.medora.DOCTOR.processing.models;

import com.medora.common.models.ContactInformation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Doctor {

    private Long id;
    private Long doctorRegistrationNumber; // SYSTEM-GENERATED
    private String medicalRegistrationNumber;
    private ContactInformation contactInformation;
    private String specialization;
    private String location;

    @Builder.Default
    private List<DoctorSchedule> doctorSchedules = new ArrayList<>();
}
