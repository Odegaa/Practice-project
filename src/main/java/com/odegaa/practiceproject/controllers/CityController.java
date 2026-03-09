package com.odegaa.practiceproject.controllers;

import com.odegaa.practiceproject.advice.ApiResponse;
import com.odegaa.practiceproject.entities.templates.Status;
import com.odegaa.practiceproject.models.address.CityDTO;
import com.odegaa.practiceproject.services.CityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "1.1. Location Settings (Cities)",
        description = "Regional configuration. Only ADMIN can manage cities/regions used for addresses.")
@PreAuthorize("hasRole('ADMIN')")
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/api/v1")
public class CityController {

    private final CityService cityService;

    @Operation(summary = "Get list of cities",
            description = "Accessible by: ADMIN. Returns all cities. Can be filtered by status (ACTIVE/DELETED).")
    @GetMapping("/all-city")
    public ResponseEntity<ApiResponse<List<CityDTO>>> getAllCity(@PathVariable(required = false) Status status) {
        ApiResponse<List<CityDTO>> allCities = cityService.getAllCities(status);
        return ResponseEntity.ok(allCities);
    }

    @Operation(summary = "Get city by ID", description = "Accessible by: ADMIN. Returns specific city details.")
    @GetMapping("/city/{id}")
    public ResponseEntity<ApiResponse<CityDTO>> getCityById(@PathVariable Long id) {
        ApiResponse<CityDTO> cityById = cityService.getCityById(id);
        return ResponseEntity.ok(cityById);
    }

    @Operation(summary = "Add a new city", description = "Accessible by: ADMIN. Adds a new region to the database.")
    @PostMapping("/city/create")
    public ResponseEntity<ApiResponse<CityDTO>> addCity(@RequestBody @Valid CityDTO cityDTO) {
        ApiResponse<CityDTO> cityDTOApiResponse = cityService.addCity(cityDTO);
        return ResponseEntity.ok(cityDTOApiResponse);
    }

    @Operation(summary = "Update city name", description = "Accessible by: ADMIN. Modifies existing city data.")
    @PatchMapping("/city/update/{id}")
    public ResponseEntity<ApiResponse<CityDTO>> updateCity(@PathVariable Long id,
                                                           @RequestBody @Valid CityDTO cityDTO) {
        ApiResponse<CityDTO> cityDTOApiResponse = cityService.updateCity(id, cityDTO);
        return ResponseEntity.ok(cityDTOApiResponse);
    }

    @Operation(summary = "Archive city", description = "Accessible by: ADMIN. Soft delete for a city.")
    @PatchMapping("/city/archive/{id}")
    public ResponseEntity<ApiResponse<CityDTO>> archiveCity(@PathVariable Long id) {
        ApiResponse<CityDTO> city = cityService.archiveCity(id);
        return ResponseEntity.ok(city);
    }

}
