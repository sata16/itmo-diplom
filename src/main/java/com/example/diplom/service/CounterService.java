package com.example.diplom.service;

import com.example.diplom.exceptions.CustomException;
import com.example.diplom.model.db.entity.Address;
import com.example.diplom.model.db.entity.Counter;
import com.example.diplom.model.db.entity.TypeCounter;
import com.example.diplom.model.db.repository.CounterRepository;
import com.example.diplom.model.dto.request.CounterInfoRequest;
import com.example.diplom.model.dto.request.CounterToAddressRequest;
import com.example.diplom.model.dto.request.CounterToTypeCounterRequest;
import com.example.diplom.model.dto.response.CounterInfoResponse;
import com.example.diplom.model.dto.response.ServiceInfoResponse;
import com.example.diplom.model.enums.AttributeCounter;
import com.example.diplom.model.enums.CounterStatus;
import com.example.diplom.model.enums.ServiceStatus;
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
public class CounterService {
    private final AddressService addressService;
    private final TypeCounterService typeCounterService;
    private final ObjectMapper mapper;
    private final CounterRepository counterRepository;

    public CounterInfoResponse createCounter(CounterInfoRequest request) {
        //сначала проверяем, что серийный номер отсутствует в БД
        counterRepository.findBySerialNumberIgnoreCase(request.getSerialNumber())
                .ifPresent(counter->{
                    throw new CustomException(String.format("SerialNumber with number: %s already exists",request.getSerialNumber()),HttpStatus.BAD_REQUEST);
                });

        Counter counter = mapper.convertValue(request,Counter.class);
        counter.setCreatedAt(LocalDateTime.now());
        counter.setStatus(CounterStatus.OPENED);

        return mapper.convertValue(counterRepository.save(counter), CounterInfoResponse.class);
    }

    //метод получения прибора учета
    public Counter getCounterFromDB(Long id){
        return counterRepository.findById(id)
                .orElseThrow(()->new CustomException("Прибор учета не найден", HttpStatus.NOT_FOUND));
    }

    //Проверка статуса Прибора Учета
    public void controlStatus(Long id){
        counterRepository.findById(id).filter(user -> !user.getStatus().equals(CounterStatus.CLOSED)).orElseThrow(()->new CustomException("Counter has status DELETED", HttpStatus.BAD_REQUEST));
    }

    public CounterInfoResponse getCounter(Long id) {
        return mapper.convertValue(getCounterFromDB(id), CounterInfoResponse.class);
    }

    public CounterInfoResponse updateCounter(Long id, CounterInfoRequest request) {
        Counter counter = getCounterFromDB(id);

        counter.setSerialNumber(request.getSerialNumber() == null ? counter.getSerialNumber() : request.getSerialNumber());
        counter.setDataBegin(request.getDataBegin() == null ? counter.getDataBegin() : request.getDataBegin());
        counter.setDataCheck(request.getDataCheck() == null ? counter.getDataCheck() : request.getDataCheck());
        counter.setValueBegin(request.getValueBegin() == null ? counter.getValueBegin() : request.getValueBegin());
        counter.setAttributeCounter(request.getAttributeCounter() == null ? counter.getAttributeCounter() : request.getAttributeCounter());

        counter.setUpdatedAt(LocalDateTime.now());
        counter.setStatus(CounterStatus.UPDATED);

        return mapper.convertValue(counterRepository.save(counter), CounterInfoResponse.class);
    }

    public void deleteCounter(Long id) {
        Counter counter = getCounterFromDB(id);

        counter.setUpdatedAt(LocalDateTime.now());
        counter.setStatus(CounterStatus.CLOSED);

        counterRepository.save(counter);
    }

    public List<CounterInfoResponse> getAllCounters() {
        return counterRepository.findAll().stream()
                .map(counter -> mapper.convertValue(counter, CounterInfoResponse.class))
                .collect(Collectors.toList());
    }

    // связь прибора учета и адреса
    public void addCounterToAddress(CounterToAddressRequest request) {
        //проверка статуса ПУ и адреса
        controlStatus(request.getCounterId());
        addressService.controlStatus(request.getAddressId());

        Counter counterFromDB = getCounterFromDB(request.getCounterId());
        Address addressFromDB = addressService.getAddressFromDB(request.getAddressId());

        counterFromDB.setAddress(addressFromDB);
        counterRepository.save(counterFromDB);
    }

   // Связь прибора учете с типом ПУ
    public void addCounterToTypeCounter(CounterToTypeCounterRequest request) {
        //проверяем статусы
        controlStatus(request.getCounterId());
        typeCounterService.controlStatus(request.getTypeCounterId());

        Counter counterFromDB = getCounterFromDB(request.getCounterId());
        TypeCounter typeCounterFromDB = typeCounterService.getTypeCounterFromDB(request.getTypeCounterId());

        counterFromDB.setTypeCounter(typeCounterFromDB);
        counterRepository.save(counterFromDB);
    }

    //список ИПУ по адресу
    public List<CounterInfoResponse> getAllCounterToAddress(Long id, AttributeCounter attributeCounter) {
        return counterRepository.findCounterByAddress(id, CounterStatus.CLOSED, attributeCounter).stream()
                .map(counter -> mapper.convertValue(counter, CounterInfoResponse.class))
                .collect(Collectors.toList());
    }
}
