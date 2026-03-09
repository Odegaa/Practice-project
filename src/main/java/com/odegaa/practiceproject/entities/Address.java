package com.odegaa.practiceproject.entities;

import com.odegaa.practiceproject.entities.templates.Base;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@Entity(name = "address")
public class Address extends Base {

    @NotNull
    @Column(length = 30)
    private String streetName;

    @NotNull
    private int numberOfHouse;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "region_id")
    private Region region;

}
