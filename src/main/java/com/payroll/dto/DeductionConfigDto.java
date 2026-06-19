package com.payroll.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeductionConfigDto {
    private Long id;

    @NotBlank(message = "Deduction name is required")
    @Size(max = 100, message = "Deduction name must not exceed 100 characters")
    private String name;

    @NotNull(message = "Percentage is required")
    @DecimalMin(value = "0.0", message = "Percentage must be greater than or equal to 0")
    @DecimalMax(value = "100.0", message = "Percentage must not exceed 100")
    private Double percentage;
}
