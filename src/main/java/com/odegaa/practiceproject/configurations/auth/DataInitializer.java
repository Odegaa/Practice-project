package com.odegaa.practiceproject.configurations.auth;

import com.odegaa.practiceproject.entities.Employee;
import com.odegaa.practiceproject.entities.templates.Department;
import com.odegaa.practiceproject.entities.templates.Roles;
import com.odegaa.practiceproject.repositories.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        Employee admin = employeeRepository.findByUsername("admin");

        if (admin == null) {
            Employee newAdmin = new Employee();
            HashSet<Roles> roles = new HashSet<>();
            roles.add(Roles.ADMIN);

            newAdmin.setFirstName("admin");
            newAdmin.setLastName("admin");
            newAdmin.setName("admin");
            newAdmin.setUsername("admin");
            newAdmin.setPassword(passwordEncoder.encode("admin123"));
            newAdmin.setRoles(roles);
            newAdmin.setDepartment(Department.OPERATIONAL);

            employeeRepository.save(newAdmin);

            log.info("Admin has been saved and created!");
        }
    }
}
