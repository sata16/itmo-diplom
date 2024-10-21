package com.example.diplom.service;

import com.example.diplom.model.db.entity.*;
import com.example.diplom.model.db.repository.ValueRepository;
import com.example.diplom.model.dto.request.ValueInfoRequest;
import com.example.diplom.model.dto.request.ValueToCounterRequest;
import com.example.diplom.model.dto.response.ValueInfoResponse;
import com.example.diplom.model.enums.ValueStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ValueServiceTest {
    @InjectMocks
    private ValueService valueService;
    @Mock
    private CounterService counterService;
    @Mock
    private ValueRepository valueRepository;
    @Spy
    private ObjectMapper mapper;

    @Test
    public void createValue() {
        ValueInfoRequest request = new ValueInfoRequest();

        Value value = new Value();
        value.setId(1L);

        when(valueRepository.save(any(Value.class))).thenReturn(value);

        ValueInfoResponse result = valueService.createValue(request);
        assertEquals(value.getId(), result.getId());
    }

    @Test
    public void controlStatus() {
        Value value = new Value();
        value.setId(1L);
        value.setStatus(ValueStatus.UPDATED);

        when(valueRepository.findById(value.getId())).thenReturn(Optional.of(value));
        valueService.controlStatus(value.getId());
        assertEquals(ValueStatus.UPDATED, value.getStatus());
    }

    @Test
    public void getValue() {
        Value value = new Value();
        value.setId(1L);
        when(valueRepository.findById(value.getId())).thenReturn(Optional.of(value));
        ValueInfoResponse result = valueService.getValue(1L);
        assertEquals(value.getId(), result.getId());
    }

    @Test
    public void updateValue() {
        ValueInfoRequest request = new ValueInfoRequest();
        request.setValue(5L);

        Value value = new Value();
        value.setId(1L);
        when(valueRepository.findById(value.getId())).thenReturn(Optional.of(value));
        valueService.updateValue(1L, request);
        verify(valueRepository,times(1)).save(any(Value.class));
        assertEquals(ValueStatus.UPDATED, value.getStatus());
        assertEquals(request.getValue(), value.getValue());
    }

    @Test
    public void deleteValue() {
        Value value = new Value();
        value.setId(1L);
        when(valueRepository.findById(value.getId())).thenReturn(Optional.of(value));
        valueService.deleteValue(value.getId());
        verify(valueRepository, times(1)).save(any(Value.class));
        assertEquals(ValueStatus.DELETED,value.getStatus());
    }

    @Test
    public void getAllValue() {
        Value value = new Value();
        value.setId(1L);

        Value value1 = new Value();
        value1.setId(2L);

        List<Value> values = List.of(value, value1);
        when(valueRepository.findAll()).thenReturn(values);
        List<ValueInfoResponse> result = valueService.getAllValue();

        assertEquals(values.size(),result.size());
    }

    @Test
    public void addValueToCounter() {
        Value value = new Value();
        value.setId(1L);
        value.setStatus(ValueStatus.CREATED);
        when(valueRepository.findById(value.getId())).thenReturn(Optional.of(value));
        Counter counter = new Counter();
        counter.setId(1L);
        when(counterService.getCounterFromDB(counter.getId())).thenReturn(counter);
        ValueToCounterRequest request = ValueToCounterRequest.builder()
                .counterId(counter.getId())
                .valueId(value.getId())
                .build();
        valueService.addValueToCounter(request);
        verify(valueRepository,times(1)).save(any(Value.class));
        assertEquals(counter.getId(),value.getCounter().getId());
    }

    @Test
    public void getAllValueToCounter() {
        Counter counter = new Counter();
        counter.setId(1L);

        Value value1 = new Value();
        value1.setId(1L);

        Value value2 = new Value();
        value2.setId(2L);

        List<Value> values = List.of(value1, value2);
        when(valueRepository.findAllValueToCounter(counter.getId(), ValueStatus.DELETED)).thenReturn(values);
        List<ValueInfoResponse> result = valueService.getAllValueToCounter(counter.getId());


        assertEquals(values.size(), result.size());
    }
}