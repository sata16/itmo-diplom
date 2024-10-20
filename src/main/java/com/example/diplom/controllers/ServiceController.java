package com.example.diplom.controllers;

import com.example.diplom.model.dto.request.AddressToServiceRequest;
import com.example.diplom.model.dto.request.ServiceInfoRequest;
import com.example.diplom.model.dto.response.ServiceInfoResponse;
import com.example.diplom.service.ServiceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "Услуги")
@RestController
@RequestMapping("/api/services")
@RequiredArgsConstructor
public class ServiceController {
    private  final ServiceService serviceService;

    @PostMapping
    @Operation(summary = "Создать услугу")
    public ServiceInfoResponse createService(@RequestBody ServiceInfoRequest request){
        return serviceService.createService(request);
    }
    @GetMapping("/{id}")
    @Operation(summary = "Получить услугу по id")
    public ServiceInfoResponse getService(@PathVariable Long id){
        return serviceService.getService(id);
    }
    @PutMapping("/{id}")
    @Operation(summary = "Обновить услугу по id")
    public ServiceInfoResponse updateService(@PathVariable Long id, @RequestBody ServiceInfoRequest request){
        return serviceService.updateService(id,request);
    }
    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить услугу по id")
    public void deleteService(@PathVariable Long id){
        serviceService.deleteService(id);
    }
    @GetMapping("/all")
    @Operation(summary = "Получить список услуг")
    public List<ServiceInfoResponse> getAllService(){
        return serviceService.getAllService();
    }

    @PostMapping("/serviceToAddress")
    @Operation(summary = "Связать услугу и адрес")
    public void addServiceToAddress(@RequestBody @Valid AddressToServiceRequest request){
        serviceService.addServiceToAddress(request);
    }
    @GetMapping("/allToAddress/{id}")
    @Operation(summary = "Получить список услуг по адресу")
    public List<ServiceInfoResponse> getAllServiceToAddress(@PathVariable Long id){
        return serviceService.getAllServiceToAddress(id);
    }

}
