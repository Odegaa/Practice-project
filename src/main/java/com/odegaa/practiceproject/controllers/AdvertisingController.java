package com.odegaa.practiceproject.controllers;

import com.odegaa.practiceproject.advice.ApiResponse;
import com.odegaa.practiceproject.entities.templates.Status;
import com.odegaa.practiceproject.models.advertising.AdvertisingDTO;
import com.odegaa.practiceproject.models.advertising.CreateAdvertisingDTO;
import com.odegaa.practiceproject.services.AdvertisingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "5. Advertising Management",
        description = "Operations for tracking ad campaigns and expenses (Instagram, Google Ads, etc.). Used by Sales and Management.")
@RestController
@RequestMapping("/api/v1/advertising")
public class AdvertisingController {

    private final AdvertisingService advertisingService;

    public AdvertisingController(AdvertisingService advertisingService) {
        this.advertisingService = advertisingService;
    }

    @Operation(summary = "Get list of all ad campaigns",
            description = "Accessible by: ADMIN, SALES_MANAGER, DIRECTOR. Supports pagination and filtering by status (ACTIVE/DELETED).")
    @PreAuthorize("hasAnyRole('ADMIN', 'SALES_MANAGER', 'DIRECTOR')")
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<AdvertisingDTO>>> getAllAds(@Parameter(hidden = true) Pageable pageable,
                                                                       @Parameter(description = "Filter by status: ACTIVE, ARCHIVED or e.t.c.") Status status) {
        ApiResponse<List<AdvertisingDTO>> allAds = advertisingService.getAllAds(pageable, status);
        return ResponseEntity.ok(allAds);
    }

    @Operation(summary = "Create new ad campaign record",
            description = "Accessible by: ADMIN, SALES_MANAGER. Employee must provide ad type, expense amount, and duration.")
    @PreAuthorize("hasAnyRole('ADMIN', 'SALES_MANAGER')")
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<AdvertisingDTO>> createAds(@RequestBody CreateAdvertisingDTO advertisingDTO) {
        ApiResponse<AdvertisingDTO> ads = advertisingService.createAds(advertisingDTO);
        return ResponseEntity.ok(ads);
    }

    @Operation(summary = "Update ad campaign duration or details",
            description = "Accessible by: ADMIN, SALES_MANAGER. Used to adjust running ads (e.g., extending duration).")
    @PreAuthorize("hasAnyRole('ADMIN', 'SALES_MANAGER')")
    @PatchMapping("/update/{id}")
    public ResponseEntity<ApiResponse<AdvertisingDTO>> changeAdsDuration(@PathVariable Long id,
                                                                         @RequestBody CreateAdvertisingDTO advertisingDTO) {
        ApiResponse<AdvertisingDTO> response = advertisingService.changeAdsDuration(id, advertisingDTO);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Archive an ad campaign",
            description = "Accessible by: ADMIN, SALES_MANAGER. Marks the ad campaign as finished/archived.")
    @PreAuthorize("hasAnyRole('ADMIN', 'SALES_MANAGER')")
    @PatchMapping("/archive/{id}")
    public ResponseEntity<ApiResponse<AdvertisingDTO>> archiveAds(@PathVariable Long id) {
        ApiResponse<AdvertisingDTO> response = advertisingService.archiveAds(id);
        return ResponseEntity.ok(response);
    }
}
