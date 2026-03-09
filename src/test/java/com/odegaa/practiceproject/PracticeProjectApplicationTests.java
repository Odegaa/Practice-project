package com.odegaa.practiceproject;

import com.odegaa.practiceproject.entities.City;
import com.odegaa.practiceproject.mappers.CityMappers;
import com.odegaa.practiceproject.models.address.CityDTO;
import com.odegaa.practiceproject.repositories.CityRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class PracticeProjectApplicationTests {

    @Autowired
    private CityMappers cityMappers;

    @Autowired
    private CityRepository cityRepository;

    @Test
    void testCityUpdateMapping() {
        City excitingCity = new City();
        excitingCity.setId(1L);
        excitingCity.setName("Tashkent");

        CityDTO cityDTO = new CityDTO();
        cityDTO.setName("Tashkent");

        cityMappers.updateEntityFromDto(cityDTO, excitingCity);
        City updatedCity = cityRepository.save(excitingCity);

        Assertions.assertEquals(updatedCity.getName(), excitingCity.getName());
        Assertions.assertNotNull(updatedCity.getId());

    }

}
