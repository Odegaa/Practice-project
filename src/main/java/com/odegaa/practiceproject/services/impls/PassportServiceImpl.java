package com.odegaa.practiceproject.services.impls;

import com.odegaa.practiceproject.advice.ApiResponse;
import com.odegaa.practiceproject.advice.SecurityHelper;
import com.odegaa.practiceproject.entities.Passport;
import com.odegaa.practiceproject.entities.templates.Status;
import com.odegaa.practiceproject.exceptions.AlreadyExistsException;
import com.odegaa.practiceproject.mappers.PassportMappers;
import com.odegaa.practiceproject.models.passport.PassportDTO;
import com.odegaa.practiceproject.repositories.PassportRepository;
import com.odegaa.practiceproject.services.PassportService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
public class PassportServiceImpl implements PassportService {

    private final PassportRepository passportRepository;
    private final PassportMappers passportMappers;
    private final SecurityHelper securityHelper;

    public PassportServiceImpl(PassportRepository passportRepository,
                               PassportMappers passportMappers,
                               SecurityHelper securityHelper) {
        this.passportRepository = passportRepository;
        this.passportMappers = passportMappers;
        this.securityHelper = securityHelper;
    }

    @Override
    public ApiResponse<List<PassportDTO>> getAllPassports(Status status) {
        log.info("{} - пользователь использовал метод getAllPassports(), {} - в такое время",
                securityHelper.getACCOUNT_EMPLOYEE().getUsername(), LocalDateTime.now());

        List<Passport> all = passportRepository.findAll();

        List<PassportDTO> dtoList = all.stream()
                .filter(passport -> status == null || passport.getStatus().equals(status))
                .map(passportMappers::toPassportDTO)
                .toList();

        return ApiResponse.<List<PassportDTO>>builder()
                .message("Список паспортов")
                .success(true)
                .data(dtoList).build();
    }

    @Override
    public ApiResponse<PassportDTO> getPassportById(Long id) {
        log.info("{} - пользователь использовал метод getPassportById(), {} - в такое время",
                securityHelper.getACCOUNT_EMPLOYEE().getUsername(), LocalDateTime.now());

        Passport passport = passportRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Passport not found"));

        PassportDTO dto = passportMappers.toPassportDTO(passport);

        return ApiResponse.<PassportDTO>builder()
                .message("Успешно!")
                .success(true)
                .data(dto).build();
    }

    @Transactional
    @Override
    public ApiResponse<PassportDTO> createPassport(PassportDTO passportDTO) {
        log.info("{} - пользователь использовал метод createPassport(), {} - в такое время",
                securityHelper.getACCOUNT_EMPLOYEE().getUsername(), LocalDateTime.now());

        boolean byIdentificationNumber = passportRepository
                .existsByIdentificationNumber(passportDTO.getIdentificationNumber());

        if (byIdentificationNumber) {
            throw new AlreadyExistsException("This identification number already exists");
        }

        Passport passport = passportMappers.toPassport(passportDTO);
        Passport savedPassport = passportRepository.save(passport);
        PassportDTO dto = passportMappers.toPassportDTO(savedPassport);

        return ApiResponse.<PassportDTO>builder()
                .message("Успешно создан!")
                .success(true)
                .data(dto).build();
    }

    @Transactional
    @Override
    public ApiResponse<PassportDTO> updatePassport(Long id, PassportDTO passportDTO) {
        log.info("{} - пользователь использовал метод updatePassport(), {} - в такое время",
                securityHelper.getACCOUNT_EMPLOYEE().getUsername(), LocalDateTime.now());

        Passport passport = passportRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Passport with this id not found"));

        Passport passwordUpdate = passportMappers.toPasswordUpdate(passportDTO, passport);
        Passport save = passportRepository.save(passwordUpdate);
        PassportDTO dto = passportMappers.toPassportDTO(save);

        return ApiResponse.<PassportDTO>builder()
                .message("Успешно изменен!")
                .success(true)
                .data(dto).build();

    }

    @Transactional
    @Override
    public ApiResponse<PassportDTO> archivePassport(Long id) {
        log.info("{} - пользователь использовал метод archivePassport(), {} - в такое время",
                securityHelper.getACCOUNT_EMPLOYEE().getUsername(), LocalDateTime.now());

        Passport passport = passportRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Passport with this id not found"));

        passport.setStatus(Status.ARCHIVE);
        passportRepository.save(passport);
        PassportDTO dto = passportMappers.toPassportDTO(passport);

        return ApiResponse.<PassportDTO>builder()
                .message("Успешно архивирован!")
                .success(true)
                .data(dto).build();
    }
}
