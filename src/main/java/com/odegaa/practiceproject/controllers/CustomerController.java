package com.odegaa.practiceproject.controllers;

import com.odegaa.practiceproject.advice.ApiResponse;
import com.odegaa.practiceproject.entities.templates.Status;
import com.odegaa.practiceproject.models.customer.CustomerDTO;
import com.odegaa.practiceproject.services.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "2. CRM (Customer Relationship Management)",
        description = "Operations for customer onboarding and management. Features employee-specific data isolation.")
@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Operation(summary = "Get all customers (Global list)",
            description = "Accessible by: ADMIN, DIRECTOR. Returns all customers in the system with pagination and status filter.")
    @PreAuthorize("hasAnyRole('ADMIN', 'DIRECTOR')")
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<CustomerDTO>>> getAllCustomers(@Parameter(description = "Filter by Status (ACTIVE/DELETED)") Status status,
                                                                          @Parameter(hidden = true) Pageable pageable) {
        ApiResponse<List<CustomerDTO>> response = customerService.getAllCustomers(status, pageable);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get detailed customer info by ID", description = "Accessible by: ADMIN, DIRECTOR, CUSTOMER_MANAGER.")
    @PreAuthorize("hasAnyRole('ADMIN', 'DIRECTOR', 'CUSTOMER_MANAGER')")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CustomerDTO>> getCustomerById(@PathVariable("id") Long id) {
        ApiResponse<CustomerDTO> response = customerService.getCustomerById(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get my registered customers",
            description = "Accessible by: CUSTOMER_MANAGER. Returns only the customers registered by the currently authenticated user.")
    @PreAuthorize("hasRole('CUSTOMER_MANAGER')")
    @GetMapping("/own")
    public ResponseEntity<ApiResponse<List<CustomerDTO>>> getOwnCustomers(@Parameter(hidden = true) Pageable pageable) {
        ApiResponse<List<CustomerDTO>> response = customerService.getOwnCustomers(pageable);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Register a new customer", description = "Accessible by: ADMIN, CUSTOMER_MANAGER. Captures Passport, Identification number, and Address.")
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER_MANAGER')")
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<CustomerDTO>> createCustomer(@RequestBody CustomerDTO customerDTO) {
        ApiResponse<CustomerDTO> response = customerService.createCustomer(customerDTO);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Update customer data", description = "Accessible by: ADMIN, CUSTOMER_MANAGER.")
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER_MANAGER')")
    @PatchMapping("/update/{id}")
    public ResponseEntity<ApiResponse<CustomerDTO>> updateCustomer(@RequestBody CustomerDTO customerDTO, @PathVariable("id") Long id) {
        ApiResponse<CustomerDTO> response = customerService.updateCustomer(id, customerDTO);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Archive customer", description = "Accessible by: ADMIN, CUSTOMER_MANAGER. Moves customer to archive/inactive status.")
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER_MANAGER')")
    @PatchMapping("/archive/{id}")
    public ResponseEntity<ApiResponse<CustomerDTO>> archiveCustomer(@PathVariable Long id) {
        ApiResponse<CustomerDTO> response = customerService.archiveCustomer(id);
        return ResponseEntity.ok(response);
    }

}
