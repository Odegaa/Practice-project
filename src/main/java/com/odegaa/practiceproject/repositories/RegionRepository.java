package com.odegaa.practiceproject.repositories;

import com.odegaa.practiceproject.entities.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionRepository extends JpaRepository<Region, Long> {
    boolean existsByIdAndCity_id(Long regionId, Long cityId);

    boolean existsByNameAndCity_id(String name, Long cityId);

}
