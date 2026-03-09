package com.odegaa.practiceproject.models.customer;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.odegaa.practiceproject.models.address.AddressDTO;
import com.odegaa.practiceproject.models.passport.PassportDTO;

import java.time.LocalDateTime;

public record CustomerDTO(
        Long id,
        String firstName,
        String name,
        AddressDTO addressDTO,
        PassportDTO passportDTO,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime registrationDateTime
) {
}
