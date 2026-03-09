package com.odegaa.practiceproject.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.odegaa.practiceproject.entities.templates.Base;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity(name = "advertising")
public class Advertising extends Base {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ads_type_id")
    private AdsTypes type;

    @Column(length = 50, nullable = false)
    private Double expense;

    @NotNull
    private Integer duration;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;
}
