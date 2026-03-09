package com.odegaa.practiceproject.models.employee;

import com.odegaa.practiceproject.entities.templates.Department;
import com.odegaa.practiceproject.entities.templates.Roles;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
public class CreateEmployeeDTO {
    @NotBlank(message = "Имя обязательно!")
    private String name;

    @NotBlank(message = "Фамилия обязательно!")
    private String firstName;

    @NotBlank(message = "Отчество обязательно!")
    private String lastName;

    @NotBlank(message = "Логин обязательно!")
    private String username;

    @Size(min = 8, message = "Пароль должен содеражать больше 8 симловов", max = 30)
    @NotBlank(message = "Пароль обязательно!")
    private String password;

    @NotEmpty(message = "Указать роля обязательно!")
    private Set<Roles> roles = new HashSet<>();

    @NotNull(message = "Укажите департамент сотрудника!")
    private Department department;

}
