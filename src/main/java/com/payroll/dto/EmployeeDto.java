package com.payroll.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeDto {
    private Long employeeId;

    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@gmail\\.com$", message = "Email must be in the format of @gmail (e.g., example@gmail.com)")
    private String email;

    @NotBlank(message = "District is required")
    private String district;

    @NotBlank(message = "Mobile number is required")
    @Pattern(regexp = "^\\+2507[2389]\\d{7}$", message = "Mobile must be a valid Rwandan phone number starting with +2507 (e.g. +250788888888)")
    private String mobile;

    @NotNull(message = "Date of birth is required")
    @Past(message = "Date of birth must be in the past")
    private LocalDate dateOfBirth;

    @NotBlank(message = "Department is required")
    private String department;

    @NotBlank(message = "Position is required")
    private String position;

    @NotNull(message = "Base salary is required")
    @Min(value = 1, message = "Base salary must be greater than 0")
    private Double baseSalary;

    @NotBlank(message = "Status is required (ACTIVE/INACTIVE)")
    @Pattern(regexp = "^(ACTIVE|INACTIVE)$", message = "Status must be ACTIVE or INACTIVE")
    private String status;

    @NotNull(message = "Joining date is required")
    private LocalDate joiningDate;
}
