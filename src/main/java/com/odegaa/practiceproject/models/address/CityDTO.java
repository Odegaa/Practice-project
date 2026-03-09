package com.odegaa.practiceproject.models.address;

import com.odegaa.practiceproject.entities.templates.Status;
import jakarta.persistence.Column;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
public class CityDTO {
    private Long id;
    @Column(unique = true, nullable = false, length = 30)
    private String name;
}
