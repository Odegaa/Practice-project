package com.odegaa.practiceproject.services.impls;

import com.odegaa.practiceproject.advice.ApiResponse;
import com.odegaa.practiceproject.advice.SecurityHelper;
import com.odegaa.practiceproject.models.customersDashboard.CustomersCountDTO;
import com.odegaa.practiceproject.models.customersDashboard.CustomersDailyStatsDashboardDTO;
import com.odegaa.practiceproject.models.customersDashboard.CustomersMostRegisteredEmployeeDTO;
import com.odegaa.practiceproject.repositories.CustomerRepository;
import com.odegaa.practiceproject.services.CustomersDashboardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class CustomersDashboardServiceImpl implements CustomersDashboardService {

    private final CustomerRepository customerRepository;
    private final SecurityHelper securityHelper;

    public CustomersDashboardServiceImpl(CustomerRepository customerRepository, SecurityHelper securityHelper) {
        this.customerRepository = customerRepository;
        this.securityHelper = securityHelper;
    }

    @Override
    public ApiResponse<CustomersDailyStatsDashboardDTO> getDailyStats(LocalDate date) {
        log.info("{} - пользователь использовал метод getDailyStats(), {} - в такое время",
                securityHelper.getACCOUNT_EMPLOYEE().getUsername(), LocalDateTime.now());

        Long countCustomersByDate = customerRepository.countCustomersByDate(date);

        CustomersDailyStatsDashboardDTO response = new CustomersDailyStatsDashboardDTO(date, countCustomersByDate);

        return ApiResponse.<CustomersDailyStatsDashboardDTO>builder()
                .message("Количество регистрировавших пользователь по заданному дату!")
                .success(true)
                .data(response).build();
    }

    @Override
    public ApiResponse<CustomersMostRegisteredEmployeeDTO> getMostEmployeeRegisteredCustomers() {
        log.info("{} - пользователь использовал метод getMostEmployeeRegisteredCustomers(), {} - в такое время",
                securityHelper.getACCOUNT_EMPLOYEE().getUsername(), LocalDateTime.now());

        List<Object[]> result = customerRepository.getMostCustomersRegisteredEmployee();

        if (result.isEmpty()) {
            return ApiResponse.<CustomersMostRegisteredEmployeeDTO>builder()
                    .message("Результат пустой!")
                    .success(false)
                    .build();
        }

        Object[] row = result.get(0);

        CustomersMostRegisteredEmployeeDTO response = new CustomersMostRegisteredEmployeeDTO(
                (String) row[0],
                (Long) row[1]
        );

        return ApiResponse.<CustomersMostRegisteredEmployeeDTO>builder()
                .message("Самый топовый сотрудник")
                .success(true)
                .data(response).build();
    }

    @Override
    public ApiResponse<List<CustomersMostRegisteredEmployeeDTO>> getTopThreeEmployeesMostRegisteredCustomers() {
        log.info("{} - пользователь использовал метод getTopThreeEmployeesMostRegisteredCustomers(), {} - в такое время",
                securityHelper.getACCOUNT_EMPLOYEE().getUsername(), LocalDateTime.now());

        List<Object[]> results = customerRepository.getMostCustomersRegisteredEmployee();

        List<CustomersMostRegisteredEmployeeDTO> response = results.stream()
                .limit(3)
                .map(row -> new CustomersMostRegisteredEmployeeDTO(
                        (String) row[0],
                        (Long) row[1]
                )).toList();

        if (response.isEmpty()) {
            return ApiResponse.<List<CustomersMostRegisteredEmployeeDTO>>builder()
                    .message("Пустой")
                    .success(false)
                    .build();
        }

        return ApiResponse.<List<CustomersMostRegisteredEmployeeDTO>>builder()
                .message("Список топовых сотрудников!")
                .success(true)
                .data(response).build();
    }

    @Override
    public ApiResponse<CustomersCountDTO> getCustomersCountLastMonth() {
        log.info("{} - пользователь использовал метод getCustomerCountLastMonth(), {} - в такое время",
                securityHelper.getACCOUNT_EMPLOYEE().getUsername(), LocalDateTime.now());

        LocalDateTime monthAgo = LocalDateTime.now().minusMonths(1);

        Long count = customerRepository.countCustomersSince(monthAgo);

        CustomersCountDTO dto = new CustomersCountDTO(count, "Last 1 month");

        return ApiResponse.<CustomersCountDTO>builder()
                .message("Успешно!")
                .success(true)
                .data(dto).build();
    }

    @Override
    public ApiResponse<CustomersDailyStatsDashboardDTO> getPeakRegistrationDate() {
        log.info("{} - пользователь использовал метод getPeakRegistrationDate(), {} - в такое время",
                securityHelper.getACCOUNT_EMPLOYEE().getUsername(), LocalDateTime.now());

        LocalDateTime monthAge = LocalDateTime.now().minusMonths(1);

        List<Object[]> results = customerRepository.findPeakRegistrationDay(monthAge);

        if (results.isEmpty()) {
            return ApiResponse.<CustomersDailyStatsDashboardDTO>builder()
                    .message("Данных за последний месяц нет!")
                    .success(false)
                    .build();
        }

        Object[] row = results.get(0);

        CustomersDailyStatsDashboardDTO dto = new CustomersDailyStatsDashboardDTO(
                ((java.sql.Date) row[0]).toLocalDate(),
                (Long) row[1]
        );

        return ApiResponse.<CustomersDailyStatsDashboardDTO>builder()
                .message("Пиковый день за последний месяц")
                .success(true)
                .data(dto).build();
    }
}
