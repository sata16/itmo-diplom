package com.example.diplom.controllers;
import com.example.diplom.model.dto.request.TypeCounterInfoRequest;

import com.example.diplom.model.dto.response.TypeCounterInfoResponse;
import com.example.diplom.service.TypeCounterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
@Tag(name = "Тип прибора учета")
@RestController
@RequestMapping("/api/typecounters")
@RequiredArgsConstructor
public class TypeCounterController {
    private final TypeCounterService typeCounterService;

    @PostMapping
    @Operation(summary = "Создать тип ПУ")
    public TypeCounterInfoResponse createTypeCounter(@RequestBody TypeCounterInfoRequest request){
        return typeCounterService.createTypeCounter(request);
    }
    @GetMapping("/{id}")
    @Operation(summary = "Получить тип ПУ по id")
    public TypeCounterInfoResponse getTypeCounter(@PathVariable Long id){
        return typeCounterService.getTypeCounter(id);
    }
    @PutMapping("/{id}")
    @Operation(summary = "Обновить тип ПУ по id")
    public TypeCounterInfoResponse updateTypeCounter(@PathVariable Long id, @RequestBody TypeCounterInfoRequest request){
        return typeCounterService.updateTypeCounter(id,request);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить тип ПУ по id")
    public void deleteTypeCounter(@PathVariable Long id){
        typeCounterService.deleteTypeCounter(id);
    }

    @GetMapping("/all")
    @Operation(summary = "Получить все типы ПУ")
    public List<TypeCounterInfoResponse> getAllTypeCounter(){
        return typeCounterService.getAllTypeCounter();
    }
}
