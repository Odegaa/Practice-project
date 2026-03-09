package com.odegaa.practiceproject.mappers;

import com.odegaa.practiceproject.entities.Region;
import com.odegaa.practiceproject.models.address.CreateRegionDTO;
import com.odegaa.practiceproject.models.address.RegionDTO;
import com.odegaa.practiceproject.models.address.UpdateRegionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RegionMappers {

    @Mapping(source = "city", target = "cityDTO")
    RegionDTO toRegionDTO(Region region);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "city", ignore = true)
    Region toRegionEntity(UpdateRegionDTO updateRegionDTO, @MappingTarget Region region);

    Region toCreateRegionEntity(CreateRegionDTO createRegionDTO);
}
