package com.odegaa.practiceproject.services;

import com.odegaa.practiceproject.advice.ApiResponse;
import com.odegaa.practiceproject.entities.templates.Status;
import com.odegaa.practiceproject.models.address.CreateRegionDTO;
import com.odegaa.practiceproject.models.address.RegionDTO;
import com.odegaa.practiceproject.models.address.UpdateRegionDTO;

import java.util.List;

public interface RegionService {

    ApiResponse<RegionDTO> createRegion(CreateRegionDTO createRegionDTO);

    ApiResponse<List<RegionDTO>> getAllRegions(Status status);

    ApiResponse<RegionDTO> getRegionById(Long id);

    ApiResponse<RegionDTO> updateRegion(Long id, UpdateRegionDTO updateRegionDTO);

    ApiResponse<RegionDTO> archiveRegion(Long id);

}
