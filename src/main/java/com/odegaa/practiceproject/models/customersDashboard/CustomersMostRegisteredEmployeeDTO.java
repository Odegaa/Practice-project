package com.odegaa.practiceproject.models.customersDashboard;

public record CustomersMostRegisteredEmployeeDTO(
        String fullName,
        Long registeredCustomersCount
) {
}
