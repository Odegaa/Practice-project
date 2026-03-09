package com.odegaa.practiceproject.services;

import com.odegaa.practiceproject.advice.ApiResponse;
import com.odegaa.practiceproject.models.employee.CreateEmployeeDTO;
import com.odegaa.practiceproject.models.employee.EmployeeDTO;
import com.odegaa.practiceproject.models.employee.UpdateEmployeeDTO;

import java.util.List;

public interface EmployeeService {

    ApiResponse<EmployeeDTO> createEmployee(CreateEmployeeDTO createEmployeeDTO);

    ApiResponse<List<EmployeeDTO>> getAllEmployees();

    ApiResponse<EmployeeDTO> getEmployeeById(Long id);

    ApiResponse<EmployeeDTO> dismissEmployee(Long id);

    ApiResponse<EmployeeDTO> updateEmployee(Long id, UpdateEmployeeDTO employeeDTO);
}
