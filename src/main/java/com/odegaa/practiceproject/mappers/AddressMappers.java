package com.odegaa.practiceproject.mappers;

import com.odegaa.practiceproject.entities.Address;
import com.odegaa.practiceproject.models.address.AddressDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AddressMappers {

    Address toAddress(AddressDTO addressDTO);

    @Mapping(source = "region.id", target = "regionId")
    @Mapping(source = "region.city.id", target = "cityId")
    AddressDTO toAddressDTO(Address address);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "regionId", target = "region.id")
    @Mapping(source = "cityId", target = "region.city.id")
    Address toEntityAddressFromDTO(AddressDTO addressDTO, @MappingTarget Address address);

}
