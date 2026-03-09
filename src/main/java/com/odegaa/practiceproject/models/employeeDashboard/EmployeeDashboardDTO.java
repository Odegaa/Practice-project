package com.odegaa.practiceproject.models.employeeDashboard;

public record EmployeeDashboardDTO(
        String department,
        Long employeeCount,
        Double percentage
) {
}
