package com.odegaa.practiceproject.models.address;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class UpdateRegionDTO {
    private Long id;
    private String name;
    private Long cityId;
}
