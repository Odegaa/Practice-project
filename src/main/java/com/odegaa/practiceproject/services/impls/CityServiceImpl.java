package com.odegaa.practiceproject.services.impls;

import com.odegaa.practiceproject.advice.ApiResponse;
import com.odegaa.practiceproject.advice.SecurityHelper;
import com.odegaa.practiceproject.entities.City;
import com.odegaa.practiceproject.entities.templates.Status;
import com.odegaa.practiceproject.exceptions.AlreadyExistsException;
import com.odegaa.practiceproject.mappers.CityMappers;
import com.odegaa.practiceproject.models.address.CityDTO;
import com.odegaa.practiceproject.repositories.CityRepository;
import com.odegaa.practiceproject.services.CityService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;
    private final CityMappers cityMappers;
    private final SecurityHelper securityHelper;

    @Override
    public ApiResponse<List<CityDTO>> getAllCities(Status status) {
        log.info("{} - пользователь использовал метод getAllCities(), {} - в такое время",
                securityHelper.getACCOUNT_EMPLOYEE().getUsername(), LocalDateTime.now());

        List<City> all = cityRepository.findAll();

        List<CityDTO> dtoList = all.stream()
                .filter(city -> status == null || city.getStatus().equals(status))
                .map(cityMappers::toCityDto)
                .toList();

        return ApiResponse.<List<CityDTO>>builder()
                .message("Список городов")
                .success(true)
                .data(dtoList).build();
    }

    @Override
    public ApiResponse<CityDTO> getCityById(Long id) {
        log.info("{} - пользователь использовал метод getCityById(), {} - в такое время",
                securityHelper.getACCOUNT_EMPLOYEE().getUsername(), LocalDateTime.now());

        City city = cityRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Город с таким ID не найдено!"));

        CityDTO cityDto = cityMappers.toCityDto(city);

        return ApiResponse.<CityDTO>builder()
                .message("Успешно!")
                .success(true)
                .data(cityDto).build();
    }

    @Transactional
    @Override
    public ApiResponse<CityDTO> addCity(CityDTO cityDTO) {
        log.info("{} - пользователь использовал метод addCity(), {} - в такое время",
                securityHelper.getACCOUNT_EMPLOYEE().getUsername(), LocalDateTime.now());

        if (cityRepository.existsByName(cityDTO.getName())) {
            throw new AlreadyExistsException("Город с таким названием уже есть!");
        }

        City city = cityMappers.createCity(cityDTO);
        City save = cityRepository.save(city);

        CityDTO savedDTO = cityMappers.toCityDto(save);

        return ApiResponse.<CityDTO>builder()
                .message("Успешно добавлено!")
                .success(true)
                .data(savedDTO).build();
    }

    @Transactional
    @Override
    public ApiResponse<CityDTO> updateCity(Long id, CityDTO cityDTO) {
        log.info("{} - пользователь использовал метод updateCity(), {} - в такое время",
                securityHelper.getACCOUNT_EMPLOYEE().getUsername(), LocalDateTime.now());

        City city = cityRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Город с таким ID [" + id + "] не найдено!"));

        if (cityRepository.existsByNameAndIdNot(cityDTO.getName(), id)) {
            throw new AlreadyExistsException("Такой город уже имеется!");
        }

        City updatedCity = cityMappers.updateEntityFromDto(cityDTO, city);

        City save = cityRepository.save(updatedCity);
        CityDTO savedDTO = cityMappers.toCityDto(save);

        return ApiResponse.<CityDTO>builder()
                .message("Успешно изменен!")
                .success(true)
                .data(savedDTO).build();
    }

    @Transactional
    @Override
    public ApiResponse<CityDTO> archiveCity(Long id) {
        log.info("{} - пользователь использовал метод archiveCity(), {} - в такое время",
                securityHelper.getACCOUNT_EMPLOYEE().getUsername(), LocalDateTime.now());

        City city = cityRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Не найдено!"));

        if (city.getStatus().equals(Status.ARCHIVE)) {
            throw new AlreadyExistsException("Этот город уже удалён!");
        }

        city.setStatus(Status.ARCHIVE);
        City save = cityRepository.save(city);
        CityDTO savedDTO = cityMappers.toCityDto(save);

        return ApiResponse.<CityDTO>builder()
                .message("Успешно удалён!")
                .success(true)
                .data(savedDTO).build();
    }
}
