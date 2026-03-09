package com.odegaa.practiceproject.models.advertisingDashboard;

import java.time.LocalDate;

public record LastMonthAdsDashboardDTO(
        LocalDate lastMonthAds,
        Long count
) {
}
