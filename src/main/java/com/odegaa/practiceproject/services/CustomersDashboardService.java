package com.odegaa.practiceproject.services;

import com.odegaa.practiceproject.advice.ApiResponse;
import com.odegaa.practiceproject.models.customersDashboard.CustomersCountDTO;
import com.odegaa.practiceproject.models.customersDashboard.CustomersDailyStatsDashboardDTO;
import com.odegaa.practiceproject.models.customersDashboard.CustomersMostRegisteredEmployeeDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface CustomersDashboardService {

    ApiResponse<CustomersDailyStatsDashboardDTO> getDailyStats(LocalDate date);

    ApiResponse<CustomersMostRegisteredEmployeeDTO> getMostEmployeeRegisteredCustomers();

    ApiResponse<List<CustomersMostRegisteredEmployeeDTO>> getTopThreeEmployeesMostRegisteredCustomers();

    ApiResponse<CustomersCountDTO> getCustomersCountLastMonth();

    ApiResponse<CustomersDailyStatsDashboardDTO> getPeakRegistrationDate();
}
