package com.example.diplom.service;

import com.example.diplom.model.dto.request.ValueInfoRequest;
import com.example.diplom.model.dto.response.ValueInfoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ValueService {
    public ValueInfoResponse createValue(ValueInfoRequest request) {
        return null;
    }

    public ValueInfoResponse getValue(Long id) {
        return null;
    }

    public ValueInfoResponse updateValue(Long id, ValueInfoRequest request) {
        return null;
    }

    public void deleteValue(Long id) {
    }

    public List<ValueInfoResponse> getAllValue() {
        return null;
    }
}
