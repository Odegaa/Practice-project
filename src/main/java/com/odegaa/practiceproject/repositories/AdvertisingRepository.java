package com.odegaa.practiceproject.repositories;

import com.odegaa.practiceproject.entities.Advertising;
import com.odegaa.practiceproject.entities.templates.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AdvertisingRepository extends JpaRepository<Advertising, Long> {
    Page<Advertising> findAllByStatus(Status status, Pageable pageable);

    @Query("select a.type.name, sum(a.expense) from advertising a " +
            "group by a.type.id, a.type.name order by sum(a.expense) desc ")
    List<Object[]> getMostExpensiveAdsTypes();

    @Query("select e.username, sum(a.expense) from advertising a join a.employee e " +
            "group by e.id, e.username order by sum(a.expense) desc")
    List<Object[]> getTopSpenderEmployee();

    Long countByStartDateAfter(LocalDate startDate);

    @Query("select a.type.name, count(a) from advertising a group by a.type.id, a.type.name")
    List<Object[]> getCountAdsFromType();

    @Query(value = "SELECT count(c) FROM advertising c WHERE (c.start_date + c.duration) >= :oneMonthAgo", nativeQuery = true)
    Long countFinishedAtLastMonth(@Param("oneMonthAgo") LocalDate oneMonthAgo);
}
