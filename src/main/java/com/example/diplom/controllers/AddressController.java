package com.example.diplom.controllers;

import com.example.diplom.model.dto.request.AddressInfoRequest;
import com.example.diplom.model.dto.response.AddressInfoResponse;
import com.example.diplom.service.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

//    @GetMapping("/all")
//    @Operation(summary = "Получить все адреса")
//    public List<AddressInfoResponse> getAllAddress(){
//        return addressService.getAllAddress();
//    }
    @GetMapping("/allToUsers/{id}")
    @Operation(summary = "Получить список адресов пользователя")
    public List<AddressInfoResponse> getAllAddressToUser(@PathVariable Long id){
        return addressService.getAllAddressToUser(id);
    }

    @GetMapping("/allAndCounter")
    @Operation(summary = "Получить список адресов с ПУ в разбивке на ИПУ,ОДПУ")
    public Page<AddressInfoResponse> getAllAddressAndCounter(@RequestParam(defaultValue = "1") Integer page,
                                          @RequestParam(defaultValue = "10") Integer perPage,
                                          @RequestParam(defaultValue = "nameDistrict") String sort,
                                          @RequestParam(defaultValue = "ASC") Sort.Direction order,
                                          @RequestParam(required = false) String filter) {
    return addressService.getAllAddressAndCounter(page, perPage, sort, order, filter);
    }

    @GetMapping("/allAndDist")
    @Operation(summary = "Получить список адресов с фильтром на район")
    public Page<AddressInfoResponse> getAllAddressDist(@RequestParam(defaultValue = "1") Integer page,
                                                             @RequestParam(defaultValue = "10") Integer perPage,
                                                             @RequestParam(defaultValue = "codeDistrict") String sort,
                                                             @RequestParam(defaultValue = "ASC") Sort.Direction order,
                                                             @RequestParam(required = false) String filter) {
        return addressService.getAllAddressDist(page, perPage, sort, order, filter);
    }


}
