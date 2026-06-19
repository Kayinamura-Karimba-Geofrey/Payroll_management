package com.payroll.controller;

import com.payroll.dto.PayslipDto;
import com.payroll.service.PayslipService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payrolls")
@RequiredArgsConstructor
@Tag(name = "Payroll & Payslip Management", description = "APIs to compute, retrieve, and approve employee payslips")
public class PayslipController {

    private final PayslipService payslipService;

    @PostMapping("/generate")
    @Operation(summary = "Generate monthly payroll (Draft payslips) for all ACTIVE employees (prevents duplicate generation)")
    public ResponseEntity<List<PayslipDto>> generatePayroll(@RequestParam String month, @RequestParam Integer year) {
        return ResponseEntity.ok(payslipService.generatePayrollForAllActiveEmployees(month, year));
    }

    @PostMapping("/approve")
    @Operation(summary = "ADMIN approval endpoint calling a SQL SP Cursor routine to generate notification logs and set status to Paid")
    public ResponseEntity<String> approvePayroll(@RequestParam String month, @RequestParam Integer year, @RequestParam String institution) {
        payslipService.approvePayroll(month, year, institution);
        return ResponseEntity.ok("Payroll for " + month + "/" + year + " has been approved successfully for " + institution + " and database notifications have been queued.");
    }

    @GetMapping("/{id}")
    @Operation(summary = "View a payslip by ID")
    public ResponseEntity<PayslipDto> getPayslipById(@PathVariable Long id) {
        return ResponseEntity.ok(payslipService.getPayslipById(id));
    }

    @GetMapping("/employee/{employeeId}")
    @Operation(summary = "Individual employee view of their payslips")
    public ResponseEntity<List<PayslipDto>> getPayslipsByEmployeeId(@PathVariable Long employeeId) {
        return ResponseEntity.ok(payslipService.getPayslipsByEmployeeId(employeeId));
    }

    @GetMapping
    @Operation(summary = "Get all generated payslips")
    public ResponseEntity<List<PayslipDto>> getAllPayslips() {
        return ResponseEntity.ok(payslipService.getAllPayslips());
    }

    @GetMapping("/download/{id}")
    @Operation(summary = "Download a payroll draft / payslip as a PDF file")
    public ResponseEntity<byte[]> downloadPayslipPdf(@PathVariable Long id) {
        byte[] pdfBytes = payslipService.generatePayslipPdf(id);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "payslip_draft_" + id + ".pdf");
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        
        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }
}
