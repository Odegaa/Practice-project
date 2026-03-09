package com.odegaa.practiceproject.models.employee;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.odegaa.practiceproject.entities.templates.Department;
import com.odegaa.practiceproject.models.address.AddressDTO;
import com.odegaa.practiceproject.models.passport.PassportDTO;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
public class EmployeeDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String name;
    private String username;
    private Integer age;
    private Double salary;

    private Department department;

    private AddressDTO address;
    private PassportDTO passport;
}
