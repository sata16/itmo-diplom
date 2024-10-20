package com.example.diplom.controllers;
import com.example.diplom.model.dto.request.ValueInfoRequest;
import com.example.diplom.model.dto.request.ValueToCounterRequest;
import com.example.diplom.model.dto.response.ValueInfoResponse;
import com.example.diplom.service.ValueService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
@Tag(name = "Показания по ИПУ")
@RestController
@RequestMapping("/api/values")
@RequiredArgsConstructor
public class ValueController {
    private final ValueService valueService;

    @PostMapping
    @Operation(summary = "Создать показание")
    public ValueInfoResponse createValue(@RequestBody @Valid ValueInfoRequest request){
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

    @GetMapping("/all/{id}")
    @Operation(summary = "Получить все показания по id прибора учета")
    public List<ValueInfoResponse> getAllValueToCounter(@PathVariable Long id){
        return valueService.getAllValueToCounter(id);
    }

    @PostMapping("/valueToCounter")
    @Operation(summary = "Добавить показания к прибору учета")
    public void addValueToCounter(@RequestBody @Valid ValueToCounterRequest request){
        valueService.addValueToCounter(request);
    }

}
