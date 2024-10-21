package com.example.diplom.service;

import com.example.diplom.model.db.entity.*;
import com.example.diplom.model.db.repository.ServiceRepository;
import com.example.diplom.model.dto.request.AddressToServiceRequest;
import com.example.diplom.model.dto.request.ServiceInfoRequest;
import com.example.diplom.model.dto.response.ServiceInfoResponse;
import com.example.diplom.model.enums.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ServiceServiceTest {
    @InjectMocks
    private ServiceService serviceService;
    @Mock
    private AddressService addressService;
    @Mock
    private ServiceRepository serviceRepository;
    @Spy
    private ObjectMapper mapper;

    @Test
    public void createService() {
        ServiceInfoRequest request = new ServiceInfoRequest();

        Services services = new Services();
        services.setId(1L);

        when(serviceRepository.save(any(Services.class))).thenReturn(services);

        ServiceInfoResponse result = serviceService.createService(request);
        assertEquals(services.getId(), result.getId());
    }

    @Test
    public void getService() {
        Services services = new Services();
        services.setId(1L);
        when(serviceRepository.findById(services.getId())).thenReturn(Optional.of(services));
        ServiceInfoResponse result = serviceService.getService(1L);
        assertEquals(services.getId(), result.getId());
    }

    @Test
    public void updateService() {
        ServiceInfoRequest request = new ServiceInfoRequest();
        request.setNameService("Отопление");

        Services services = new Services();
        services.setId(1L);
        when(serviceRepository.findById(services.getId())).thenReturn(Optional.of(services));
        serviceService.updateService(1L, request);
        verify(serviceRepository,times(1)).save(any(Services.class));
        assertEquals(ServiceStatus.UPDATED, services.getStatus());
        assertEquals(request.getNameService(), services.getNameService());
    }

    @Test
    public void deleteService() {
        Services services = new Services();
        services.setId(1L);
        when(serviceRepository.findById(services.getId())).thenReturn(Optional.of(services));
        serviceService.deleteService(services.getId());
        verify(serviceRepository, times(1)).save(any(Services.class));
        assertEquals(ServiceStatus.CLOSED,services.getStatus());
    }

    @Test
    public void getAllService() {
        Services services1 = new Services();
        services1.setId(1L);

        Services services2 = new Services();
        services2.setId(1L);


        List<Services> services = List.of(services1, services2);
        when(serviceRepository.findAll()).thenReturn(services);
        List<ServiceInfoResponse> result = serviceService.getAllService();

        assertEquals(services.size(),result.size());
    }

    @Test
    public void addServiceToAddress() {
        Services services1 = new Services();
        services1.setId(1L);
        services1.setAddresses(new ArrayList<>());
        services1.setStatus(ServiceStatus.OPENED);
        when(serviceRepository.findById(services1.getId())).thenReturn(Optional.of(services1));
        Address address = new Address();
        address.setId(1L);
        when(addressService.getAddressFromDB(address.getId())).thenReturn(address);
        AddressToServiceRequest request = AddressToServiceRequest.builder()
                .serviceId(services1.getId())
                .addressId(address.getId())
                .build();
        serviceService.addServiceToAddress(request);
        verify(serviceRepository,times(1)).save(any(Services.class));
        assertEquals(services1.getAddresses().get(0).getId(), request.getAddressId());
    }

    @Test
    public void getAllServiceToAddress() {
        Address address = new Address();
        address.setId(1L);

        Services services1 = new Services();
        services1.setId(1L);

        Services services2 = new Services();
        services2.setId(1L);

        List<Services> services = List.of(services1, services2);
        when(serviceRepository.findServicesByAddresses(address.getId(), ServiceStatus.CLOSED)).thenReturn(services);
        List<ServiceInfoResponse> result = serviceService.getAllServiceToAddress(address.getId());


        assertEquals(services.size(), result.size());
    }
}