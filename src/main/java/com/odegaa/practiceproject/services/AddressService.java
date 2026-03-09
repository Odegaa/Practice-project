package com.odegaa.practiceproject.services;

import com.odegaa.practiceproject.advice.ApiResponse;
import com.odegaa.practiceproject.entities.templates.Status;
import com.odegaa.practiceproject.models.address.AddressDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AddressService {

    ApiResponse<List<AddressDTO>> getAllAddresses(Status status, Pageable pageable);

    ApiResponse<AddressDTO> getAddressById(Long id);

    ApiResponse<AddressDTO> createAddress(AddressDTO addressDTO);

    ApiResponse<AddressDTO> updateAddress(Long id, AddressDTO addressDTO);

    ApiResponse<AddressDTO> archiveAddress(Long id);

}
