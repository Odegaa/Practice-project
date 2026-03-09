package com.odegaa.practiceproject.controllers;

import com.odegaa.practiceproject.advice.ApiResponse;
import com.odegaa.practiceproject.entities.templates.Status;
import com.odegaa.practiceproject.models.address.AddressDTO;
import com.odegaa.practiceproject.services.AddressService;
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

@Tag(name = "1. Address Management",
        description = "Operations for managing company branch addresses and locations. Restricted to ADMIN only.")
@PreAuthorize("hasRole('ADMIN')")
@RestController
@RequestMapping("/api/v1/addresses")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AddressController {

    private final AddressService addressService;

    @Operation(summary = "Get all addresses with pagination",
            description = "Accessible by: ADMIN. Allows filtering by Status (ACTIVE/DELETED) and supports pagination (page, size, sort).")
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<AddressDTO>>> getAllAddresses(Status status, Pageable pageable) {
        ApiResponse<List<AddressDTO>> allAddresses = addressService.getAllAddresses(status, pageable);
        return ResponseEntity.ok(allAddresses);
    }

    @Operation(summary = "Get address by ID", description = "Accessible by: ADMIN. Returns detailed address info.")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AddressDTO>> getAddress(@PathVariable Long id) {
        ApiResponse<AddressDTO> addressById = addressService.getAddressById(id);
        return ResponseEntity.ok(addressById);
    }

    @Operation(summary = "Create new address", description = "Accessible by: ADMIN. Adds a new location to the system.")
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<AddressDTO>> createAddress(@Valid @RequestBody AddressDTO addressDTO) {
        ApiResponse<AddressDTO> addressById = addressService.createAddress(addressDTO);
        return ResponseEntity.ok(addressById);
    }

    @Operation(summary = "Update address", description = "Accessible by: ADMIN. Update specific fields of an existing address.")
    @PatchMapping("/update/{id}")
    public ResponseEntity<ApiResponse<AddressDTO>> updateAddress(@Valid @RequestBody AddressDTO addressDTO,
                                                                 @PathVariable Long id) {
        ApiResponse<AddressDTO> addressById = addressService.updateAddress(id, addressDTO);
        return ResponseEntity.ok(addressById);
    }

    @Operation(summary = "Archive address", description = "Accessible by: ADMIN. Moves the address to the archive (Soft delete).")
    @PatchMapping("/archive/{id}")
    public ResponseEntity<ApiResponse<AddressDTO>> archiveAddress(@PathVariable Long id) {
        ApiResponse<AddressDTO> addressById = addressService.archiveAddress(id);
        return ResponseEntity.ok(addressById);
    }

}
