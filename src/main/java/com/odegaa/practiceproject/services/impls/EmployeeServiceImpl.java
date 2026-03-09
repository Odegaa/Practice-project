package com.odegaa.practiceproject.services.impls;

import com.odegaa.practiceproject.advice.ApiResponse;
import com.odegaa.practiceproject.advice.SecurityHelper;
import com.odegaa.practiceproject.entities.Address;
import com.odegaa.practiceproject.entities.Employee;
import com.odegaa.practiceproject.entities.Passport;
import com.odegaa.practiceproject.entities.Region;
import com.odegaa.practiceproject.entities.templates.Roles;
import com.odegaa.practiceproject.entities.templates.Status;
import com.odegaa.practiceproject.exceptions.AlreadyExistsException;
import com.odegaa.practiceproject.mappers.EmployeeMappers;
import com.odegaa.practiceproject.mappers.PassportMappers;
import com.odegaa.practiceproject.models.address.AddressDTO;
import com.odegaa.practiceproject.models.employee.CreateEmployeeDTO;
import com.odegaa.practiceproject.models.employee.EmployeeDTO;
import com.odegaa.practiceproject.models.employee.UpdateEmployeeDTO;
import com.odegaa.practiceproject.models.passport.PassportDTO;
import com.odegaa.practiceproject.repositories.AddressRepository;
import com.odegaa.practiceproject.repositories.EmployeeRepository;
import com.odegaa.practiceproject.repositories.PassportRepository;
import com.odegaa.practiceproject.repositories.RegionRepository;
import com.odegaa.practiceproject.services.EmployeeService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMappers employeeMappers;
    private final PasswordEncoder passwordEncoder;
    private final AddressRepository addressRepository;
    private final RegionRepository regionRepository;
    private final SecurityHelper securityHelper;
    private final PassportRepository passportRepository;
    private final PassportMappers passportMappers;


    @Transactional
    @Override
    public ApiResponse<EmployeeDTO> createEmployee(CreateEmployeeDTO createEmployeeDTO) {
        log.info("{} - пользователь использовал метод createEmployee(), {} - в такое время",
                securityHelper.getACCOUNT_EMPLOYEE().getUsername(), LocalDateTime.now());

        if (employeeRepository.existsByUsername(createEmployeeDTO.getUsername())) {
            throw new AlreadyExistsException("Такой пользователь уже зарегестрирован!");
        }

        Employee employee = employeeMappers.toEntity(createEmployeeDTO);
        employee.setPassword(passwordEncoder.encode(createEmployeeDTO.getPassword()));
        Employee save = employeeRepository.save(employee);

        EmployeeDTO employeeDTO = employeeMappers.toEmployeeDTO(save);

        return ApiResponse.<EmployeeDTO>builder()
                .message("Пользователь успешно добавлен!")
                .success(true)
                .data(employeeDTO)
                .build();
    }

    @Override
    public ApiResponse<List<EmployeeDTO>> getAllEmployees() {
        log.info("{} - пользователь использовал метод getAllEmployees(), {} - в такое время",
                securityHelper.getACCOUNT_EMPLOYEE().getUsername(), LocalDateTime.now());

        List<Employee> employeeList = employeeRepository.findAll();
        List<EmployeeDTO> employeeDTOList = employeeMappers.toEmployeeDTOList(employeeList);

        return ApiResponse.<List<EmployeeDTO>>builder()
                .message("Список пользователей")
                .success(true)
                .data(employeeDTOList)
                .build();
    }

    @Override
    public ApiResponse<EmployeeDTO> getEmployeeById(Long id) {
        log.info("{} - пользователь использовал метод getEmployeeById(), {} - в такое время",
                securityHelper.getACCOUNT_EMPLOYEE().getUsername(), LocalDateTime.now());

        if (securityHelper.getACCOUNT_EMPLOYEE().getRoles().contains(Roles.EMPLOYEE)) {
            if (!securityHelper.getACCOUNT_EMPLOYEE().getId().equals(id)) {
                return ApiResponse.<EmployeeDTO>builder()
                        .message("У вас нет доступа для просмотра другого пользователя!")
                        .success(false)
                        .build();
            }
        }

        Employee employee = employeeRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Польхователь с таким ID не найдено!"));
        EmployeeDTO employeeDTO = employeeMappers.toEmployeeDTO(employee);

        return ApiResponse.<EmployeeDTO>builder()
                .message("Данные о пользователя!")
                .success(true)
                .data(employeeDTO)
                .build();
    }

    @Transactional
    @Override
    public ApiResponse<EmployeeDTO> dismissEmployee(Long id) {
        log.info("{} - пользователь использовал метод dismissEmployee(), {} - в такое время",
                securityHelper.getACCOUNT_EMPLOYEE().getUsername(), LocalDateTime.now());

        Employee employee = employeeRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Пльзователь не найдет с таким ID!"));

        if (employee.getStatus() == Status.ARCHIVE) {
            log.warn("Пользователь с таким ID: {{}} уже архивирован!", id);
            return ApiResponse.<EmployeeDTO>builder()
                    .message("Пользователь с таким ID: {" + id + "} уже архивирован!")
                    .success(false)
                    .build();
        }

        employee.setStatus(Status.ARCHIVE);
        employeeRepository.save(employee);

        EmployeeDTO employeeDTO = employeeMappers.toEmployeeDTO(employee);

        return ApiResponse.<EmployeeDTO>builder()
                .data(employeeDTO)
                .message("Сотрудник успешно уволен (архивирован)!")
                .success(true)
                .build();
    }

    @Transactional
    @Override
    public ApiResponse<EmployeeDTO> updateEmployee(Long id, UpdateEmployeeDTO employeeDTO) {
        log.info("{} - пользователь использовал метод updateEmployee(), {} - в такое время",
                securityHelper.getACCOUNT_EMPLOYEE().getUsername(), LocalDateTime.now());

        Employee employee = employeeRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Пользователь с таким ID не найдено"));

        if (employeeDTO.getPassport() != null) {
            PassportDTO passportDTO = employeeDTO.getPassport();
            Passport passport = passportMappers.toPassport(passportDTO);

            employee.setPassport(passport);
            Passport save = passportRepository.save(passport);

            PassportDTO dto = passportMappers.toPassportDTO(save);
            employeeDTO.setPassport(dto);
        }

        if (employeeDTO.getAddress() != null) {
            AddressDTO addressDTO = employeeDTO.getAddress();
            Address address = employee.getAddress();

            if (addressDTO.getCityId() != null && addressDTO.getRegionId() != null) {
                if (!regionRepository.existsByIdAndCity_id(addressDTO.getRegionId(), addressDTO.getCityId())) {
                    throw new AlreadyExistsException(
                            "Этот регион не принадлежит указанному городу! - {" + addressDTO.getRegionId() + "}");
                }
            }

            if (address == null) {
                address = new Address();
            }

            address.setStreetName(addressDTO.getStreetName());
            address.setNumberOfHouse(addressDTO.getNumberOfHouse());

            if (addressDTO.getRegionId() != null) {
                Region region = regionRepository.findById(addressDTO.getRegionId()).orElseThrow(() ->
                        new EntityNotFoundException("Регион не найден!"));
                address.setRegion(region);
            }

            addressRepository.save(address);
            employee.setAddress(address);
        }

        Employee updatedEmployee = employeeMappers.updateEntityFromDTO(employeeDTO, employee);

        Employee save = employeeRepository.save(updatedEmployee);
        EmployeeDTO updatedDTO = employeeMappers.toEmployeeDTO(save);

        return ApiResponse.<EmployeeDTO>builder()
                .data(updatedDTO)
                .success(true)
                .message("Пользователь успешно изменён")
                .build();
    }
}
