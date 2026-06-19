package com.payroll.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "payslips")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payslip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(name = "base_salary", nullable = false)
    private Double baseSalary;

    @Column(name = "house_allowance", nullable = false)
    private Double houseAllowance;

    @Column(name = "transport_allowance", nullable = false)
    private Double transportAllowance;

    @Column(name = "gross_salary", nullable = false)
    private Double grossSalary;

    @Column(name = "tax", nullable = false)
    private Double tax;

    @Column(name = "pension", nullable = false)
    private Double pension;

    @Column(name = "medical_insurance", nullable = false)
    private Double medicalInsurance;

    @Column(name = "other_deductions", nullable = false)
    private Double otherDeductions;

    @Column(name = "net_salary", nullable = false)
    private Double netSalary;

    @Column(name = "status", nullable = false)
    private String status; // Draft, Paid

    @Column(name = "month", nullable = false)
    private String month;

    @Column(name = "year", nullable = false)
    private Integer year;

    @Column(name = "generated_date", nullable = false)
    private LocalDate generatedDate;
}
