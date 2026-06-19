package com.payroll.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "employee_messages")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(name = "message_text", nullable = false, length = 1000)
    private String messageText;

    @Column(name = "month", nullable = false)
    private String month;

    @Column(name = "year", nullable = false)
    private Integer year;

    @Column(name = "sent_at", nullable = false)
    private LocalDateTime sentAt;
}
