package com.odegaa.practiceproject.mappers;

import com.odegaa.practiceproject.entities.Passport;
import com.odegaa.practiceproject.models.passport.PassportDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PassportMappers {

    Passport toPassport(PassportDTO passportDTO);

    PassportDTO toPassportDTO(Passport passport);

    List<PassportDTO> toListPassportDTO(List<Passport> passports);

    Passport toPasswordUpdate(PassportDTO passportDTO, @MappingTarget Passport passport);
}
