package com.example.diplom.controllers;

import com.example.diplom.model.dto.request.UserInfoRequest;
import com.example.diplom.model.dto.request.ValueInfoRequest;
import com.example.diplom.model.dto.response.UserInfoResponse;
import com.example.diplom.model.dto.response.ValueInfoResponse;
import com.example.diplom.service.ValueService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
@Tag(name = "Показания по ИПУ")
@RestController
@RequestMapping("/api/values")
@RequiredArgsConstructor
public class ValueController {
    private final ValueService valueService;

    @PostMapping
    @Operation(summary = "Создать показание")
    public ValueInfoResponse createValue(@RequestBody ValueInfoRequest request){
        return valueService.createValue(request);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить показание по id")
    public ValueInfoResponse getValue(@PathVariable Long id){
        return valueService.getValue(id);
    }
    @PutMapping("/{id}")
    @Operation(summary = "Обновить показание по id")
    public ValueInfoResponse updateValue(@PathVariable Long id, @RequestBody ValueInfoRequest request){
        return valueService.updateValue(id, request);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить показание по id")
    public void deleteValue(@PathVariable Long id){
        valueService.deleteValue(id);
    }

    @GetMapping("/all")
    @Operation(summary = "Получить все показания")
    public List<ValueInfoResponse> getAllValue(){
        return valueService.getAllValue();
    }
}
