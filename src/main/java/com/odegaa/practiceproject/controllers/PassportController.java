package com.odegaa.practiceproject.controllers;

import com.odegaa.practiceproject.advice.ApiResponse;
import com.odegaa.practiceproject.entities.templates.Status;
import com.odegaa.practiceproject.models.passport.PassportDTO;
import com.odegaa.practiceproject.services.PassportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "1.2. Identity Management (Passports)",
        description = "Handling sensitive personal identification data (Passport series, Number, Identification number). Strictly restricted to ADMIN.")
@PreAuthorize("hasRole('ADMIN')")
@RestController
@RequestMapping("/api/v1/passport")
public class PassportController {

    private final PassportService passportService;

    public PassportController(PassportService passportService) {
        this.passportService = passportService;
    }

    @Operation(summary = "Get all passport records",
            description = "Accessible by: ADMIN. Returns a list of all identity documents. Can be filtered by status (ACTIVE/ARCHIVED).")
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<PassportDTO>>> getAllPassportDTO(Status status) {
        ApiResponse<List<PassportDTO>> allPassports = passportService.getAllPassports(status);
        return ResponseEntity.ok(allPassports);
    }

    @Operation(summary = "Get passport by ID", description = "Accessible by: ADMIN. Returns full identity details for a specific record.")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PassportDTO>> getPassportDTO(@PathVariable Long id) {
        ApiResponse<PassportDTO> passportById = passportService.getPassportById(id);
        return ResponseEntity.ok(passportById);
    }

    @Operation(summary = "Register new passport", description = "Accessible by: ADMIN. Registers a new identity document with JSHSHIR and nationality.")
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<PassportDTO>> createPassport(@RequestBody PassportDTO passportDTO) {
        ApiResponse<PassportDTO> passport = passportService.createPassport(passportDTO);
        return ResponseEntity.ok(passport);
    }

    @Operation(summary = "Update passport details", description = "Accessible by: ADMIN. Used to correct identity information or update document data.")
    @PatchMapping("/update/{id}")
    public ResponseEntity<ApiResponse<PassportDTO>> updatePassport(@PathVariable Long id,
                                                                   @Valid @RequestBody PassportDTO passportDTO) {
        ApiResponse<PassportDTO> passport = passportService.updatePassport(id, passportDTO);
        return ResponseEntity.ok(passport);
    }

    @Operation(summary = "Archive passport record", description = "Accessible by: ADMIN. Soft delete for identity records.")
    @PatchMapping("/archive/{id}")
    public ResponseEntity<ApiResponse<PassportDTO>> archivePassport(@PathVariable Long id) {
        ApiResponse<PassportDTO> passport = passportService.archivePassport(id);
        return ResponseEntity.ok(passport);
    }
}
