package com.payroll.config;

import com.payroll.entity.DeductionConfig;
import com.payroll.entity.User;
import com.payroll.repository.DeductionConfigRepository;
import com.payroll.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class DatabaseInitializer implements CommandLineRunner {

    private final DeductionConfigRepository deductionConfigRepository;
    private final UserRepository userRepository;
    private final com.payroll.repository.EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... args) throws Exception {
        // 1. Seed standard deduction rates
        Map<String, Double> defaultRates = new HashMap<>();
        defaultRates.put("EmployeeTax", 30.0);
        defaultRates.put("Pension", 6.0);
        defaultRates.put("MedicalInsurance", 5.0);
        defaultRates.put("Others", 5.0);
        defaultRates.put("House", 14.0);
        defaultRates.put("Transport", 14.0);

        for (Map.Entry<String, Double> entry : defaultRates.entrySet()) {
            if (deductionConfigRepository.findByName(entry.getKey()).isEmpty()) {
                deductionConfigRepository.save(
                    DeductionConfig.builder()
                        .name(entry.getKey())
                        .percentage(entry.getValue())
                        .build()
                );
                log.info("Seeded deduction rate: {} = {}%", entry.getKey(), entry.getValue());
            }
        }

        // 2. Seed default system users
        try {
            if (!userRepository.existsByEmail("admin@gmail.com")) {
                userRepository.save(
                    User.builder()
                        .email("admin@gmail.com")
                        .password(passwordEncoder.encode("admin123"))
                        .role("ROLE_ADMIN")
                        .build()
                );
                log.info("Seeded default admin account (email: admin@gmail.com, password: admin123)");
            }

            if (!userRepository.existsByEmail("manager@gmail.com")) {
                userRepository.save(
                    User.builder()
                        .email("manager@gmail.com")
                        .password(passwordEncoder.encode("manager123"))
                        .role("ROLE_MANAGER")
                        .build()
                );
                log.info("Seeded default manager account (email: manager@gmail.com, password: manager123)");
            }

            // Seed default Employee User Account and Employee Entity
            if (employeeRepository.findByEmail("peter@gmail.com").isEmpty()) {
                com.payroll.entity.Employee employee = com.payroll.entity.Employee.builder()
                        .firstName("Peter")
                        .lastName("Rwanda")
                        .email("peter@gmail.com")
                        .district("Gasabo")
                        .mobile("+250788888888")
                        .dateOfBirth(java.time.LocalDate.of(1995, 1, 1))
                        .department("Engineering")
                        .position("Software Engineer")
                        .baseSalary(70000.0)
                        .status("ACTIVE")
                        .joiningDate(java.time.LocalDate.of(2024, 1, 1))
                        .build();
                employeeRepository.save(employee);
                log.info("Seeded default Employee entity (Peter Rwanda)");

                if (!userRepository.existsByEmail("peter@gmail.com")) {
                    userRepository.save(
                        User.builder()
                            .email("peter@gmail.com")
                            .password(passwordEncoder.encode("peter123"))
                            .role("ROLE_EMPLOYEE")
                            .build()
                    );
                    log.info("Seeded default employee login account (email: peter@gmail.com, password: peter123)");
                }
            }
        } catch (Exception e) {
            log.warn("Database schema mismatch detected (e.g. old users table). Dropping tables to reset schema...");
            try {
                jdbcTemplate.execute("DROP TABLE IF EXISTS employee_messages CASCADE;");
                jdbcTemplate.execute("DROP TABLE IF EXISTS payslips CASCADE;");
                jdbcTemplate.execute("DROP TABLE IF EXISTS deductions CASCADE;");
                jdbcTemplate.execute("DROP TABLE IF EXISTS employees CASCADE;");
                jdbcTemplate.execute("DROP TABLE IF EXISTS users CASCADE;");
                log.warn("Successfully dropped all tables to reset schema. Please restart the application once to automatically recreate tables.");
            } catch (Exception dropEx) {
                log.error("Failed to drop tables: {}", dropEx.getMessage());
            }
            throw e;
        }

        // 3. Register Stored Procedure for payslip approvals and cursor messaging
        try {
            jdbcTemplate.execute("DROP PROCEDURE IF EXISTS approve_payroll_sp;");
            
            String spSql = "CREATE OR REPLACE PROCEDURE approve_payroll_sp(p_month VARCHAR(20), p_year INT, p_institution VARCHAR(255))\n" +
                    "LANGUAGE plpgsql\n" +
                    "AS $$\n" +
                    "DECLARE\n" +
                    "    v_id BIGINT;\n" +
                    "    v_first_name VARCHAR(255);\n" +
                    "    v_employee_id BIGINT;\n" +
                    "    v_net_salary DOUBLE PRECISION;\n" +
                    "    cur CURSOR FOR \n" +
                    "        SELECT p.id, e.first_name, e.employee_id, p.net_salary \n" +
                    "        FROM payslips p\n" +
                    "        JOIN employees e ON p.employee_id = e.employee_id\n" +
                    "        WHERE p.month = p_month AND p.year = p_year AND p.status = 'Draft';\n" +
                    "BEGIN\n" +
                    "    OPEN cur;\n" +
                    "    LOOP\n" +
                    "        FETCH cur INTO v_id, v_first_name, v_employee_id, v_net_salary;\n" +
                    "        EXIT WHEN NOT FOUND;\n" +
                    "        \n" +
                    "        -- Insert message notification linked to Employee\n" +
                    "        INSERT INTO employee_messages (employee_id, message_text, month, year, sent_at)\n" +
                    "        VALUES (v_employee_id, \n" +
                    "                CONCAT('Dear ', v_first_name, ', Your salary of ', p_month, '/', p_year, ' from ', p_institution, ' ', v_net_salary, ' RWF has been credited to your ', v_employee_id, ' account Successfully.'), \n" +
                    "                p_month, \n" +
                    "                p_year, \n" +
                    "                NOW());\n" +
                    "                \n" +
                    "        -- Update payslip status to Paid\n" +
                    "        UPDATE payslips SET status = 'Paid' WHERE id = v_id;\n" +
                    "    END LOOP;\n" +
                    "    CLOSE cur;\n" +
                    "END;\n" +
                    "$$;";
            
            jdbcTemplate.execute(spSql);
            log.info("Registered Stored Procedure: approve_payroll_sp successfully.");
        } catch (Exception e) {
            log.error("Failed to register Stored Procedure: {}", e.getMessage());
        }
    }
}
