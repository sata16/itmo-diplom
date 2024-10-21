package com.example.diplom.service;

import com.example.diplom.exceptions.CustomException;
import com.example.diplom.model.db.entity.Address;
import com.example.diplom.model.db.repository.AddressRepository;
import com.example.diplom.model.dto.request.AddressInfoRequest;
import com.example.diplom.model.dto.response.AddressInfoResponse;
import com.example.diplom.model.enums.*;
import com.example.diplom.utils.PaginationUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AddressServiceTest {
    @InjectMocks
    private AddressService addressService;
    @Mock
    private AddressRepository addressRepository;
    @Spy
    private ObjectMapper mapper;

    @Test
    public void createAddress() {
        AddressInfoRequest request = new AddressInfoRequest();
        Address address = new Address();
        address.setId(1L);

        when(addressRepository.save(any(Address.class))).thenReturn(address);

        AddressInfoResponse result = addressService.createAddress(request);
        assertEquals(address.getId(), result.getId());
    }

    @Test
    public void controlStatus() {
        Address address = new Address();
        address.setId(1L);
        address.setStatus(AddressStatus.CREATED);
        when(addressRepository.findById(address.getId())).thenReturn(Optional.of(address));
        addressService.controlStatus(address.getId());
        assertEquals(AddressStatus.CREATED, address.getStatus());
    }
    @Test(expected = CustomException.class)
    public void controlStatus_badStatus() {
        Address address = new Address();
        address.setId(1L);
        address.setStatus(AddressStatus.DELETED);

        addressService.controlStatus(address.getId());
    }

    @Test
    public void getAddress() {
        Address address = new Address();
        address.setId(1L);
        when(addressRepository.findById(address.getId())).thenReturn(Optional.of(address));
        AddressInfoResponse result = addressService.getAddress(1L);
        assertEquals(address.getId(), result.getId());
    }

    @Test
    public void updateAddress() {
        AddressInfoRequest request = new AddressInfoRequest();
        request.setFlat("12");
        request.setStreet("Фонтанка");

        Address address = new Address();
        address.setId(1L);
        when(addressRepository.findById(address.getId())).thenReturn(Optional.of(address));
        addressService.updateAddress(1L, request);
        verify(addressRepository,times(1)).save(any(Address.class));
        assertEquals(AddressStatus.UPDATED, address.getStatus());
        assertEquals(request.getStreet(),address.getStreet());
        assertEquals(request.getFlat(),address.getFlat());
    }

    @Test
    public void deleteAddress() {
        Address address = new Address();
        address.setId(1L);
        when(addressRepository.findById(address.getId())).thenReturn(Optional.of(address));
        addressService.deleteAddress(address.getId());
        verify(addressRepository,times(1)).save(any(Address.class));
        assertEquals(AddressStatus.DELETED,address.getStatus());
    }

    @Test
    public void getAllAddressAndCounter() {
        Address address = new Address();
        address.setId(1L);
        address.setStreet("Фонтанка");
        address.setKorpus("2");

        Address address1 = new Address();
        address1.setId(2L);
        address1.setStreet("Фонтанка");
        address1.setKorpus("3");

        List<Address> addresses = List.of(address,address1);
        Pageable pageRequest = PaginationUtil.getPageRequests(1, 3, "street", Sort.Direction.ASC);

        PageImpl<Address> page = new PageImpl<>(addresses, pageRequest,addresses.size());
        when(addressRepository.findByAddressStatusAndAttributeCounter(pageRequest,AddressStatus.DELETED, CounterStatus.CLOSED,"COM")).thenReturn(page);

        Page<AddressInfoResponse> result = addressService.getAllAddressAndCounter(1,3,"street", Sort.Direction.ASC,"COM");
        assertEquals(addresses.size(),result.getTotalElements());
    }

    @Test
    public void getAllAddressAndCounter_notFilter() {
        Address address = new Address();
        address.setId(1L);
        address.setStreet("Фонтанка");
        address.setKorpus("2");

        Address address1 = new Address();
        address1.setId(2L);
        address1.setStreet("Фонтанка");
        address1.setKorpus("3");

        List<Address> addresses = List.of(address,address1);
        Pageable pageRequest = PaginationUtil.getPageRequests(1, 3, "street", Sort.Direction.ASC);

        PageImpl<Address> page = new PageImpl<>(addresses, pageRequest,addresses.size());
        when(addressRepository.findByAddressStatusAndCounters(pageRequest,AddressStatus.DELETED, CounterStatus.CLOSED)).thenReturn(page);

        Page<AddressInfoResponse> result = addressService.getAllAddressAndCounter(1,3,"street", Sort.Direction.ASC,null);
        assertEquals(addresses.size(),result.getTotalElements());
    }

    @Test
    public void getAllAddressDist() {
        Address address = new Address();
        address.setId(1L);
        address.setStreet("Фонтанка");
        address.setKorpus("2");
        address.setCodeDistrict(5);

        Address address1 = new Address();
        address1.setId(2L);
        address1.setStreet("Фонтанка");
        address1.setKorpus("3");
        address1.setCodeDistrict(5);

        List<Address> addresses = List.of(address,address1);
        Pageable pageRequest = PaginationUtil.getPageRequests(1, 3, "street", Sort.Direction.ASC);

        PageImpl<Address> page = new PageImpl<>(addresses, pageRequest,addresses.size());
        when(addressRepository.findByAddressStatusAndNameDistrict(pageRequest,AddressStatus.DELETED, "5")).thenReturn(page);

        Page<AddressInfoResponse> result = addressService.getAllAddressDist(1,3,"street", Sort.Direction.ASC,"5");
        assertEquals(addresses.size(),result.getTotalElements());
    }

    @Test
    public void getAllAddressDist_notFilter() {
        Address address = new Address();
        address.setId(1L);
        address.setStreet("Фонтанка");
        address.setKorpus("2");
        address.setCodeDistrict(5);

        Address address1 = new Address();
        address1.setId(2L);
        address1.setStreet("Фонтанка");
        address1.setKorpus("3");
        address1.setCodeDistrict(5);

        List<Address> addresses = List.of(address,address1);
        Pageable pageRequest = PaginationUtil.getPageRequests(1, 3, "street", Sort.Direction.ASC);

        PageImpl<Address> page = new PageImpl<>(addresses, pageRequest,addresses.size());
        when(addressRepository.findByAddressStatusNot(pageRequest,AddressStatus.DELETED)).thenReturn(page);

        Page<AddressInfoResponse> result = addressService.getAllAddressDist(1,3,"street", Sort.Direction.ASC,null);
        assertEquals(addresses.size(),result.getTotalElements());
    }
}