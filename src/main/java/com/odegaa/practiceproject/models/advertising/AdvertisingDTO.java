package com.odegaa.practiceproject.models.advertising;

import com.odegaa.practiceproject.entities.templates.Status;

import java.time.LocalDate;

public record AdvertisingDTO(
        Long id,
        Long typeId,
        Double expense,
        Integer duration,
        LocalDate startDate,
        Long employeeId,
        Status status
) {
}
