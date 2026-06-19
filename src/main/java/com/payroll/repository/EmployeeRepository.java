package com.payroll.repository;

import com.payroll.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByStatus(String status);
    java.util.Optional<Employee> findByEmail(String email);
}
