package com.example.diplom.service;

import com.example.diplom.exceptions.CustomException;
import com.example.diplom.model.db.entity.Counter;
import com.example.diplom.model.db.entity.Value;
import com.example.diplom.model.db.repository.ValueRepository;
import com.example.diplom.model.dto.request.ValueInfoRequest;
import com.example.diplom.model.dto.request.ValueToCounterRequest;
import com.example.diplom.model.dto.response.ValueInfoResponse;
import com.example.diplom.model.enums.ValueStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ValueService {
    private final CounterService counterService;
    private final ObjectMapper mapper;
    private final ValueRepository valueRepository;

    public ValueInfoResponse createValue(ValueInfoRequest request) {
        //проверка периода ввода показаний. показания можно ввести только в текущем периоде
        validPeriod(request);

        Value value = mapper.convertValue(request, Value.class);
        value.setCreatedAt(LocalDateTime.now());
        value.setStatus(ValueStatus.CREATED);
        return mapper.convertValue(valueRepository.save(value),ValueInfoResponse.class);
    }

    //проверка периода ввода показаний. показания можно ввести только в текущем периоде
    public void validPeriod(ValueInfoRequest request){
        if(!YearMonth.now().equals(YearMonth.of(request.getPeriod().getYear(), request.getPeriod().getMonth()))) {
            throw new CustomException("Invalid Period", HttpStatus.BAD_REQUEST);
        } ;

    }

    //метод получения показания
    private Value getValueFromDB(Long id){
        return  valueRepository.findById(id).orElseThrow(()->new CustomException("Показания не найдены", HttpStatus.NOT_FOUND));
    }

    //Проверка статуса Показаний
    public void controlStatus(Long id){
        valueRepository.findById(id).filter(value -> !value.getStatus().equals(ValueStatus.DELETED)).orElseThrow(()->new CustomException("Value has status DELETED", HttpStatus.BAD_REQUEST));


    }

    public ValueInfoResponse getValue(Long id) {
        return mapper.convertValue(getValueFromDB(id),ValueInfoResponse.class);
    }

    public ValueInfoResponse updateValue(Long id, ValueInfoRequest request) {
        Value value = getValueFromDB(id);
        validPeriod(request);
        value.setPeriod(request.getPeriod());
        value.setValue(request.getValue() == null ? value.getValue() : request.getValue());

        value.setUpdatedAt(LocalDateTime.now());
        value.setStatus(ValueStatus.UPDATED);

        return mapper.convertValue(valueRepository.save(value),ValueInfoResponse.class);
    }

    public void deleteValue(Long id) {
        Value value = getValueFromDB(id);
        value.setStatus(ValueStatus.DELETED);
        value.setUpdatedAt(LocalDateTime.now());
        valueRepository.save(value);
    }

    public List<ValueInfoResponse> getAllValue() {
        return valueRepository.findAll().stream()
                .map(value ->mapper.convertValue(value, ValueInfoResponse.class))
                .collect(Collectors.toList());
    }
    //связать ПУ и показания
    public void addValueToCounter(ValueToCounterRequest request) {
        //проверяем статусы
        controlStatus(request.getValueId());
        counterService.controlStatus(request.getCounterId());

        Counter counterFromDB = counterService.getCounterFromDB(request.getCounterId());
        Value valueFromDB = getValueFromDB(request.getValueId());

        valueFromDB.setCounter(counterFromDB);
        valueRepository.save(valueFromDB);
    }

    public List<ValueInfoResponse> getAllValueToCounter(Long id) {

            //Проверка наличия прибора учета
            counterService.getCounterFromDB(id);
            //Проверка статуса прибора учета
            counterService.controlStatus(id);

            return valueRepository.findAllValueToCounter(id,ValueStatus.DELETED).stream()
                    .map(value->mapper.convertValue(value,ValueInfoResponse.class))
                    .collect(Collectors.toList());
    }
}
