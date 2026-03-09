package com.odegaa.practiceproject.models.address;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class RegionDTO {
    private Long id;
    private String name;
    private CityDTO cityDTO;
}
