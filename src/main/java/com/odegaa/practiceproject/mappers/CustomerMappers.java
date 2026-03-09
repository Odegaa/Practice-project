package com.odegaa.practiceproject.mappers;

import com.odegaa.practiceproject.entities.Customer;
import com.odegaa.practiceproject.models.customer.CustomerDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CustomerMappers {

    @Mapping(source = "addressDTO", target = "address")
    @Mapping(source = "passportDTO", target = "passport")
    Customer toEntity(CustomerDTO customerDTO);

    @Mapping(source = "address", target = "addressDTO")
    @Mapping(source = "passport", target = "passportDTO")
    CustomerDTO toEntityDTO(Customer customer);

    Customer toCustomerFromDTO(CustomerDTO customerDTO, @MappingTarget Customer customer);

}
