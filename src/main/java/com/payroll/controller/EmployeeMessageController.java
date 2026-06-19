package com.payroll.controller;

import com.payroll.dto.EmployeeMessageDto;
import com.payroll.service.EmployeeMessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
@Tag(name = "Notification Log Viewers", description = "APIs for viewing database message notifications generated on payslip approval")
public class EmployeeMessageController {

    private final EmployeeMessageService messageService;

    @GetMapping
    @Operation(summary = "Get all message notification logs")
    public ResponseEntity<List<EmployeeMessageDto>> getAllMessages() {
        return ResponseEntity.ok(messageService.getAllMessages());
    }

    @GetMapping("/employee/{employeeId}")
    @Operation(summary = "Get message notification logs for a specific employee")
    public ResponseEntity<List<EmployeeMessageDto>> getMessagesByEmployeeId(@PathVariable Long employeeId) {
        return ResponseEntity.ok(messageService.getMessagesByEmployeeId(employeeId));
    }
}
