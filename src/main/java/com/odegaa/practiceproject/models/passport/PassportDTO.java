package com.odegaa.practiceproject.models.passport;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class PassportDTO {
    private Long id;
    private String serial;
    private String serialNumber;
    private String identificationNumber;
    private String nation;
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate birthDate;
}
