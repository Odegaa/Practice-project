package com.odegaa.practiceproject.models.address;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CreateRegionDTO {
    private String name;
    private Long cityId;
}
