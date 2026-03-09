package com.odegaa.practiceproject.services.impls;

import com.odegaa.practiceproject.advice.ApiResponse;
import com.odegaa.practiceproject.advice.SecurityHelper;
import com.odegaa.practiceproject.models.advertisingDashboard.AdsDashboardCountDTO;
import com.odegaa.practiceproject.models.advertisingDashboard.AdsDashboardDTO;
import com.odegaa.practiceproject.models.advertisingDashboard.LastMonthAdsDashboardDTO;
import com.odegaa.practiceproject.repositories.AdvertisingRepository;
import com.odegaa.practiceproject.services.AdsDashboardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class AdsDashboardServiceImpl implements AdsDashboardService {

    private final AdvertisingRepository repository;
    private final SecurityHelper securityHelper;

    public AdsDashboardServiceImpl(AdvertisingRepository repository, SecurityHelper securityHelper) {
        this.repository = repository;
        this.securityHelper = securityHelper;
    }

    @Override
    public ApiResponse<AdsDashboardDTO> getMostExpensiveAdsTypes() {
        log.info("{} - пользователь использовал метод getMostExpensiveAdsTypes(), {} - в такое время",
                securityHelper.getACCOUNT_EMPLOYEE().getUsername(), LocalDateTime.now());

        List<Object[]> results = repository.getMostExpensiveAdsTypes();

        if (results.isEmpty()) {
            return ApiResponse.<AdsDashboardDTO>builder()
                    .message("Данных по расходам на рекламу пока нет")
                    .success(false)
                    .build();
        }

        Object[] result = results.get(0);

        AdsDashboardDTO dto = new AdsDashboardDTO(
                (String) result[0],
                (Double) result[1]
        );

        return ApiResponse.<AdsDashboardDTO>builder()
                .success(true)
                .message("Самый затратный вид рекламы определен")
                .data(dto)
                .build();
    }

    @Override
    public ApiResponse<AdsDashboardDTO> getTopSpenderEmployee() {
        log.info("{} - пользователь использовал метод getTopSpenderEmployee(), {} - в такое время",
                securityHelper.getACCOUNT_EMPLOYEE().getUsername(), LocalDateTime.now());

        List<Object[]> results = repository.getTopSpenderEmployee();

        if (results.isEmpty()) {
            return ApiResponse.<AdsDashboardDTO>builder()
                    .message("Данных по расходам на рекламу пока нет")
                    .success(false)
                    .build();
        }

        Object[] result = results.get(0);

        AdsDashboardDTO dto = new AdsDashboardDTO(
                (String) result[0],
                (Double) result[1]
        );

        return ApiResponse.<AdsDashboardDTO>builder()
                .message("Самый расточительный сотрудник!")
                .success(true)
                .data(dto).build();
    }

    @Override
    public ApiResponse<LastMonthAdsDashboardDTO> getLastMonthAds() {
        log.info("{} - пользователь использовал метод getLastMonthAds(), {} - в такое время",
                securityHelper.getACCOUNT_EMPLOYEE().getUsername(), LocalDateTime.now());

        LocalDate now = LocalDate.now().minusDays(1);
        Long count = repository.countByStartDateAfter(now);

        LastMonthAdsDashboardDTO dto = new LastMonthAdsDashboardDTO(now, count);

        return ApiResponse.<LastMonthAdsDashboardDTO>builder()
                .message("Общее количество реклам за последний месяц!")
                .success(true)
                .data(dto).build();
    }

    @Override
    public ApiResponse<List<AdsDashboardCountDTO>> getCountAds() {
        log.info("{} - пользователь использовал метод getCountAds(), {} - в такое время",
                securityHelper.getACCOUNT_EMPLOYEE().getUsername(), LocalDateTime.now());

        List<Object[]> results = repository.getCountAdsFromType();

        List<AdsDashboardCountDTO> dto = results.stream()
                .map(row -> new AdsDashboardCountDTO(
                        (String) row[0],
                        (Long) row[1]
                ))
                .toList();

        if (dto.isEmpty()) {
            return ApiResponse.<List<AdsDashboardCountDTO>>builder()
                    .message("Список пусть, реклам пока нету!")
                    .success(false)
                    .build();
        }

        return ApiResponse.<List<AdsDashboardCountDTO>>builder()
                .message("Список реклам!")
                .success(true)
                .data(dto).build();
    }

    @Override
    public ApiResponse<LastMonthAdsDashboardDTO> getFinishedAdsCount() {
        log.info("{} - пользователь использовал метод getFinishedAdsCount(), {} - в такое время",
                securityHelper.getACCOUNT_EMPLOYEE().getUsername(), LocalDateTime.now());

        LocalDate monthAgo = LocalDate.now().minusMonths(1);

        Long count = repository.countFinishedAtLastMonth(monthAgo);

        LastMonthAdsDashboardDTO dto = new LastMonthAdsDashboardDTO(monthAgo, count);

        return ApiResponse.<LastMonthAdsDashboardDTO>builder()
                .message("Количество закончившихся реклам за последний месяц!")
                .success(true)
                .data(dto).build();
    }
}
