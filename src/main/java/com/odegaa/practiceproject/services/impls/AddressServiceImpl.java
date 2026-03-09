package com.odegaa.practiceproject.services.impls;

import com.odegaa.practiceproject.advice.ApiResponse;
import com.odegaa.practiceproject.advice.SecurityHelper;
import com.odegaa.practiceproject.entities.Address;
import com.odegaa.practiceproject.entities.City;
import com.odegaa.practiceproject.entities.Region;
import com.odegaa.practiceproject.entities.templates.Status;
import com.odegaa.practiceproject.exceptions.AlreadyExistsException;
import com.odegaa.practiceproject.mappers.AddressMappers;
import com.odegaa.practiceproject.models.address.AddressDTO;
import com.odegaa.practiceproject.repositories.AddressRepository;
import com.odegaa.practiceproject.repositories.CityRepository;
import com.odegaa.practiceproject.repositories.RegionRepository;
import com.odegaa.practiceproject.services.AddressService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final AddressMappers addressMappers;
    private final RegionRepository regionRepository;
    private final CityRepository cityRepository;
    private final SecurityHelper securityHelper;

    @Override
    public ApiResponse<List<AddressDTO>> getAllAddresses(Status status, Pageable pageable) {
        log.info("{} - пользователь использовал метод getAllAddresses(), {} - в такое время",
                securityHelper.getACCOUNT_EMPLOYEE().getUsername(), LocalDateTime.now());

        List<Address> addressList = addressRepository.findAll();

        List<AddressDTO> list = addressList.stream()
                .filter(address -> status == null || address.getStatus().equals(status))
                .map(addressMappers::toAddressDTO)
                .toList();

        return ApiResponse.<List<AddressDTO>>builder()
                .message("Список адресов")
                .success(true)
                .data(list).build();

    }

    @Override
    public ApiResponse<AddressDTO> getAddressById(Long id) {
        log.info("{} - пользователь использовал метод getAddressById(), {} - в такое время",
                securityHelper.getACCOUNT_EMPLOYEE().getUsername(), LocalDateTime.now());

        Address address = addressRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("This address does not exist"));

        AddressDTO dto = addressMappers.toAddressDTO(address);

        return ApiResponse.<AddressDTO>builder()
                .message("Адрес")
                .success(true)
                .data(dto).build();
    }

    @Override
    public ApiResponse<AddressDTO> createAddress(AddressDTO addressDTO) {
        log.info("{} - пользователь использовал метод createAddress(), {} - в такое время",
                securityHelper.getACCOUNT_EMPLOYEE().getUsername(), LocalDateTime.now());

        boolean byStreetNameAndNumberOfHouse = addressRepository
                .existsByStreetNameAndNumberOfHouse(addressDTO.getStreetName(), addressDTO.getNumberOfHouse());

        if (byStreetNameAndNumberOfHouse) {
            throw new AlreadyExistsException("This address already exists");
        }

        Region region = regionRepository.findById(addressDTO.getRegionId()).orElseThrow(() ->
                new EntityNotFoundException("This region does not exist"));

        City city = cityRepository.findById(addressDTO.getCityId()).orElseThrow(() ->
                new EntityNotFoundException("This city does not exist"));

        Address address = addressMappers.toAddress(addressDTO);
        region.setCity(city);
        address.setRegion(region);
        Address savedAddress = addressRepository.save(address);
        AddressDTO dto = addressMappers.toAddressDTO(savedAddress);

        return ApiResponse.<AddressDTO>builder()
                .message("Адрес успешно сохранен!")
                .success(true)
                .data(dto).build();
    }

    @Override
    public ApiResponse<AddressDTO> updateAddress(Long id, AddressDTO addressDTO) {
        log.info("{} - пользователь использовал метод updateAddress(), {} - в такое время",
                securityHelper.getACCOUNT_EMPLOYEE().getUsername(), LocalDateTime.now());

        Address address = addressRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("This address does not exist"));

        Region region = regionRepository.findById(addressDTO.getRegionId()).orElseThrow(() ->
                new EntityNotFoundException("This region does not exist"));

        City city = cityRepository.findById(addressDTO.getCityId()).orElseThrow(() ->
                new EntityNotFoundException("This city does not exist"));

        if (!region.getCity().getId().equals(city.getId())) {
            throw new IllegalArgumentException("This region does not exist");
        }

        address.setRegion(region);
        Address newAddress = addressMappers.toEntityAddressFromDTO(addressDTO, address);
        Address savedAddress = addressRepository.save(newAddress);
        AddressDTO dto = addressMappers.toAddressDTO(savedAddress);

        return ApiResponse.<AddressDTO>builder()
                .message("Успешно")
                .success(true)
                .data(dto).build();
    }

    @Override
    public ApiResponse<AddressDTO> archiveAddress(Long id) {
        log.info("{} - пользователь использовал метод archiveAddress(), {} - в такое время",
                securityHelper.getACCOUNT_EMPLOYEE().getUsername(), LocalDateTime.now());

        Address address = addressRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("This address does not exist"));

        if (address.getStatus().equals(Status.ARCHIVE)) {
            throw new AlreadyExistsException("This address already archived");
        }

        address.setStatus(Status.ARCHIVE);
        Address save = addressRepository.save(address);

        AddressDTO dto = addressMappers.toAddressDTO(save);

        return ApiResponse.<AddressDTO>builder()
                .message("Архивирован!")
                .success(true)
                .data(dto).build();
    }
}
