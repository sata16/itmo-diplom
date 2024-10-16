package com.example.diplom.service;

import com.example.diplom.model.dto.request.AddressInfoRequest;
import com.example.diplom.model.dto.response.AddressInfoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AddressService {

    public AddressInfoResponse createAddress(AddressInfoRequest request) {
        return null;
    }

    public AddressInfoResponse getAddress(Long id) {
        return null;
    }

    public AddressInfoResponse updateAddress(Long id, AddressInfoRequest request) {
        return null;
    }

    public void deleteAddress(Long id) {
    }

    public List<AddressInfoResponse> getAllAddress() {
        return null;
    }
}
