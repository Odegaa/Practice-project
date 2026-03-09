package com.odegaa.practiceproject.entities;

import com.odegaa.practiceproject.entities.templates.Base;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity(name = "passport")
public class Passport extends Base {

    @NotNull
    @Column(length = 2)
    private String serial;

    @NotNull
    @Column(length = 7)
    private String serialNumber;

    @NotNull
    @Column(length = 14, unique = true)
    private String identificationNumber;

    @NotNull
    @Column(length = 20)
    private String nation;

    private LocalDate birthDate;

}
