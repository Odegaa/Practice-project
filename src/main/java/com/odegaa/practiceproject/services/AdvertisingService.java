package com.odegaa.practiceproject.services;

import com.odegaa.practiceproject.advice.ApiResponse;
import com.odegaa.practiceproject.entities.templates.Status;
import com.odegaa.practiceproject.models.advertising.AdvertisingDTO;
import com.odegaa.practiceproject.models.advertising.CreateAdvertisingDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AdvertisingService {

    ApiResponse<AdvertisingDTO> createAds(CreateAdvertisingDTO createAdvertisingDTO);

    ApiResponse<List<AdvertisingDTO>> getAllAds(Pageable pageable, Status status);

    ApiResponse<AdvertisingDTO> changeAdsDuration(Long id, CreateAdvertisingDTO createAdvertisingDTO);

    ApiResponse<AdvertisingDTO> archiveAds(Long id);

}
