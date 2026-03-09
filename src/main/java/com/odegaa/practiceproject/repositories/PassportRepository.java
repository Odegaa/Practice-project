package com.odegaa.practiceproject.repositories;

import com.odegaa.practiceproject.entities.Passport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PassportRepository extends JpaRepository<Passport, Long> {
    boolean existsByIdentificationNumber(String identificationNumber);

    Optional<Passport> findByIdentificationNumber(String identificationNumber);
}
