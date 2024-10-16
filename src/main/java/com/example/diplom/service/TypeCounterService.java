package com.example.diplom.service;

import com.example.diplom.model.dto.request.TypeCounterInfoRequest;
import com.example.diplom.model.dto.response.TypeCounterInfoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TypeCounterService {

    public TypeCounterInfoResponse createTypeCounter(TypeCounterInfoRequest request) {
        return null;
    }

    public TypeCounterInfoResponse getTypeCounter(Long id) {
        return null;
    }

    public TypeCounterInfoResponse updateTypeCounter(Long id, TypeCounterInfoRequest request) {
        return null;
    }

    public void deleteTypeCounter(Long id) {
    }

    public List<TypeCounterInfoResponse> getAllTypeCounter() {
        return null;
    }
}
