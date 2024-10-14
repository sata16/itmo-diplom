package com.example.diplom.controllers;

import com.example.diplom.model.dto.request.CounterInfoRequest;
import com.example.diplom.model.dto.request.UserInfoRequest;
import com.example.diplom.model.dto.response.CounterInfoResponse;
import com.example.diplom.model.dto.response.UserInfoResponse;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/counters")
public class CounterController {
    @PostMapping
    public CounterInfoResponse createCounter(@RequestBody  CounterInfoRequest request){
        return new CounterInfoResponse();
    }
    @GetMapping("/{id}")
    public CounterInfoResponse getCounter(@PathVariable Long id){
        return new CounterInfoResponse();
    }
    @PutMapping("/{id}")
    public CounterInfoResponse updateCounter(@PathVariable Long id, @RequestBody  CounterInfoRequest request){
        return new CounterInfoResponse();
    }
    @GetMapping("/all")
    public List<CounterInfoResponse> getAllCounters(){
        return Collections.singletonList(new CounterInfoResponse());
    }

}
