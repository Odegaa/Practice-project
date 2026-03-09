package com.odegaa.practiceproject.services;

import com.odegaa.practiceproject.advice.ApiResponse;
import com.odegaa.practiceproject.models.advertisingDashboard.AdsDashboardCountDTO;
import com.odegaa.practiceproject.models.advertisingDashboard.AdsDashboardDTO;
import com.odegaa.practiceproject.models.advertisingDashboard.LastMonthAdsDashboardDTO;

import java.util.List;

public interface AdsDashboardService {

    ApiResponse<AdsDashboardDTO> getMostExpensiveAdsTypes();

    ApiResponse<AdsDashboardDTO> getTopSpenderEmployee();

    ApiResponse<LastMonthAdsDashboardDTO> getLastMonthAds();

    ApiResponse<List<AdsDashboardCountDTO>> getCountAds();

    ApiResponse<LastMonthAdsDashboardDTO> getFinishedAdsCount();
}
