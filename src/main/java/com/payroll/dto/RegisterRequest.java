package com.payroll.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequest {
    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@gmail\\.com$", message = "Email must be in the format of @gmail (e.g., example@gmail.com)")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    @NotBlank(message = "Role is required (ADMIN, MANAGER, EMPLOYEE)")
    @Pattern(regexp = "^(?i)(ADMIN|MANAGER|EMPLOYEE)$", message = "Role must be ADMIN, MANAGER, or EMPLOYEE")
    private String role;
}
