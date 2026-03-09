package com.odegaa.practiceproject.models.address;

import jakarta.persistence.Column;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CreateCityDTO {
    @Column(unique = true, nullable = false, length = 30)
    private String name;
}
