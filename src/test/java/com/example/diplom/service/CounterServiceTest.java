package com.example.diplom.service;

import com.example.diplom.exceptions.CustomException;
import com.example.diplom.model.db.entity.Address;
import com.example.diplom.model.db.entity.Counter;
import com.example.diplom.model.db.entity.TypeCounter;
import com.example.diplom.model.db.repository.CounterRepository;
import com.example.diplom.model.dto.request.*;
import com.example.diplom.model.dto.response.CounterInfoResponse;
import com.example.diplom.model.enums.AttributeCounter;
import com.example.diplom.model.enums.CounterStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CounterServiceTest {
    @InjectMocks
    private CounterService counterService;
    @Mock
    private AddressService addressService;
    @Mock
    private TypeCounterService typeCounterService;
    @Mock
    private CounterRepository counterRepository;
    @Spy
    private ObjectMapper mapper;

    @Test
    public void createCounter() {
        CounterInfoRequest request = new CounterInfoRequest();
        request.setSerialNumber("123");

        Counter counter = new Counter();
        counter.setId(1L);

        when(counterRepository.save(any(Counter.class))).thenReturn(counter);

        CounterInfoResponse result = counterService.createCounter(request);
        assertEquals(counter.getId(), result.getId());
    }

    @Test(expected = CustomException.class)
    public void createCounter_counterExists() {
        CounterInfoRequest request = new CounterInfoRequest();
        request.setSerialNumber("123");

        Counter counter = new Counter();
        counter.setId(1L);

        when(counterRepository.findBySerialNumberIgnoreCase(anyString())).thenReturn(Optional.of(counter));
        counterService.createCounter(request);
    }

    @Test
    public void controlStatus() {
        Counter counter = new Counter();
        counter.setId(1L);
        counter.setStatus(CounterStatus.OPENED);
        when(counterRepository.findById(counter.getId())).thenReturn(Optional.of(counter));
        counterService.controlStatus(counter.getId());
        assertEquals(CounterStatus.OPENED, counter.getStatus());
    }
    @Test(expected = CustomException.class)
    public void controlStatus_badStatus() {
        Counter counter = new Counter();
        counter.setId(1L);
        counter.setStatus(CounterStatus.CLOSED);

        counterService.controlStatus(counter.getId());
    }


    @Test
    public void getCounter() {
        Counter counter = new Counter();
        counter.setId(1L);
        when(counterRepository.findById(counter.getId())).thenReturn(Optional.of(counter));
        CounterInfoResponse result = counterService.getCounter(1L);
        assertEquals(counter.getId(), result.getId());
    }

    @Test
    public void updateCounter() {
        CounterInfoRequest request = new CounterInfoRequest();
        request.setSerialNumber("123");
        request.setAttributeCounter(AttributeCounter.COMMUNAL);

        Counter counter = new Counter();
        counter.setId(1L);

        when(counterRepository.findById(counter.getId())).thenReturn(Optional.of(counter));
        counterService.updateCounter(1L, request);
        verify(counterRepository,times(1)).save(any(Counter.class));
        assertEquals(CounterStatus.UPDATED, counter.getStatus());
        assertEquals(AttributeCounter.COMMUNAL, counter.getAttributeCounter());
    }

    @Test
    public void deleteCounter() {
        Counter counter = new Counter();
        counter.setId(1L);
        when(counterRepository.findById(counter.getId())).thenReturn(Optional.of(counter));
        counterService.deleteCounter(counter.getId());
        verify(counterRepository, times(1)).save(any(Counter.class));
        assertEquals(CounterStatus.CLOSED,counter.getStatus());
    }

    @Test
    public void addCounterToAddress() {
        Counter counter = new Counter();
        counter.setId(1L);
        counter.setStatus(CounterStatus.OPENED);
        when(counterRepository.findById(counter.getId())).thenReturn(Optional.of(counter));
        Address address = new Address();
        address.setId(1L);
        when(addressService.getAddressFromDB(address.getId())).thenReturn(address);
        CounterToAddressRequest request = CounterToAddressRequest.builder()
                .counterId(counter.getId())
                .addressId(address.getId())
                .build();
        counterService.addCounterToAddress(request);
        verify(counterRepository,times(1)).save(any(Counter.class));
        assertEquals(address.getId(),counter.getAddress().getId());
    }

    @Test
    public void addCounterToTypeCounter() {
        Counter counter = new Counter();
        counter.setId(1L);
        counter.setStatus(CounterStatus.OPENED);
        when(counterRepository.findById(counter.getId())).thenReturn(Optional.of(counter));
        TypeCounter typeCounter = new TypeCounter();
        typeCounter.setId(1L);
        when(typeCounterService.getTypeCounterFromDB(typeCounter.getId())).thenReturn(typeCounter);
        CounterToTypeCounterRequest request = CounterToTypeCounterRequest.builder()
                .counterId(counter.getId())
                .typeCounterId(typeCounter.getId())
                .build();
        counterService.addCounterToTypeCounter(request);
        verify(counterRepository,times(1)).save(any(Counter.class));
        assertEquals(typeCounter.getId(),counter.getTypeCounter().getId());
    }
}