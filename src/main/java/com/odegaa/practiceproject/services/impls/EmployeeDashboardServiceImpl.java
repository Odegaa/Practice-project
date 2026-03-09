package com.odegaa.practiceproject.services.impls;

import com.odegaa.practiceproject.advice.ApiResponse;
import com.odegaa.practiceproject.advice.SecurityHelper;
import com.odegaa.practiceproject.entities.templates.Department;
import com.odegaa.practiceproject.models.employeeDashboard.EmployeeDashboardDTO;
import com.odegaa.practiceproject.repositories.EmployeeRepository;
import com.odegaa.practiceproject.services.EmployeeDashboardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Transactional(readOnly = true)
public class EmployeeDashboardServiceImpl implements EmployeeDashboardService {

    private final EmployeeRepository employeeRepository;
    private final SecurityHelper securityHelper;

    @Override
    public ApiResponse<List<EmployeeDashboardDTO>> getListCountEmployeeOfDepartment(Pageable pageable) {
        log.info("{} - пользователь использовал метод getListCountEmployeeOfDepartment(), {} - в такое время",
                securityHelper.getACCOUNT_EMPLOYEE().getUsername(), LocalDateTime.now());

        List<Object[]> dbResults = employeeRepository.countEmployeeByDepartment();
        Long totalEmployees = employeeRepository.getTotalEmployeeCount();

        Map<String, Long> statsMap = dbResults.stream().collect(Collectors.toMap(
                row -> row[0].toString(),
                row -> (Long) row[1]
        ));

        List<EmployeeDashboardDTO> finalStats = new ArrayList<>();
        for (Department department : Department.values()) {
            String departmentName = department.name();
            Long count = statsMap.getOrDefault(departmentName, 0L);

            Double doublePercentage = (totalEmployees > 0)
                    ? Math.round(((double) (count * 100) / totalEmployees) * 100.0) / 100.0
                    : 0.0;

            finalStats.add(new EmployeeDashboardDTO(departmentName, count, doublePercentage));
        }

        return ApiResponse.<List<EmployeeDashboardDTO>>builder()
                .message("Успешно!")
                .success(true)
                .data(finalStats).build();
    }

    @Override
    public ApiResponse<Long> getListEmployeeSalaryByDepartment() {
        log.info("{} - пользователь использовал метод getListEmployeeSalaryByDepartment(), {} - в такое время",
                securityHelper.getACCOUNT_EMPLOYEE().getUsername(), LocalDateTime.now());

        Long totalSalary = employeeRepository.getTotalSalary();

        return ApiResponse.<Long>builder()
                .message("Общяя сумма всех зарплат!")
                .success(true)
                .data(totalSalary).build();
    }
}
