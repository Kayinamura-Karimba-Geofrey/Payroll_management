package com.payroll.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "employees")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private Long employeeId;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "district")
    private String district;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "department", nullable = false)
    private String department;

    @Column(name = "position", nullable = false)
    private String position;

    @Column(name = "base_salary", nullable = false)
    private Double baseSalary;

    @Column(name = "status", nullable = false)
    private String status; // ACTIVE or INACTIVE

    @Column(name = "joining_date")
    private LocalDate joiningDate;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Payslip> payslips;
}
