package com.example.diplom.model.dto.request;

import com.example.diplom.model.enums.AttributeCounter;
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
public class CounterInfoRequest {
    String serialNumber;
    LocalDate dataBegin;
    LocalDate dataCheck;
    Long valueBegin;
    AttributeCounter attributeCounter;

}
