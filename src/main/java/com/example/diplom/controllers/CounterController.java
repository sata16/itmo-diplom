package com.example.diplom.controllers;

import com.example.diplom.model.dto.request.CounterInfoRequest;
import com.example.diplom.model.dto.response.CounterInfoResponse;
import com.example.diplom.service.CounterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


import java.util.List;
@Tag(name = "Приборы учета")
@RestController
@RequestMapping("/api/counters")
@RequiredArgsConstructor
public class CounterController {
    private final CounterService counterService;

    @PostMapping
    @Operation(summary = "Создать прибор учета")
    public CounterInfoResponse createCounter(@RequestBody  CounterInfoRequest request){
        return counterService.createCounter(request);
    }
    @GetMapping("/{id}")
    @Operation(summary = "Получить прибор учета по id")
    public CounterInfoResponse getCounter(@PathVariable Long id){
        return counterService.getCounter(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить прибор учета по id")
    public CounterInfoResponse updateCounter(@PathVariable Long id, @RequestBody  CounterInfoRequest request){
        return counterService.updateCounter(id,request);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить прибор учета по id")
    public void deleteCounter(@PathVariable Long id){
        counterService.deleteCounter(id);
    }

    @GetMapping("/all")
    @Operation(summary = "Получить все приборы учета")
    public List<CounterInfoResponse> getAllCounters(){
        return counterService.getAllCounters();
    }

}
