package com.payroll.service.impl;

import com.payroll.dto.EmployeeDto;
import com.payroll.entity.Employee;
import com.payroll.exception.ResourceNotFoundException;
import com.payroll.repository.EmployeeRepository;
import com.payroll.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Override
    public EmployeeDto createEmployee(EmployeeDto dto) {
        Employee employee = Employee.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .district(dto.getDistrict())
                .mobile(dto.getMobile())
                .dateOfBirth(dto.getDateOfBirth())
                .department(dto.getDepartment())
                .position(dto.getPosition())
                .baseSalary(dto.getBaseSalary())
                .status(dto.getStatus().toUpperCase())
                .joiningDate(dto.getJoiningDate())
                .build();
        Employee saved = employeeRepository.save(employee);
        return mapToDto(saved);
    }

    @Override
    public EmployeeDto updateEmployee(Long id, EmployeeDto dto) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));

        employee.setFirstName(dto.getFirstName());
        employee.setLastName(dto.getLastName());
        employee.setEmail(dto.getEmail());
        employee.setDistrict(dto.getDistrict());
        employee.setMobile(dto.getMobile());
        employee.setDateOfBirth(dto.getDateOfBirth());
        employee.setDepartment(dto.getDepartment());
        employee.setPosition(dto.getPosition());
        employee.setBaseSalary(dto.getBaseSalary());
        employee.setStatus(dto.getStatus().toUpperCase());
        employee.setJoiningDate(dto.getJoiningDate());

        Employee updated = employeeRepository.save(employee);
        return mapToDto(updated);
    }

    @Override
    public void deleteEmployee(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
        employeeRepository.delete(employee);
    }

    @Override
    public EmployeeDto getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
        return mapToDto(employee);
    }

    @Override
    public List<EmployeeDto> getAllEmployees() {
        return employeeRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeDto> getEmployeesByStatus(String status) {
        return employeeRepository.findByStatus(status.toUpperCase()).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private EmployeeDto mapToDto(Employee employee) {
        return EmployeeDto.builder()
                .employeeId(employee.getEmployeeId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .email(employee.getEmail())
                .district(employee.getDistrict())
                .mobile(employee.getMobile())
                .dateOfBirth(employee.getDateOfBirth())
                .department(employee.getDepartment())
                .position(employee.getPosition())
                .baseSalary(employee.getBaseSalary())
                .status(employee.getStatus())
                .joiningDate(employee.getJoiningDate())
                .build();
    }
}
