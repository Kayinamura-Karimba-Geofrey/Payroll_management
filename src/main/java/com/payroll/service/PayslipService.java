package com.payroll.service;

import com.payroll.dto.PayslipDto;
import java.util.List;

public interface PayslipService {
    List<PayslipDto> generatePayrollForAllActiveEmployees(String month, Integer year);
    void approvePayroll(String month, Integer year, String institution);
    PayslipDto getPayslipById(Long id);
    List<PayslipDto> getPayslipsByEmployeeId(Long employeeId);
    List<PayslipDto> getAllPayslips();
    byte[] generatePayslipPdf(Long payslipId);
}
