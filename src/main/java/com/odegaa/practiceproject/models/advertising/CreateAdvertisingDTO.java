package com.odegaa.practiceproject.models.advertising;

import java.time.LocalDate;

public record CreateAdvertisingDTO(
        Long typeId,
        Double expense,
        Integer duration,
        LocalDate startDate
) {
}
