package com.odegaa.practiceproject.entities;

import com.odegaa.practiceproject.entities.templates.Base;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity(name = "city")
public class City extends Base {
    @NotNull
    @Column(unique = true, nullable = false, length = 30)
    private String name;
}
