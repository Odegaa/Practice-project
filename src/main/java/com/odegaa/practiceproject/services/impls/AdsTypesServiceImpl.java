package com.odegaa.practiceproject.services.impls;

import com.odegaa.practiceproject.advice.ApiResponse;
import com.odegaa.practiceproject.entities.AdsTypes;
import com.odegaa.practiceproject.entities.templates.Status;
import com.odegaa.practiceproject.exceptions.AlreadyExistsException;
import com.odegaa.practiceproject.mappers.AdsTypeMappers;
import com.odegaa.practiceproject.models.advertising.AdsTypesDTO;
import com.odegaa.practiceproject.models.advertising.CreateAdsTypeDTO;
import com.odegaa.practiceproject.repositories.AdsTypesRepository;
import com.odegaa.practiceproject.services.AdsTypesService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class AdsTypesServiceImpl implements AdsTypesService {

    private final AdsTypesRepository adsTypesRepository;
    private final AdsTypeMappers adsTypeMappers;

    public AdsTypesServiceImpl(AdsTypesRepository adsTypesRepository, AdsTypeMappers adsTypeMappers) {
        this.adsTypesRepository = adsTypesRepository;
        this.adsTypeMappers = adsTypeMappers;
    }

    @Override
    public ApiResponse<List<AdsTypesDTO>> getAllAdsTypes() {
        List<AdsTypes> all = adsTypesRepository.findAll();

        List<AdsTypesDTO> dto = all.stream()
                .map(adsTypeMappers::toDto)
                .toList();

        return ApiResponse.<List<AdsTypesDTO>>builder()
                .message("Список всех типов реклам!")
                .success(true)
                .data(dto).build();
    }

    @Override
    public ApiResponse<AdsTypesDTO> createAdsType(CreateAdsTypeDTO adsType) {
        boolean existsByName = adsTypesRepository.existsByName(adsType.name().toLowerCase());
        if (existsByName) {
            throw new AlreadyExistsException("This ads type - " + adsType.name() + " is already exists!");
        }
        String lowerCase = adsType.name().toLowerCase();
        AdsTypes entity = adsTypeMappers.toEntityFromCreate(adsType);
        entity.setName(lowerCase);
        AdsTypes save = adsTypesRepository.save(entity);
        AdsTypesDTO dto = adsTypeMappers.toDto(save);

        return ApiResponse.<AdsTypesDTO>builder()
                .message("Успешно создано!")
                .success(true)
                .data(dto).build();
    }

    @Override
    public ApiResponse<AdsTypesDTO> updateAdsType(Long id, CreateAdsTypeDTO createAdsType) {
        AdsTypes adsTypes = adsTypesRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Not found AdsTypes with id: " + id));

        boolean existsByName = adsTypesRepository.existsByName(createAdsType.name().toLowerCase());

        if (existsByName) {
            throw new AlreadyExistsException("This ads type - " + createAdsType.name() + " is already exists!");
        }

        adsTypes.setName(createAdsType.name().toLowerCase());
        AdsTypes save = adsTypesRepository.save(adsTypes);
        AdsTypesDTO dto = adsTypeMappers.toDto(save);

        return ApiResponse.<AdsTypesDTO>builder()
                .message("Успешно изменен!")
                .success(true)
                .data(dto).build();
    }

    @Override
    public ApiResponse<AdsTypesDTO> archiveAdsType(Long id) {
        AdsTypes adsTypes = adsTypesRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Not found AdsTypes with id: " + id));

        if (adsTypes.getStatus().equals(Status.ARCHIVE)) {
            throw new AlreadyExistsException("This ads type - " + adsTypes.getName() + " is already archived!");
        }

        adsTypes.setStatus(Status.ARCHIVE);
        AdsTypes save = adsTypesRepository.save(adsTypes);
        AdsTypesDTO dto = adsTypeMappers.toDto(save);

        return ApiResponse.<AdsTypesDTO>builder()
                .message("Успешно архивирован!!")
                .success(true)
                .data(dto).build();
    }
}
