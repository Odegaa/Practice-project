package com.odegaa.practiceproject.controllers;

import com.odegaa.practiceproject.advice.ApiResponse;
import com.odegaa.practiceproject.models.advertising.AdsTypesDTO;
import com.odegaa.practiceproject.models.advertising.CreateAdsTypeDTO;
import com.odegaa.practiceproject.services.AdsTypesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "4. Marketing Settings (Ads Types)",
        description = "Dictionary management for advertising channels. Restricted to ADMIN only.")
@PreAuthorize("hasRole('ADMIN')")
@RestController
@RequestMapping("/api/v1/advertising/ads-types")
public class AdsTypesController {

    private final AdsTypesService adsTypesService;

    public AdsTypesController(AdsTypesService adsTypesService) {
        this.adsTypesService = adsTypesService;
    }

    @Operation(summary = "Get all available ads types",
            description = "Accessible by: ADMIN. Returns a list of all advertising platforms used by the company.")
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<AdsTypesDTO>>> getAllAdsTypes() {
        ApiResponse<List<AdsTypesDTO>> response = adsTypesService.getAllAdsTypes();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Create a new ads type",
            description = "Accessible by: ADMIN. Adds a new channel (e.g., 'TikTok Ads') to the system.")
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<AdsTypesDTO>> createAdsType(@Valid @RequestBody CreateAdsTypeDTO dto) {
        ApiResponse<AdsTypesDTO> response = adsTypesService.createAdsType(dto);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Update ads type name/details",
            description = "Accessible by: ADMIN. Updates information for a specific advertising channel.")
    @PatchMapping("/update/{id}")
    public ResponseEntity<ApiResponse<AdsTypesDTO>> updateAdsType(@PathVariable Long id,
                                                                  @Valid @RequestBody CreateAdsTypeDTO dto) {
        ApiResponse<AdsTypesDTO> response = adsTypesService.updateAdsType(id, dto);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Archive ads type",
            description = "Accessible by: ADMIN. Disables an advertising channel without deleting its history (Soft delete).")
    @PatchMapping("/archive/{id}")
    public ResponseEntity<ApiResponse<AdsTypesDTO>> archiveAdsType(@PathVariable Long id) {
        ApiResponse<AdsTypesDTO> response = adsTypesService.archiveAdsType(id);
        return ResponseEntity.ok(response);
    }

}
