package com.example.diplom.service;

import com.example.diplom.exceptions.CustomException;
import com.example.diplom.model.db.entity.Address;
import com.example.diplom.model.db.repository.AddressRepository;
import com.example.diplom.model.dto.request.AddressInfoRequest;
import com.example.diplom.model.dto.response.AddressInfoResponse;
import com.example.diplom.model.enums.AddressStatus;
import com.example.diplom.model.enums.CounterStatus;
import com.example.diplom.utils.PaginationUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AddressService {
    private final ObjectMapper mapper;
    private final AddressRepository addressRepository;

    public AddressInfoResponse createAddress(AddressInfoRequest request) {
        Address address = mapper.convertValue(request, Address.class);

        address.setCreatedAt(LocalDateTime.now());
        address.setStatus(AddressStatus.CREATED);

        return mapper.convertValue(addressRepository.save(address),AddressInfoResponse.class);
    }

    //отдельный метод получения адреса
    public Address getAddressFromDB(Long id){
        return addressRepository.findById(id)
                .orElseThrow(()->new CustomException("Адрес не найден", HttpStatus.NOT_FOUND));
    }
    //Проверка статуса адреса
    public void controlStatus(Long id){
        addressRepository.findById(id).filter(user -> !user.getStatus().equals(AddressStatus.DELETED)).orElseThrow(()->new CustomException("Address has status DELETED", HttpStatus.BAD_REQUEST));


    }

    public AddressInfoResponse getAddress(Long id) {
        return mapper.convertValue(getAddressFromDB(id),AddressInfoResponse.class);
    }

    public AddressInfoResponse updateAddress(Long id, AddressInfoRequest request) {
        Address address = getAddressFromDB(id);

        address.setCity(request.getCity() == null ? address.getCity() : request.getCity());
        address.setNameDistrict(request.getNameDistrict() == null ? address.getNameDistrict() : request.getNameDistrict());
        address.setCodeDistrict(request.getCodeDistrict() == null ? address.getCodeDistrict() : request.getCodeDistrict());
        address.setStreet(request.getStreet() == null ? address.getStreet() : request.getStreet());
        address.setHouse(request.getHouse() == null ? address.getHouse() : request.getHouse());
        address.setKorpus(request.getKorpus() == null ? address.getKorpus() : request.getKorpus());
        address.setFlat(request.getFlat() == null ? address.getFlat() : request.getFlat());
        address.setCodeFias(request.getCodeFias() == null ? address.getCodeFias() : request.getCodeFias());

        address.setUpdatedAt(LocalDateTime.now());
        address.setStatus(AddressStatus.UPDATED);
        return mapper.convertValue(addressRepository.save(address),AddressInfoResponse.class);
    }

    public void deleteAddress(Long id) {
        Address address = getAddressFromDB(id);
        address.setUpdatedAt(LocalDateTime.now());
        address.setStatus(AddressStatus.DELETED);
        addressRepository.save(address);
    }

    public List<AddressInfoResponse> getAllAddress() {
        return addressRepository.findAll().stream()
                .map(address -> mapper.convertValue(address,AddressInfoResponse.class))
                .collect(Collectors.toList());
    }

    //список адресов с приборами учета
    public Page<AddressInfoResponse> getAllAddressAndCounter(Integer page, Integer perPage, String sort, Sort.Direction order, String filter) {
        Pageable pageRequest = PaginationUtil.getPageRequests(page,perPage, sort,order);
        Page<Address> all;
        if(filter == null){
            all = addressRepository.findByAddressStatusAndCounters(pageRequest, AddressStatus.DELETED, CounterStatus.CLOSED);
        }else{
            all = addressRepository.findByAddressStatusAndAttributeCounter(pageRequest,AddressStatus.DELETED,CounterStatus.CLOSED,filter);
        }
        List<AddressInfoResponse> content = all.getContent()
                .stream()
                .map(address -> mapper.convertValue(address, AddressInfoResponse.class))
                .collect(Collectors.toList());
        return new PageImpl<>(content,pageRequest, all.getTotalElements());
    }

    //список адресов по районам
    public Page<AddressInfoResponse> getAllAddressDist(Integer page, Integer perPage, String sort, Sort.Direction order, String filter) {
        Pageable pageRequest = PaginationUtil.getPageRequests(page,perPage, sort,order);
        Page<Address> all;
        if(filter == null){
            all = addressRepository.findByAddressStatusNot(pageRequest, AddressStatus.DELETED);
        }else{
            all = addressRepository.findByAddressStatusAndNameDistrict(pageRequest,AddressStatus.DELETED,filter);
        }
        List<AddressInfoResponse> content = all.getContent()
                .stream()
                .map(address -> mapper.convertValue(address, AddressInfoResponse.class))
                .collect(Collectors.toList());
        return new PageImpl<>(content,pageRequest, all.getTotalElements());
    }
}
