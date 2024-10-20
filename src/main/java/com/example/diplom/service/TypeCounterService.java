package com.example.diplom.service;

import com.example.diplom.exceptions.CustomException;
import com.example.diplom.model.db.entity.TypeCounter;
import com.example.diplom.model.db.repository.TypeCounterRepository;
import com.example.diplom.model.dto.request.TypeCounterInfoRequest;
import com.example.diplom.model.dto.response.TypeCounterInfoResponse;
import com.example.diplom.model.enums.TypeCounterStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TypeCounterService {

    private  final ObjectMapper mapper;
    private final TypeCounterRepository typeCounterRepository;

    public TypeCounterInfoResponse createTypeCounter(TypeCounterInfoRequest request) {
        TypeCounter typeCounter = mapper.convertValue(request,TypeCounter.class);

        typeCounter.setCreatedAt(LocalDateTime.now());
        typeCounter.setStatus(TypeCounterStatus.CREATED);
        return mapper.convertValue(typeCounterRepository.save(typeCounter),TypeCounterInfoResponse.class);
    }
    //метод получения типов приборов учета
    public TypeCounter getTypeCounterFromDB(Long id){
        return typeCounterRepository.findById(id).orElseThrow(()->new CustomException("Тип ПУ не найден", HttpStatus.NOT_FOUND));
    }
    //Проверка статуса ПУ
    public void controlStatus(Long id){
        typeCounterRepository.findById(id).filter(user -> !user.getStatus().equals(TypeCounterStatus.DELETED)).orElseThrow(()->new CustomException("Type Counter has status DELETED", HttpStatus.BAD_REQUEST));


    }

    public TypeCounterInfoResponse getTypeCounter(Long id) {
        return mapper.convertValue(getTypeCounterFromDB(id),TypeCounterInfoResponse.class);
    }

    public TypeCounterInfoResponse updateTypeCounter(Long id, TypeCounterInfoRequest request) {
        TypeCounter typeCounter = getTypeCounterFromDB(id);
        typeCounter.setCapacity(request.getCapacity() == null ? typeCounter.getCapacity() : request.getCapacity());
        typeCounter.setNameCounter(request.getNameCounter() == null ? typeCounter.getNameCounter() : request.getNameCounter());
        typeCounter.setUnit(request.getUnit() == null ? typeCounter.getUnit() : request.getUnit());

        typeCounter.setUpdatedAt(LocalDateTime.now());
        typeCounter.setStatus(TypeCounterStatus.UPDATED);
        return mapper.convertValue(typeCounterRepository.save(typeCounter),TypeCounterInfoResponse.class);
    }

    public void deleteTypeCounter(Long id) {
        TypeCounter typeCounter = getTypeCounterFromDB(id);
        typeCounter.setUpdatedAt(LocalDateTime.now());
        typeCounter.setStatus(TypeCounterStatus.DELETED);

        typeCounterRepository.save(typeCounter);

    }

    public List<TypeCounterInfoResponse> getAllTypeCounter() {
        return typeCounterRepository.findAll().stream()
                .map(typeCounter -> mapper.convertValue(typeCounter,TypeCounterInfoResponse.class))
                .collect(Collectors.toList());
    }

}
