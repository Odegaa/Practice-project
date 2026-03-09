package com.odegaa.practiceproject.repositories;

import com.odegaa.practiceproject.entities.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    boolean existsByName(String cityName);

    boolean existsByNameAndIdNot(String name, Long id);



}
