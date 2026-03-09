package com.odegaa.practiceproject.entities;

import com.odegaa.practiceproject.entities.templates.Base;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "ads_types")
public class AdsTypes extends Base {
    @Column(length = 50, nullable = false, unique = true)
    private String name;
}
