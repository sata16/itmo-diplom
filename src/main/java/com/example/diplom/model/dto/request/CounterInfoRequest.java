package com.example.diplom.model.dto.request;

import com.example.diplom.model.enums.AttributeCounter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import java.time.LocalDate;


@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CounterInfoRequest {
    String serialNumber;
    LocalDate dataBegin;
    LocalDate dataCheck;
    Long valueBegin;
    AttributeCounter attributeCounter;

}
