package com.odegaa.practiceproject.mappers;

import com.odegaa.practiceproject.entities.City;
import com.odegaa.practiceproject.models.address.CityDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CityMappers {

    CityDTO toCityDto(City city);

    @Mapping(target = "status", constant = "ACTIVE")
    City createCity(CityDTO cityDTO);

    City updateEntityFromDto(CityDTO dto, @MappingTarget City city);
}
