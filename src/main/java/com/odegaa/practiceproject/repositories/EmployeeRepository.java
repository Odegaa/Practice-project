package com.odegaa.practiceproject.repositories;

import com.odegaa.practiceproject.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Employee findByUsername(String username);

    boolean existsByUsername(String username);

    @Query("select e.department, count(e) from employee e group by e.department")
    List<Object[]> countEmployeeByDepartment();

    @Query("select count(e) from employee e")
    Long getTotalEmployeeCount();

    @Query("select sum(salary) as total_salary_expense from employee")
    Long getTotalSalary();


}
