package com.payroll.dto;

import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PayslipDto {
    private Long id;
    private Long employeeId;
    private String employeeName;
    private Double baseSalary;
    private Double houseAllowance;
    private Double transportAllowance;
    private Double grossSalary;
    private Double tax;
    private Double pension;
    private Double medicalInsurance;
    private Double otherDeductions;
    private Double netSalary;
    private String status;
    private String month;
    private Integer year;
    private LocalDate generatedDate;
}
