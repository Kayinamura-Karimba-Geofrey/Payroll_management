package com.payroll.repository;

import com.payroll.entity.Employee;
import com.payroll.entity.Payslip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PayslipRepository extends JpaRepository<Payslip, Long> {
    boolean existsByEmployeeAndMonthAndYear(Employee employee, String month, Integer year);
    List<Payslip> findByEmployee_EmployeeId(Long employeeId);
    List<Payslip> findByMonthAndYear(String month, Integer year);
}
