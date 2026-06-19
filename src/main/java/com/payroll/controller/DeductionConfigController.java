package com.payroll.controller;

import com.payroll.dto.DeductionConfigDto;
import com.payroll.service.DeductionConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/deductions")
@RequiredArgsConstructor
@Tag(name = "Deductions & Allowances Config", description = "APIs to configure tax, allowance, and insurance rates")
public class DeductionConfigController {

    private final DeductionConfigService deductionService;

    @PostMapping
    @Operation(summary = "Configure or update a deduction percentage rate")
    public ResponseEntity<DeductionConfigDto> configureDeduction(@Valid @RequestBody DeductionConfigDto dto) {
        return ResponseEntity.ok(deductionService.createOrUpdateDeduction(dto));
    }

    @GetMapping
    @Operation(summary = "Get all configured deduction rules")
    public ResponseEntity<List<DeductionConfigDto>> getAllDeductions() {
        return ResponseEntity.ok(deductionService.getAllDeductions());
    }

    @GetMapping("/{name}")
    @Operation(summary = "Get a configured deduction rate by name")
    public ResponseEntity<DeductionConfigDto> getDeductionByName(@PathVariable String name) {
        return ResponseEntity.ok(deductionService.getDeductionByName(name));
    }
}
