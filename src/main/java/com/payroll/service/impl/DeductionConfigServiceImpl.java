package com.payroll.service.impl;

import com.payroll.dto.DeductionConfigDto;
import com.payroll.entity.DeductionConfig;
import com.payroll.exception.ResourceNotFoundException;
import com.payroll.repository.DeductionConfigRepository;
import com.payroll.service.DeductionConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DeductionConfigServiceImpl implements DeductionConfigService {

    private final DeductionConfigRepository repository;

    @Override
    public DeductionConfigDto createOrUpdateDeduction(DeductionConfigDto dto) {
        Optional<DeductionConfig> existing = repository.findByName(dto.getName());
        DeductionConfig deduction;
        if (existing.isPresent()) {
            deduction = existing.get();
            deduction.setPercentage(dto.getPercentage());
        } else {
            deduction = DeductionConfig.builder()
                    .name(dto.getName())
                    .percentage(dto.getPercentage())
                    .build();
        }
        DeductionConfig saved = repository.save(deduction);
        return mapToDto(saved);
    }

    @Override
    public DeductionConfigDto getDeductionByName(String name) {
        DeductionConfig deduction = repository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Deduction not found with name: " + name));
        return mapToDto(deduction);
    }

    @Override
    public List<DeductionConfigDto> getAllDeductions() {
        return repository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private DeductionConfigDto mapToDto(DeductionConfig deduction) {
        return DeductionConfigDto.builder()
                .id(deduction.getId())
                .name(deduction.getName())
                .percentage(deduction.getPercentage())
                .build();
    }
}
