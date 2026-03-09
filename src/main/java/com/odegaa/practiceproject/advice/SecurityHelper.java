package com.odegaa.practiceproject.advice;

import com.odegaa.practiceproject.entities.Employee;
import com.odegaa.practiceproject.repositories.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityHelper {

    private final EmployeeRepository employeeRepository;

    public String getACCOUNT_USER_NAME() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return "SYSTEM NOT AUTHENTICATED";
        }
        return authentication.getName();
    }

    public Employee getACCOUNT_EMPLOYEE() {
        String ACCOUNT_USER_NAME = getACCOUNT_USER_NAME();
        return employeeRepository.findByUsername(ACCOUNT_USER_NAME);
    }

}
