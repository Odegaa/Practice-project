package com.odegaa.practiceproject.mappers;

import com.odegaa.practiceproject.entities.Employee;
import com.odegaa.practiceproject.models.employee.CreateEmployeeDTO;
import com.odegaa.practiceproject.models.employee.EmployeeDTO;
import com.odegaa.practiceproject.models.employee.UpdateEmployeeDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface EmployeeMappers {

    Employee toEntity(CreateEmployeeDTO employee);

    @Mapping(source = "address.region.id", target = "address.regionId")
    @Mapping(source = "address.region.city.id", target = "address.cityId")
    EmployeeDTO toEmployeeDTO(Employee employee);

    List<EmployeeDTO> toEmployeeDTOList(List<Employee> employee);

    Employee updateEntityFromDTO(UpdateEmployeeDTO employeeDTO, @MappingTarget Employee employee);

}
