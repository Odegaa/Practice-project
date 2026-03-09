package com.odegaa.practiceproject.repositories;

import com.odegaa.practiceproject.entities.Customer;
import com.odegaa.practiceproject.entities.templates.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Page<Customer> findAllByStatus(Status status, Pageable pageable);

    @Query("select c from Customer c where c.createdBy.id = :employeeId")
    Page<Customer> findAllByEmployeeId(@Param("employeeId") Long employeeId, Pageable pageable);

    boolean existsByPassport_IdentificationNumber(String identificationNumber);

    @Query("select count(c) from Customer c where cast(c.registrationDateTime as date) = :date")
    Long countCustomersByDate(@Param("date") LocalDate date);

    @Query("select concat(e.firstName, ' ', e.name, ' ', e.lastName), count(c) from Customer c " +
            "join c.createdBy e group by e.id, e.firstName, e.name, e.lastName order by count(c) desc")
    List<Object[]> getMostCustomersRegisteredEmployee();

    @Query("select count(c) from Customer c where c.registrationDateTime >= :startDate")
    Long countCustomersSince(@Param("startDate") LocalDateTime startDate);

    @Query("select cast(c.registrationDateTime as date), count(c) from Customer c where c.registrationDateTime >= :startDate" +
            " group by cast(c.registrationDateTime as date) order by count(c) desc ")
    List<Object[]> findPeakRegistrationDay(@Param("startDate") LocalDateTime startDate);
}
