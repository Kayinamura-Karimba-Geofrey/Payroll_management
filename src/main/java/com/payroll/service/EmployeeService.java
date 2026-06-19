package com.payroll.service;

import com.payroll.dto.EmployeeDto;
import java.util.List;

public interface EmployeeService {
    EmployeeDto createEmployee(EmployeeDto employeeDto);
    EmployeeDto updateEmployee(Long id, EmployeeDto employeeDto);
    void deleteEmployee(Long id);
    EmployeeDto getEmployeeById(Long id);
    List<EmployeeDto> getAllEmployees();
    List<EmployeeDto> getEmployeesByStatus(String status);
}
