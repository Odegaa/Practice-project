package com.odegaa.practiceproject.services;

import com.odegaa.practiceproject.advice.ApiResponse;
import com.odegaa.practiceproject.models.advertising.AdsTypesDTO;
import com.odegaa.practiceproject.models.advertising.CreateAdsTypeDTO;

import java.util.List;

public interface AdsTypesService {
    ApiResponse<List<AdsTypesDTO>> getAllAdsTypes();

    ApiResponse<AdsTypesDTO> createAdsType(CreateAdsTypeDTO adsType);

    ApiResponse<AdsTypesDTO> updateAdsType(Long id, CreateAdsTypeDTO adsType);

    ApiResponse<AdsTypesDTO> archiveAdsType(Long id);
}
