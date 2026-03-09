package com.odegaa.practiceproject.mappers;

import com.odegaa.practiceproject.entities.Advertising;
import com.odegaa.practiceproject.models.advertising.AdvertisingDTO;
import com.odegaa.practiceproject.models.advertising.CreateAdvertisingDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AdvertisingMappers {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "employee", ignore = true)
    Advertising toAdvertising(CreateAdvertisingDTO advertisingDTO);

    @Mapping(source = "employee.id", target = "employeeId")
    AdvertisingDTO toAdvertisingDTO(Advertising advertising);

}
