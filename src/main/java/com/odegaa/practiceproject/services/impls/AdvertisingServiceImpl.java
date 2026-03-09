package com.odegaa.practiceproject.services.impls;

import com.odegaa.practiceproject.advice.ApiResponse;
import com.odegaa.practiceproject.advice.SecurityHelper;
import com.odegaa.practiceproject.entities.Advertising;
import com.odegaa.practiceproject.entities.Employee;
import com.odegaa.practiceproject.entities.templates.Status;
import com.odegaa.practiceproject.mappers.AdvertisingMappers;
import com.odegaa.practiceproject.models.advertising.AdvertisingDTO;
import com.odegaa.practiceproject.models.advertising.CreateAdvertisingDTO;
import com.odegaa.practiceproject.repositories.AdvertisingRepository;
import com.odegaa.practiceproject.services.AdvertisingService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Transactional(readOnly = true)
public class AdvertisingServiceImpl implements AdvertisingService {

    private final AdvertisingRepository advertisingRepository;
    private final AdvertisingMappers advertisingMappers;
    private final SecurityHelper securityHelper;

    @Transactional
    @Override
    public ApiResponse<AdvertisingDTO> createAds(CreateAdvertisingDTO advertisingDTO) {
        log.info("{} - пользователь использовал метод createAds(), {} - в такое время",
                securityHelper.getACCOUNT_EMPLOYEE().getUsername(), LocalDateTime.now());

        Employee accountEmployee = securityHelper.getACCOUNT_EMPLOYEE();

        if (accountEmployee == null) {
            throw new EntityNotFoundException("Employee not found");
        }

        Advertising advertising = advertisingMappers.toAdvertising(advertisingDTO);
        advertising.setEmployee(accountEmployee);
        Advertising advertisingEntity = advertisingRepository.save(advertising);
        AdvertisingDTO dto = advertisingMappers.toAdvertisingDTO(advertisingEntity);

        return ApiResponse.<AdvertisingDTO>builder()
                .message("Успешно сохраен")
                .success(true)
                .data(dto).build();
    }

    @Override
    public ApiResponse<List<AdvertisingDTO>> getAllAds(Pageable pageable, Status status) {
        log.info("{} - пользователь использовал метод getAllAds(), {} - в такое время",
                securityHelper.getACCOUNT_EMPLOYEE().getUsername(), LocalDateTime.now());

        Page<Advertising> adsPage = (status != null)
                ? advertisingRepository.findAllByStatus(status, pageable)
                : advertisingRepository.findAll(pageable);

        List<AdvertisingDTO> dtoList = adsPage.getContent().stream()
                .map(advertisingMappers::toAdvertisingDTO)
                .toList();

        String advertisingInfo = String.format("Страница %d из %d. Всего элементов: %d",
                adsPage.getNumber() + 1,
                adsPage.getTotalPages(),
                adsPage.getTotalElements());

        return ApiResponse.<List<AdvertisingDTO>>builder()
                .message(advertisingInfo)
                .success(true)
                .data(dtoList).build();
    }

    @Transactional
    @Override
    public ApiResponse<AdvertisingDTO> changeAdsDuration(Long id, CreateAdvertisingDTO advertisingDTO) {
        log.info("{} - пользователь использовал метод changeAdsDuration(), {} - в такое время",
                securityHelper.getACCOUNT_EMPLOYEE().getUsername(), LocalDateTime.now());

        Advertising advertising = advertisingRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        Employee accountEmployee = securityHelper.getACCOUNT_EMPLOYEE();
        if(!advertising.getEmployee().getId().equals(accountEmployee.getId())) {
            throw new EntityNotFoundException("Вы не можете менять чужую рекламу!");
        }

        advertising.setDuration(advertisingDTO.duration());
        Advertising save = advertisingRepository.save(advertising);
        AdvertisingDTO dto = advertisingMappers.toAdvertisingDTO(save);
        return ApiResponse.<AdvertisingDTO>builder()
                .message("Успешно изменен!")
                .success(true)
                .data(dto).build();
    }

    @Transactional
    @Override
    public ApiResponse<AdvertisingDTO> archiveAds(Long id) {
        log.info("{} - пользователь использовал метод archiveAds(), {} - в такое время",
                securityHelper.getACCOUNT_EMPLOYEE().getUsername(), LocalDateTime.now());

        Advertising advertising = advertisingRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        advertising.setStatus(Status.ARCHIVE);
        Advertising save = advertisingRepository.save(advertising);
        AdvertisingDTO dto = advertisingMappers.toAdvertisingDTO(save);

        return ApiResponse.<AdvertisingDTO>builder()
                .message("Реклама архивирован!")
                .success(true)
                .data(dto).build();
    }
}
