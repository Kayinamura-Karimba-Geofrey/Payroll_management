package com.payroll.repository;

import com.payroll.entity.EmployeeMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EmployeeMessageRepository extends JpaRepository<EmployeeMessage, Long> {
    List<EmployeeMessage> findByEmployee_EmployeeId(Long employeeId);
    List<EmployeeMessage> findByMonthAndYear(String month, Integer year);
}
