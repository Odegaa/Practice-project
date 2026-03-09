package com.odegaa.practiceproject.controllers;

import com.odegaa.practiceproject.advice.ApiResponse;
import com.odegaa.practiceproject.entities.templates.Status;
import com.odegaa.practiceproject.models.address.CreateRegionDTO;
import com.odegaa.practiceproject.models.address.RegionDTO;
import com.odegaa.practiceproject.models.address.UpdateRegionDTO;
import com.odegaa.practiceproject.services.RegionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "1.0. Location Settings (Regions)",
        description = "Top-level regional configuration (e.g., Tashkent Region, San-Francisco). Foundational data for addresses.")
@PreAuthorize("hasRole('ADMIN')")
@RestController
@RequestMapping("/api/v1/regions")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RegionController {

    private final RegionService regionService;

    @Operation(summary = "Get all regions",
            description = "Accessible by: ADMIN. Returns the list of all administrative regions. Supports status filtering and pagination.")
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<RegionDTO>>> getAllRegions(Status status, Pageable pageable) {
        ApiResponse<List<RegionDTO>> regions = regionService.getAllRegions(status);
        return ResponseEntity.ok(regions);
    }

    @Operation(summary = "Get region by ID", description = "Accessible by: ADMIN. Returns details of a specific region.")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<RegionDTO>> getRegionById(@PathVariable Long id) {
        ApiResponse<RegionDTO> region = regionService.getRegionById(id);
        return ResponseEntity.ok(region);
    }

    @Operation(summary = "Create a new region", description = "Accessible by: ADMIN. Registers a new top-level administrative area.")
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<RegionDTO>> createRegion(@RequestBody CreateRegionDTO regionDTO) {
        ApiResponse<RegionDTO> response = regionService.createRegion(regionDTO);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Update region details", description = "Accessible by: ADMIN. Modifies name or attributes of an existing region.")
    @PatchMapping("/update/{id}")
    public ResponseEntity<ApiResponse<RegionDTO>> updateRegion(@PathVariable Long id,
                                                               @Valid @RequestBody UpdateRegionDTO regionDTO) {
        ApiResponse<RegionDTO> response = regionService.updateRegion(id, regionDTO);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Archive region", description = "Accessible by: ADMIN. Soft delete for regions that are no longer in use.")
    @PatchMapping("/archive/{id}")
    public ResponseEntity<ApiResponse<RegionDTO>> archiveRegion(@PathVariable Long id) {
        ApiResponse<RegionDTO> response = regionService.archiveRegion(id);
        return ResponseEntity.ok(response);
    }

}
