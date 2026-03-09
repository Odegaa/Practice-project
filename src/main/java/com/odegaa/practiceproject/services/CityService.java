package com.odegaa.practiceproject.services;

import com.odegaa.practiceproject.advice.ApiResponse;
import com.odegaa.practiceproject.entities.templates.Status;
import com.odegaa.practiceproject.models.address.CityDTO;

import java.util.List;

public interface CityService {

    ApiResponse<List<CityDTO>> getAllCities(Status status);

    ApiResponse<CityDTO> getCityById(Long id);

    ApiResponse<CityDTO> addCity(CityDTO cityDTO);

    ApiResponse<CityDTO> updateCity(Long id, CityDTO cityDTO);

    ApiResponse<CityDTO> archiveCity(Long id);

}
