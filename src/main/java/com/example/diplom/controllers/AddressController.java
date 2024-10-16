package com.example.diplom.controllers;

import com.example.diplom.model.dto.request.AddressInfoRequest;
import com.example.diplom.model.dto.response.AddressInfoResponse;
import com.example.diplom.service.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
@Tag(name = "Адреса")
@RestController
@RequestMapping("/api/addresses")
@RequiredArgsConstructor
public class AddressController {
    private final AddressService addressService;

    @PostMapping
    @Operation(summary = "Создать адрес")
    public AddressInfoResponse createAddress(@RequestBody @Valid AddressInfoRequest request){
        return addressService.createAddress(request);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить адрес по id")
    public AddressInfoResponse getAddress(@PathVariable Long id){

        return addressService.getAddress(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Изменить адрес по id")
    public AddressInfoResponse updateAddress(@PathVariable Long id, @RequestBody AddressInfoRequest request){
        return addressService.updateAddress(id,request);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить адрес по id")
    public void deleteAddress(@PathVariable Long id){
        addressService.deleteAddress(id);
    }

    @GetMapping("/all")
    @Operation(summary = "Получить все адреса")
    public List<AddressInfoResponse> getAllAddress(){
        return addressService.getAllAddress();
    }
}
