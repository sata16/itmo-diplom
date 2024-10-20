package com.example.diplom.service;

import com.example.diplom.exceptions.CustomException;
import com.example.diplom.model.db.entity.Address;
import com.example.diplom.model.db.entity.Services;
import com.example.diplom.model.db.repository.ServiceRepository;
import com.example.diplom.model.dto.request.AddressToServiceRequest;
import com.example.diplom.model.dto.request.ServiceInfoRequest;
import com.example.diplom.model.dto.response.ServiceInfoResponse;
import com.example.diplom.model.enums.ServiceStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ServiceService {
    private final AddressService addressService;
    private final ObjectMapper mapper;
    private final ServiceRepository serviceRepository;

    public ServiceInfoResponse createService(ServiceInfoRequest request) {
        Services services = mapper.convertValue(request,Services.class);

        services.setCreatedAt(LocalDateTime.now());
        services.setStatus(ServiceStatus.OPENED);

        return  mapper.convertValue(serviceRepository.save(services),ServiceInfoResponse.class);
    }

    //метод получения услуги
    public Services getServiceFromDB(Long id){
        return serviceRepository.findById(id).orElseThrow(()->new CustomException("Услуга не найдена", HttpStatus.NOT_FOUND));
    }
    //Проверка статуса услуги
    private void controlStatus(Long id){
        serviceRepository.findById(id).filter(services -> !services.getStatus().equals(ServiceStatus.CLOSED)).orElseThrow(()->new CustomException("Service has status Closed", HttpStatus.BAD_REQUEST));
    }

    public ServiceInfoResponse getService(Long id) {
        return  mapper.convertValue(getServiceFromDB(id),ServiceInfoResponse.class);
    }

    public ServiceInfoResponse updateService(Long id, ServiceInfoRequest request) {
        Services services = getServiceFromDB(id);

        services.setNameService(request.getNameService() == null ? services.getNameService(): request.getNameService());
        services.setProvider(request.getProvider() == null ? services.getProvider(): request.getProvider());
        services.setUpdatedAt(LocalDateTime.now());
        services.setStatus(ServiceStatus.UPDATED);

        return  mapper.convertValue(serviceRepository.save(services), ServiceInfoResponse.class);
    }

    public void deleteService(Long id) {
        Services services = getServiceFromDB(id);
        services.setUpdatedAt(LocalDateTime.now());
        services.setStatus(ServiceStatus.CLOSED);

        serviceRepository.save(services);

    }

    public List<ServiceInfoResponse> getAllService() {
        return  serviceRepository.findAll().stream()
                .map(services -> mapper.convertValue(services, ServiceInfoResponse.class))
                .collect(Collectors.toList());
    }

    public void addServiceToAddress(AddressToServiceRequest request) {
        controlStatus(request.getServiceId());
        addressService.controlStatus(request.getAddressId());
        Services serviceFromDB = getServiceFromDB(request.getServiceId());
        Address addressFromDB = addressService.getAddressFromDB(request.getAddressId());

        serviceFromDB.getAddresses().add(addressFromDB);
        serviceRepository.save(serviceFromDB);

    }

    //все услуги по адресу
    public List<ServiceInfoResponse> getAllServiceToAddress(Long id) {
            return serviceRepository.findServicesByAddresses(id,ServiceStatus.CLOSED).stream()
                    .map(services -> mapper.convertValue(services, ServiceInfoResponse.class))
                    .collect(Collectors.toList());

    }
}
