package com.example.diplom.model.dto.response;

import com.example.diplom.model.dto.request.CounterInfoRequest;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CounterInfoResponse extends CounterInfoRequest {
    Long id;
}