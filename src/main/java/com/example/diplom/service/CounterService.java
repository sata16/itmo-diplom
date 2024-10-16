package com.example.diplom.service;

import com.example.diplom.model.dto.request.CounterInfoRequest;
import com.example.diplom.model.dto.response.CounterInfoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CounterService {

    public CounterInfoResponse createCounter(CounterInfoRequest request) {
        return null;
    }

    public CounterInfoResponse getCounter(Long id) {
        return null;
    }

    public CounterInfoResponse updateCounter(Long id, CounterInfoRequest request) {
        return null;
    }

    public void deleteCounter(Long id) {
    }

    public List<CounterInfoResponse> getAllCounters() {
        return null;
    }
}
