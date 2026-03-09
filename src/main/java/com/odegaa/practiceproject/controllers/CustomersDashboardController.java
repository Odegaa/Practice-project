package com.odegaa.practiceproject.controllers;

import com.odegaa.practiceproject.advice.ApiResponse;
import com.odegaa.practiceproject.models.customersDashboard.CustomersCountDTO;
import com.odegaa.practiceproject.models.customersDashboard.CustomersDailyStatsDashboardDTO;
import com.odegaa.practiceproject.models.customersDashboard.CustomersMostRegisteredEmployeeDTO;
import com.odegaa.practiceproject.services.CustomersDashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "3.1. CRM Analytics & Dashboard",
        description = "Customer-related statistics for the Director's office. Includes registration trends and staff performance.")
@PreAuthorize("hasRole('DIRECTOR')")
@RestController
@RequestMapping("/api/v1/customers/dashboard")
public class CustomersDashboardController {

    private final CustomersDashboardService customersDashboardService;

    public CustomersDashboardController(CustomersDashboardService customersDashboardService) {
        this.customersDashboardService = customersDashboardService;
    }

    @Operation(summary = "Daily customer registration stats",
            description = "Accessible by: DIRECTOR. Returns registration counts for a specific date.")
    @GetMapping("/daily-stats")
    public ResponseEntity<ApiResponse<CustomersDailyStatsDashboardDTO>> getDailyStats(@Parameter(description = "Date for statistics (format: YYYY-MM-DD)", example = "2026-02-15")
                                                                                      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        ApiResponse<CustomersDailyStatsDashboardDTO> response = customersDashboardService.getDailyStats(date);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Best employee (Top registrations)", description = "Identifies the single employee who registered the most customers overall.")
    @GetMapping("/best-employee")
    public ResponseEntity<ApiResponse<CustomersMostRegisteredEmployeeDTO>> getEmployeeWhereMoreCustomersRegisteredM() {
        ApiResponse<CustomersMostRegisteredEmployeeDTO> response = customersDashboardService.getMostEmployeeRegisteredCustomers();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Top 3 performing employees", description = "Returns a leaderboard of the top 3 staff members by customer registrations.")
    @GetMapping("/top-employees")
    public ResponseEntity<ApiResponse<List<CustomersMostRegisteredEmployeeDTO>>> getTopThreeEmployeesRegisteredCustomers() {
        ApiResponse<List<CustomersMostRegisteredEmployeeDTO>> response = customersDashboardService.getTopThreeEmployeesMostRegisteredCustomers();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Monthly growth stats", description = "Total number of new customers registered in the last 30 days.")
    @GetMapping("/last-month-stats")
    public ResponseEntity<ApiResponse<CustomersCountDTO>> getLastMonthStats() {
        ApiResponse<CustomersCountDTO> customersCountLastMonth = customersDashboardService.getCustomersCountLastMonth();
        return ResponseEntity.ok(customersCountLastMonth);
    }

    @Operation(summary = "Peak registration day", description = "Finds the specific day in the last month with the highest registration activity.")
    @GetMapping("/peak-registration-date")
    public ResponseEntity<ApiResponse<CustomersDailyStatsDashboardDTO>> getPeakRegistrationDate() {
        ApiResponse<CustomersDailyStatsDashboardDTO> response = customersDashboardService.getPeakRegistrationDate();
        return ResponseEntity.ok(response);
    }

}
