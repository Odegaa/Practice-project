package com.odegaa.practiceproject.controllers;

import com.odegaa.practiceproject.advice.ApiResponse;
import com.odegaa.practiceproject.models.advertisingDashboard.AdsDashboardCountDTO;
import com.odegaa.practiceproject.models.advertisingDashboard.AdsDashboardDTO;
import com.odegaa.practiceproject.models.advertisingDashboard.LastMonthAdsDashboardDTO;
import com.odegaa.practiceproject.services.AdsDashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "3. Sales Analytics & Marketing Dashboard",
        description = "Advanced marketing statistics for decision-making. Restricted to DIRECTOR role only.")
@PreAuthorize("hasRole('DIRECTOR')")
@RestController
@RequestMapping("/api/v1/dashboard/advertising")
public class AdsDashboardController {

    private final AdsDashboardService adsDashboardService;

    public AdsDashboardController(AdsDashboardService adsDashboardService) {
        this.adsDashboardService = adsDashboardService;
    }

    @Operation(summary = "Highest advertising expenses by type",
            description = "Identifies which ad type (e.g., Instagram, Google Ads) has the highest total expenditure.")
    @GetMapping("/most-expenses")
    public ResponseEntity<ApiResponse<AdsDashboardDTO>> getMostExpenses() {
        ApiResponse<AdsDashboardDTO> response = adsDashboardService.getMostExpensiveAdsTypes();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Top spending marketing employee",
            description = "Returns the employee who managed the largest advertising budget.")
    @GetMapping("/top-spended-employee")
    public ResponseEntity<ApiResponse<AdsDashboardDTO>> getTopSpenderEmployee() {
        ApiResponse<AdsDashboardDTO> response = adsDashboardService.getTopSpenderEmployee();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "New ads count (Last 30 days)",
            description = "Shows the number of advertising campaigns launched within the last month.")
    @GetMapping("/last-month-ads-count")
    public ResponseEntity<ApiResponse<LastMonthAdsDashboardDTO>> getLastMonthAds() {
        ApiResponse<LastMonthAdsDashboardDTO> response = adsDashboardService.getLastMonthAds();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Ads distribution by type",
            description = "Provides a breakdown of how many ads are running for each specific category.")
    @GetMapping("/every-ads-count")
    public ResponseEntity<ApiResponse<List<AdsDashboardCountDTO>>> getEveryAdsCount() {
        ApiResponse<List<AdsDashboardCountDTO>> response = adsDashboardService.getCountAds();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Finished ads count (Last 30 days)",
            description = "Shows the number of advertising campaigns that ended within the last month.")
    @GetMapping("/last-month-finished-ads")
    public ResponseEntity<ApiResponse<LastMonthAdsDashboardDTO>> getLastMonthFinishedAds() {
        ApiResponse<LastMonthAdsDashboardDTO> response = adsDashboardService.getFinishedAdsCount();
        return ResponseEntity.ok(response);
    }
}
