package com.odegaa.practiceproject.controllers;

import com.odegaa.practiceproject.advice.ApiResponse;
import com.odegaa.practiceproject.models.employee.CreateEmployeeDTO;
import com.odegaa.practiceproject.models.employee.EmployeeDTO;
import com.odegaa.practiceproject.models.employee.UpdateEmployeeDTO;
import com.odegaa.practiceproject.services.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "6. HR Management System",
        description = "Core module for managing company staff. Includes recruitment, profile updates, and dismissal processes.")
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/api/v1/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Operation(summary = "Onboard a new employee",
            description = "Accessible by: ADMIN, DIRECTOR, HR_MANAGER. Requires full personal data: identification number, Passport details, salary, and address.")
    @PreAuthorize("hasAnyRole('ADMIN', 'DIRECTOR', 'HR_MANAGER')")
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<EmployeeDTO>> createEmployee(@RequestBody @Valid CreateEmployeeDTO createEmployeeDTO) {
        ApiResponse<EmployeeDTO> employee = employeeService.createEmployee(createEmployeeDTO);
        return ResponseEntity.ok(employee);
    }

    @Operation(summary = "Get list of all employees",
            description = "Accessible by: ADMIN, DIRECTOR, HR_MANAGER. Returns a summary of all staff members.")
    @PreAuthorize("hasAnyRole('ADMIN', 'DIRECTOR', 'HR_MANAGER')")
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<EmployeeDTO>>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @Operation(summary = "Get employee details by ID",
            description = "Accessible by: ADMIN, DIRECTOR, HR_MANAGER, EMPLOYEE. Provides full profile information.")
    @PreAuthorize("hasAnyRole('ADMIN', 'DIRECTOR', 'HR_MANAGER', 'EMPLOYEE')")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EmployeeDTO>> getEmployee(@PathVariable Long id) {
        ApiResponse<EmployeeDTO> employeeById = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(employeeById);
    }

    @Operation(summary = "Dismiss an employee",
            description = "Accessible by: ADMIN, DIRECTOR, HR_MANAGER. Marks the employee as inactive (soft delete) in the system.")
    @PreAuthorize("hasAnyRole('ADMIN', 'DIRECTOR', 'HR_MANAGER')")
    @PatchMapping("/archive/{id}")
    public ResponseEntity<ApiResponse<EmployeeDTO>> dismissEmployee(@PathVariable Long id) {
        ApiResponse<EmployeeDTO> employeeById = employeeService.dismissEmployee(id);
        return ResponseEntity.ok(employeeById);
    }

    @Operation(summary = "Update employee information",
            description = "Accessible by: ADMIN, DIRECTOR, HR_MANAGER. Allows changing salary, position, or contact details.")
    @PreAuthorize("hasAnyRole('ADMIN', 'DIRECTOR', 'HR_MANAGER')")
    @PatchMapping("/update/{id}")
    public ResponseEntity<ApiResponse<EmployeeDTO>> updateEmployee(@PathVariable Long id,
                                                                   @RequestBody @Valid UpdateEmployeeDTO updateEmployeeDTO) {
        ApiResponse<EmployeeDTO> employeeDTOApiResponse = employeeService.updateEmployee(id, updateEmployeeDTO);
        return ResponseEntity.ok(employeeDTOApiResponse);
    }


}
