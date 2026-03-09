package com.odegaa.practiceproject.mappers;

import com.odegaa.practiceproject.entities.AdsTypes;
import com.odegaa.practiceproject.models.advertising.AdsTypesDTO;
import com.odegaa.practiceproject.models.advertising.CreateAdsTypeDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AdsTypeMappers {

    AdsTypes toEntity(AdsTypesDTO dto);

    AdsTypesDTO toDto(AdsTypes entity);

    AdsTypes toEntityFromCreate(CreateAdsTypeDTO dto);

}
