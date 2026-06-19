package com.payroll.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "deductions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeductionConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "percentage", nullable = false)
    private Double percentage;
}
