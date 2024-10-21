package com.example.diplom.service;

import com.example.diplom.exceptions.CustomException;
import com.example.diplom.model.db.entity.TypeCounter;
import com.example.diplom.model.db.repository.TypeCounterRepository;
import com.example.diplom.model.dto.request.TypeCounterInfoRequest;
import com.example.diplom.model.dto.response.TypeCounterInfoResponse;
import com.example.diplom.model.enums.TypeCounterStatus;
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
public class TypeCounterServiceTest {
    @InjectMocks
    private TypeCounterService typeCounterService;

    @Mock
    private TypeCounterRepository typeCounterRepository;
    @Spy
    private ObjectMapper mapper;

    @Test
    public void createTypeCounter() {
        TypeCounterInfoRequest request = new TypeCounterInfoRequest();
        TypeCounter typeCounter = new TypeCounter();
        typeCounter.setId(1L);

        when(typeCounterRepository.save(any(TypeCounter.class))).thenReturn(typeCounter);

        TypeCounterInfoResponse result = typeCounterService.createTypeCounter(request);
        assertEquals(typeCounter.getId(), result.getId());
    }

    @Test
    public void controlStatus() {
        TypeCounter typeCounter = new TypeCounter();
        typeCounter.setId(1L);
        typeCounter.setStatus(TypeCounterStatus.UPDATED);
        when(typeCounterRepository.findById(typeCounter.getId())).thenReturn(Optional.of(typeCounter));
        typeCounterService.controlStatus(typeCounter.getId());
        assertEquals(TypeCounterStatus.UPDATED, typeCounter.getStatus());
    }
    @Test(expected = CustomException.class)
    public void controlStatus_badStatus() {
        TypeCounter typeCounter = new TypeCounter();
        typeCounter.setId(1L);
        typeCounter.setStatus(TypeCounterStatus.DELETED);

        typeCounterService.controlStatus(typeCounter.getId());
    }


    @Test
    public void getTypeCounter() {
        TypeCounter typeCounter = new TypeCounter();
        typeCounter.setId(1L);
        when(typeCounterRepository.findById(typeCounter.getId())).thenReturn(Optional.of(typeCounter));
        TypeCounterInfoResponse result = typeCounterService.getTypeCounter(1L);
        assertEquals(typeCounter.getId(), result.getId());
    }

    @Test
    public void updateTypeCounter() {
        TypeCounterInfoRequest request = new TypeCounterInfoRequest();
        request.setNameCounter("ХВС");

        TypeCounter typeCounter = new TypeCounter();
        typeCounter.setId(1L);
        when(typeCounterRepository.findById(typeCounter.getId())).thenReturn(Optional.of(typeCounter));
        typeCounterService.updateTypeCounter(1L, request);
        verify(typeCounterRepository,times(1)).save(any(TypeCounter.class));
        assertEquals(TypeCounterStatus.UPDATED, typeCounter.getStatus());
        assertEquals(request.getNameCounter(), typeCounter.getNameCounter());
    }

    @Test
    public void deleteTypeCounter() {
        TypeCounter typeCounter = new TypeCounter();
        typeCounter.setId(1L);
        when(typeCounterRepository.findById(typeCounter.getId())).thenReturn(Optional.of(typeCounter));
        typeCounterService.deleteTypeCounter(typeCounter.getId());
        verify(typeCounterRepository, times(1)).save(any(TypeCounter.class));
        assertEquals(TypeCounterStatus.DELETED,typeCounter.getStatus());
    }

    @Test
    public void getAllTypeCounter() {
        TypeCounter typeCounter = new TypeCounter();
        typeCounter.setId(1L);
        typeCounter.setCapacity(6);

        TypeCounter typeCounter1= new TypeCounter();
        typeCounter1.setId(1L);
        typeCounter1.setCapacity(6);

        List<TypeCounter> typeCounters = List.of(typeCounter, typeCounter1);
        when(typeCounterRepository.findAll()).thenReturn(typeCounters);
        List<TypeCounterInfoResponse> result = typeCounterService.getAllTypeCounter();

        assertEquals(typeCounters.size(),result.size());
    }
}