package com.odegaa.practiceproject.models.address;

import jakarta.persistence.Column;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
public class AddressDTO {
    private Long id;
    private String streetName;
    private Integer numberOfHouse;

    @Column(name = "region_id")
    private Long regionId;
    private Long cityId;
}
