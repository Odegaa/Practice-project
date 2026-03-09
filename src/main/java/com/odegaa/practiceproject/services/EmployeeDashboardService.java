package com.odegaa.practiceproject.services;

import com.odegaa.practiceproject.advice.ApiResponse;
import com.odegaa.practiceproject.models.employeeDashboard.EmployeeDashboardDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EmployeeDashboardService {

    ApiResponse<List<EmployeeDashboardDTO>> getListCountEmployeeOfDepartment(Pageable pageable);

    ApiResponse<Long> getListEmployeeSalaryByDepartment();

}
