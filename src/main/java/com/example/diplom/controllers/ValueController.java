package com.example.diplom.controllers;

import com.example.diplom.model.dto.request.UserInfoRequest;
import com.example.diplom.model.dto.request.ValueInfoRequest;
import com.example.diplom.model.dto.response.UserInfoResponse;
import com.example.diplom.model.dto.response.ValueInfoResponse;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/values")
public class ValueController {
    @PostMapping
    public ValueInfoResponse createValue(@RequestBody ValueInfoRequest request){
        return new ValueInfoResponse();
    }
    @GetMapping("/{id}")
    public ValueInfoResponse getValue(@PathVariable Long id){
        return new ValueInfoResponse();
    }
    @PutMapping("/{id}")
    public ValueInfoResponse updateValue(@PathVariable Long id, @RequestBody ValueInfoRequest request){
        return new ValueInfoResponse();
    }
    @GetMapping("/all")
    public List<ValueInfoResponse> getAllValue(){
        return Collections.singletonList(new ValueInfoResponse());
    }
}
