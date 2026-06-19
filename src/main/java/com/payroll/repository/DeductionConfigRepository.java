package com.payroll.repository;

import com.payroll.entity.DeductionConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface DeductionConfigRepository extends JpaRepository<DeductionConfig, Long> {
    Optional<DeductionConfig> findByName(String name);
}
