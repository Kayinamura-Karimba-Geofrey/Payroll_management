package com.payroll.service;

import com.payroll.dto.EmployeeMessageDto;
import java.util.List;

public interface EmployeeMessageService {
    List<EmployeeMessageDto> getMessagesByEmployeeId(Long employeeId);
    List<EmployeeMessageDto> getAllMessages();
}
