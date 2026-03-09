package com.odegaa.practiceproject.services.impls;

import com.odegaa.practiceproject.advice.ApiResponse;
import com.odegaa.practiceproject.advice.SecurityHelper;
import com.odegaa.practiceproject.entities.City;
import com.odegaa.practiceproject.entities.Region;
import com.odegaa.practiceproject.entities.templates.Status;
import com.odegaa.practiceproject.exceptions.AlreadyExistsException;
import com.odegaa.practiceproject.mappers.RegionMappers;
import com.odegaa.practiceproject.models.address.CreateRegionDTO;
import com.odegaa.practiceproject.models.address.RegionDTO;
import com.odegaa.practiceproject.models.address.UpdateRegionDTO;
import com.odegaa.practiceproject.repositories.CityRepository;
import com.odegaa.practiceproject.repositories.RegionRepository;
import com.odegaa.practiceproject.services.RegionService;
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
@Transactional(readOnly = true)
public class RegionServiceImpl implements RegionService {

    private final RegionRepository regionRepository;
    private final RegionMappers regionMappers;
    private final CityRepository cityRepository;
    private final SecurityHelper securityHelper;

    @Transactional
    @Override
    public ApiResponse<RegionDTO> createRegion(CreateRegionDTO createRegionDTO) {
        log.info("{} - пользователь использовал метод createRegion(), {} - в такое время",
                securityHelper.getACCOUNT_EMPLOYEE().getUsername(), LocalDateTime.now());

        boolean existRegion =
                regionRepository.existsByNameAndCity_id(createRegionDTO.getName(), createRegionDTO.getCityId());

        if (existRegion) {
            throw new AlreadyExistsException("Region with name " + createRegionDTO.getName() + " already exists");
        }

        City city = cityRepository.findById(createRegionDTO.getCityId()).orElseThrow(() ->
                new EntityNotFoundException("City with id " + createRegionDTO.getCityId() + " not found"));

        Region region = regionMappers.toCreateRegionEntity(createRegionDTO);
        region.setCity(city);

        Region save = regionRepository.save(region);
        RegionDTO regionDTO = regionMappers.toRegionDTO(save);

        return ApiResponse.<RegionDTO>builder()
                .message("Успешно добавлен!")
                .success(true)
                .data(regionDTO).build();
    }

    @Override
    public ApiResponse<List<RegionDTO>> getAllRegions(Status status) {
        log.info("{} - пользователь использовал метод getAllRegions(), {} - в такое время",
                securityHelper.getACCOUNT_EMPLOYEE().getUsername(), LocalDateTime.now());

        List<Region> regionList = regionRepository.findAll();

        List<RegionDTO> regionDTOList = regionList.stream()
                .filter(region -> status == null || region.getStatus().equals(status))
                .map(regionMappers::toRegionDTO)
                .toList();

        return ApiResponse.<List<RegionDTO>>builder()
                .message("Список регионов")
                .success(true)
                .data(regionDTOList).build();
    }

    @Override
    public ApiResponse<RegionDTO> getRegionById(Long id) {
        log.info("{} - пользователь использовал метод getRegionById(), {} - в такое время",
                securityHelper.getACCOUNT_EMPLOYEE().getUsername(), LocalDateTime.now());

        Region region = regionRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Region not found with id " + id));

        RegionDTO regionDTO = regionMappers.toRegionDTO(region);

        return ApiResponse.<RegionDTO>builder()
                .message("Успешно!")
                .success(true)
                .data(regionDTO).build();
    }

    @Transactional
    @Override
    public ApiResponse<RegionDTO> updateRegion(Long id, UpdateRegionDTO updateRegionDTO) {
        log.info("{} - пользователь использовал метод updateRegion(), {} - в такое время",
                securityHelper.getACCOUNT_EMPLOYEE().getUsername(), LocalDateTime.now());

        Region region = regionRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Region not found with id " + id));

        boolean regionNameExists =
                regionRepository.existsByNameAndCity_id(updateRegionDTO.getName(), updateRegionDTO.getCityId());

        if (regionNameExists) {
            throw new AlreadyExistsException("This region in this city already exists :" + updateRegionDTO.getName());
        }

        City city = cityRepository.findById(updateRegionDTO.getCityId()).orElseThrow(() ->
                new EntityNotFoundException("City not found with id " + updateRegionDTO.getCityId()));

        Region savedRegion = regionMappers.toRegionEntity(updateRegionDTO, region);
        savedRegion.setCity(city);
        Region save = regionRepository.save(savedRegion);

        RegionDTO savedRegionDTO = regionMappers.toRegionDTO(save);

        return ApiResponse.<RegionDTO>builder()
                .success(true)
                .message("Успешно изменен!")
                .data(savedRegionDTO).build();
    }

    @Transactional
    @Override
    public ApiResponse<RegionDTO> archiveRegion(Long id) {
        log.info("{} - пользователь использовал метод archiveRegion(), {} - в такое время",
                securityHelper.getACCOUNT_EMPLOYEE().getUsername(), LocalDateTime.now());

        Region region = regionRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Region not found with id " + id));

        if (region.getStatus().equals(Status.ARCHIVE)) {
            throw new AlreadyExistsException("This region already archived");
        }

        region.setStatus(Status.ARCHIVE);
        regionRepository.save(region);
        RegionDTO savedRegionDTO = regionMappers.toRegionDTO(region);

        return ApiResponse.<RegionDTO>builder()
                .message("Регион архивирован!")
                .success(true)
                .data(savedRegionDTO).build();
    }

}
