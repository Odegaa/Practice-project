package com.odegaa.practiceproject.controllers;

import com.odegaa.practiceproject.advice.ApiResponse;
import com.odegaa.practiceproject.models.employeeDashboard.EmployeeDashboardDTO;
import com.odegaa.practiceproject.services.EmployeeDashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "3.2. HR Analytics & Dashboard",
        description = "Strategic HR insights for the Director. Includes department distribution, headcount, and payroll totals.")
@PreAuthorize("hasRole('DIRECTOR')")
@RestController
@RequestMapping("api/v1/dashboard")
public class EmployeeDashboardController {

    private final EmployeeDashboardService employeeDashboardService;

    public EmployeeDashboardController(EmployeeDashboardService employeeDashboardService) {
        this.employeeDashboardService = employeeDashboardService;
    }

    @Operation(summary = "Employee distribution by departments",
            description = "Accessible by: DIRECTOR. Returns the number of employees and their percentage representation in each department. Supports pagination.")
    @GetMapping("/totalCount")
    public ResponseEntity<ApiResponse<List<EmployeeDashboardDTO>>> getEmployeeDashboard(@Parameter(hidden = true) Pageable pageable) {
        ApiResponse<List<EmployeeDashboardDTO>> response = employeeDashboardService.getListCountEmployeeOfDepartment(pageable);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Total payroll (Salary sum)",
            description = "Accessible by: DIRECTOR. Calculates the total sum of all employee salaries across the company.")
    @GetMapping("/totalSum")
    public ResponseEntity<ApiResponse<Long>> getTotalSum() {
        ApiResponse<Long> response = employeeDashboardService.getListEmployeeSalaryByDepartment();
        return ResponseEntity.ok(response);
    }

}
