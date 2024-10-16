package com.example.diplom.service;

import com.example.diplom.model.dto.request.ServiceInfoRequest;
import com.example.diplom.model.dto.response.ServiceInfoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ServiceService {
    public ServiceInfoResponse createService(ServiceInfoRequest request) {
        return  null;
    }

    public ServiceInfoResponse getService(Long id) {
        return  null;
    }

    public ServiceInfoResponse updateService(Long id, ServiceInfoRequest request) {
        return  null;
    }

    public void deleteService(Long id) {

    }

    public List<ServiceInfoResponse> getAllService() {
        return  null;
    }
}
