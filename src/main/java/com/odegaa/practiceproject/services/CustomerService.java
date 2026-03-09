package com.odegaa.practiceproject.services;

import com.odegaa.practiceproject.advice.ApiResponse;
import com.odegaa.practiceproject.entities.templates.Status;
import com.odegaa.practiceproject.models.customer.CustomerDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomerService {

    ApiResponse<List<CustomerDTO>> getAllCustomers(Status status, Pageable pageable);

    ApiResponse<List<CustomerDTO>> getOwnCustomers(Pageable pageable);

    ApiResponse<CustomerDTO> getCustomerById(Long id);

    ApiResponse<CustomerDTO> createCustomer(CustomerDTO customerDTO);

    ApiResponse<CustomerDTO> updateCustomer(Long id, CustomerDTO customerDTO);

    ApiResponse<CustomerDTO> archiveCustomer(Long id);

}
