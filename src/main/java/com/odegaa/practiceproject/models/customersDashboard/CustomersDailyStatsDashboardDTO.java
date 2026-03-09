package com.odegaa.practiceproject.models.customersDashboard;

import java.time.LocalDate;

public record CustomersDailyStatsDashboardDTO(
        LocalDate date,
        Long registeredCount
) {
}
