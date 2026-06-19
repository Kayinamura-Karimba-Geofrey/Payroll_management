package com.payroll.dto;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeMessageDto {
    private Long id;
    private Long employeeId;
    private String employeeName;
    private String messageText;
    private String month;
    private Integer year;
    private LocalDateTime sentAt;
}
