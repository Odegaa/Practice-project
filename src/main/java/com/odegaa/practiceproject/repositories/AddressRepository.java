package com.odegaa.practiceproject.repositories;

import com.odegaa.practiceproject.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    boolean existsByStreetNameAndNumberOfHouse(String streetName, int numberOfHouse);

}
