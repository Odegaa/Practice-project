package com.odegaa.practiceproject.services.impls;

import com.odegaa.practiceproject.advice.ApiResponse;
import com.odegaa.practiceproject.advice.SecurityHelper;
import com.odegaa.practiceproject.entities.*;
import com.odegaa.practiceproject.entities.templates.Status;
import com.odegaa.practiceproject.exceptions.AlreadyExistsException;
import com.odegaa.practiceproject.mappers.CustomerMappers;
import com.odegaa.practiceproject.models.customer.CustomerDTO;
import com.odegaa.practiceproject.repositories.CustomerRepository;
import com.odegaa.practiceproject.repositories.PassportRepository;
import com.odegaa.practiceproject.repositories.RegionRepository;
import com.odegaa.practiceproject.services.CustomerService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMappers customerMappers;
    private final SecurityHelper securityHelper;
    private final RegionRepository regionRepository;
    private final PassportRepository passportRepository;

    @Override
    public ApiResponse<List<CustomerDTO>> getAllCustomers(Status status, Pageable pageable) {
        log.info("{} - пользователь использовал метод getAllCustomers(), {} - в такое время",
                securityHelper.getACCOUNT_EMPLOYEE().getUsername(), LocalDateTime.now());

        Page<Customer> customersPage = (status != null)
                ? customerRepository.findAllByStatus(status, pageable)
                : customerRepository.findAll(pageable);

        return getCustomersList(customersPage);
    }

    @Override
    public ApiResponse<List<CustomerDTO>> getOwnCustomers(Pageable pageable) {
        log.info("{} - пользователь использовал метод getOwnCustomers(), {} - в такое время",
                securityHelper.getACCOUNT_EMPLOYEE().getUsername(), LocalDateTime.now());

        Employee accountEmployee = securityHelper.getACCOUNT_EMPLOYEE();

        if (accountEmployee == null) {
            throw new EntityNotFoundException("Account not found");
        }

        Page<Customer> customerPage = (accountEmployee.getId() != null)
                ? customerRepository.findAllByEmployeeId(accountEmployee.getId(), pageable)
                : customerRepository.findAll(pageable);

        return getCustomersList(customerPage);
    }

    private ApiResponse<List<CustomerDTO>> getCustomersList(Page<Customer> customerPage) {
        log.info("{} - пользователь использовал метод getCustomersList(), {} - в такое время",
                securityHelper.getACCOUNT_EMPLOYEE().getUsername(), LocalDateTime.now());

        List<CustomerDTO> list = customerPage.getContent().stream()
                .map(customerMappers::toEntityDTO)
                .toList();

        String customerPageInfo = String.format("Страница %d из %d. Всего элементов: %d",
                customerPage.getNumber() + 1,
                customerPage.getTotalPages(),
                customerPage.getTotalElements());

        return ApiResponse.<List<CustomerDTO>>builder()
                .message(customerPageInfo)
                .success(true)
                .data(list).build();
    }

    @Override
    public ApiResponse<CustomerDTO> getCustomerById(Long id) {
        log.info("{} - пользователь использовал метод getCustomerById(), {} - в такое время",
                securityHelper.getACCOUNT_EMPLOYEE().getUsername(), LocalDateTime.now());

        Customer customer = customerRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Customer with id " + id + " not found"));

        CustomerDTO dto = customerMappers.toEntityDTO(customer);

        return ApiResponse.<CustomerDTO>builder()
                .message("Успешно!")
                .success(true)
                .data(dto).build();
    }

    @Transactional
    @Override
    public ApiResponse<CustomerDTO> createCustomer(CustomerDTO customerDTO) {
        log.info("{} - пользователь использовал метод createCustomer(), {} - в такое время",
                securityHelper.getACCOUNT_EMPLOYEE().getUsername(), LocalDateTime.now());

        String identificationNumber = customerDTO.passportDTO().getIdentificationNumber();
        boolean existsByPassportId = customerRepository.existsByPassport_IdentificationNumber(identificationNumber);
        if (existsByPassportId) {
            throw new AlreadyExistsException("Customer with identificationNumber " + identificationNumber + " already exists");
        }

        Customer entity = customerMappers.toEntity(customerDTO);

        Region region = regionRepository.findById(customerDTO.addressDTO().getRegionId()).orElseThrow(() ->
                new EntityNotFoundException("Region with id " + customerDTO.addressDTO().getRegionId() + " not found"));

        if (entity.getAddress() == null) {
            Address newAddress = new Address();
            newAddress.setNumberOfHouse(customerDTO.addressDTO().getNumberOfHouse());
            newAddress.setStreetName(customerDTO.addressDTO().getStreetName());
            entity.setAddress(newAddress);
        }

        Optional<Passport> passport = passportRepository.findByIdentificationNumber(
                (customerDTO.passportDTO().getIdentificationNumber()));

        if (entity.getPassport() == null) {
            Passport newPassport = new Passport();
            newPassport.setSerial(customerDTO.passportDTO().getSerial());
            newPassport.setSerialNumber(customerDTO.passportDTO().getSerialNumber());
            newPassport.setIdentificationNumber(customerDTO.passportDTO().getIdentificationNumber());
            newPassport.setNation(customerDTO.passportDTO().getNation());
            newPassport.setBirthDate(customerDTO.passportDTO().getBirthDate());
            entity.setPassport(newPassport);
        }


        entity.getAddress().setRegion(region);
        passport.ifPresent(entity::setPassport);
        entity.setCreatedBy(securityHelper.getACCOUNT_EMPLOYEE());
        entity.setRegistrationDateTime(LocalDateTime.now());

        Customer save = customerRepository.save(entity);
        CustomerDTO dto = customerMappers.toEntityDTO(save);

        return ApiResponse.<CustomerDTO>builder()
                .message("Успешно создан")
                .success(true)
                .data(dto).build();
    }

    @Transactional
    @Override
    public ApiResponse<CustomerDTO> updateCustomer(Long id, CustomerDTO customerDTO) {
        log.info("{} - пользователь использовал метод updateCustomer(), {} - в такое время",
                securityHelper.getACCOUNT_EMPLOYEE().getUsername(), LocalDateTime.now());

        Customer customer = customerRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Customer with id " + id + " not found"));

        Customer updatedCustomer = customerMappers.toCustomerFromDTO(customerDTO, customer);
        CustomerDTO dto = customerMappers.toEntityDTO(updatedCustomer);

        return ApiResponse.<CustomerDTO>builder()
                .message("Успешно изменен!")
                .success(true)
                .data(dto).build();
    }

    @Transactional
    @Override
    public ApiResponse<CustomerDTO> archiveCustomer(Long id) {
        log.info("{} - пользователь использовал метод archiveCustomer(), {} - в такое время",
                securityHelper.getACCOUNT_EMPLOYEE().getUsername(), LocalDateTime.now());

        Customer customer = customerRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Customer with id " + id + " not found"));

        customer.setStatus(Status.ARCHIVE);
        customerRepository.save(customer);
        CustomerDTO dto = customerMappers.toEntityDTO(customer);

        return ApiResponse.<CustomerDTO>builder()
                .message("Архивирован!")
                .success(true)
                .data(dto).build();
    }
}
