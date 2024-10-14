package com.example.diplom.controllers;

import com.example.diplom.model.dto.request.AddressInfoRequest;
import com.example.diplom.model.dto.request.UserInfoRequest;
import com.example.diplom.model.dto.response.AddressInfoResponse;
import com.example.diplom.model.dto.response.UserInfoResponse;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {
    @PostMapping
    public AddressInfoResponse createAddress(@RequestBody AddressInfoRequest request){
        return new AddressInfoResponse();
    }
    @GetMapping("/{id}")
    public AddressInfoResponse getAddress(@PathVariable Long id){
        return new AddressInfoResponse();
    }
    @PutMapping("/{id}")
    public AddressInfoResponse updateAddress(@PathVariable Long id, @RequestBody AddressInfoRequest request){
        return new AddressInfoResponse();
    }
    @GetMapping("/all")
    public List<AddressInfoResponse> getAllAddress(){
        return Collections.singletonList(new AddressInfoResponse());
    }
}
