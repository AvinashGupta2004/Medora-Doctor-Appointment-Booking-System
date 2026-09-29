package com.medora.common.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class ContactInformation {

    @NotBlank(message = "First Name cannot be left Empty!")
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @NotBlank(message = "Primary Email Address cannot be left Empty!")
    @Email(message = "Invalid Email Address format!")
    @Column(name = "primary_email_addr", nullable = false, unique = true)
    private String primaryEmailAddress;

    @Column(name = "secondary_email_addr", unique = true)
    private String secondaryEmailAddress;

    @NotBlank(message = "Primary Mobile Number is required!")
    @Column(name = "primary_mobile_number", nullable = false, unique = true)
    private String primaryMobileNumber;

    @Column(name = "secondary_mobile_number", unique = true)
    private String secondaryMobileNumber;

    @NotBlank(message = "Present Address cannot be left Empty!")
    @Column(name = "present_addr", nullable = false)
    private String presentAddress;

    @Column(name = "permanent_addr")
    private String permanentAddress;
}
