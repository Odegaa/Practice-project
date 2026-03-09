package com.odegaa.practiceproject.repositories;

import com.odegaa.practiceproject.entities.AdsTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdsTypesRepository extends JpaRepository<AdsTypes, Long> {
    boolean existsByName(String name);
}
