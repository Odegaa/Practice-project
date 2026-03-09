package com.odegaa.practiceproject.models.employee;

import com.odegaa.practiceproject.entities.templates.Department;
import com.odegaa.practiceproject.models.address.AddressDTO;
import com.odegaa.practiceproject.models.passport.PassportDTO;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class UpdateEmployeeDTO {
    private String firstName;
    private String lastName;
    private String name;
    private String username;
    private Integer age;
    private Double salary;

    private AddressDTO address;
    private PassportDTO passport;

    private Department department;
}
