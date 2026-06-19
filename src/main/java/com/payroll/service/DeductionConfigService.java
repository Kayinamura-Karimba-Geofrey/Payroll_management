package com.payroll.service;

import com.payroll.dto.DeductionConfigDto;
import java.util.List;

public interface DeductionConfigService {
    DeductionConfigDto createOrUpdateDeduction(DeductionConfigDto dto);
    DeductionConfigDto getDeductionByName(String name);
    List<DeductionConfigDto> getAllDeductions();
}
