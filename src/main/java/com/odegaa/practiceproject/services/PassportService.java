package com.odegaa.practiceproject.services;

import com.odegaa.practiceproject.advice.ApiResponse;
import com.odegaa.practiceproject.entities.templates.Status;
import com.odegaa.practiceproject.models.passport.PassportDTO;

import java.util.List;

public interface PassportService {

    ApiResponse<List<PassportDTO>> getAllPassports(Status status);

    ApiResponse<PassportDTO> getPassportById(Long id);

    ApiResponse<PassportDTO> createPassport(PassportDTO passportDTO);

    ApiResponse<PassportDTO> updatePassport(Long id, PassportDTO passportDTO);

    ApiResponse<PassportDTO> archivePassport(Long id);

}
