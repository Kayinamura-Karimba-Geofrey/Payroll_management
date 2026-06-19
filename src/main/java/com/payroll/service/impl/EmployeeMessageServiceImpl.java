package com.payroll.service.impl;

import com.payroll.dto.EmployeeMessageDto;
import com.payroll.entity.EmployeeMessage;
import com.payroll.repository.EmployeeMessageRepository;
import com.payroll.service.EmployeeMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeMessageServiceImpl implements EmployeeMessageService {

    private final EmployeeMessageRepository messageRepository;

    @Override
    public List<EmployeeMessageDto> getMessagesByEmployeeId(Long employeeId) {
        return messageRepository.findByEmployee_EmployeeId(employeeId).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeMessageDto> getAllMessages() {
        return messageRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private EmployeeMessageDto mapToDto(EmployeeMessage msg) {
        return EmployeeMessageDto.builder()
                .id(msg.getId())
                .employeeId(msg.getEmployee().getEmployeeId())
                .employeeName(msg.getEmployee().getFirstName() + " " + msg.getEmployee().getLastName())
                .messageText(msg.getMessageText())
                .month(msg.getMonth())
                .year(msg.getYear())
                .sentAt(msg.getSentAt())
                .build();
    }
}
