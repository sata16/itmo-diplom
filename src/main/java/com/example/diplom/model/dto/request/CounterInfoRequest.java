package com.example.diplom.model.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CounterInfoRequest {
    String serialNumber;
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    LocalDateTime dataBegin;
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    LocalDate dataCheck;
    Long valueBegin;

}
