package com.example.diplom.controllers;
import com.example.diplom.model.dto.request.TypeCounterInfoRequest;

import com.example.diplom.model.dto.response.TypeCounterInfoResponse;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/typecounters")
public class TypeCounterController {
    @PostMapping
    public TypeCounterInfoResponse createTypeCounter(@RequestBody TypeCounterInfoRequest request){
        return new TypeCounterInfoResponse();
    }
    @GetMapping("/{id}")
    public TypeCounterInfoResponse getTypeCounter(@PathVariable Long id){
        return new TypeCounterInfoResponse();
    }
    @PutMapping("/{id}")
    public TypeCounterInfoResponse updateTypeCounter(@PathVariable Long id, @RequestBody TypeCounterInfoRequest request){
        return new TypeCounterInfoResponse();
    }
    @GetMapping("/all")
    public List<TypeCounterInfoResponse> getAllTypeCounter(){
        return Collections.singletonList(new TypeCounterInfoResponse());
    }
}
