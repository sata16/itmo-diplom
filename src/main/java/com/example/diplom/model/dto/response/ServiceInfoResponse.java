package com.example.diplom.model.dto.response;

import com.example.diplom.model.dto.request.ServiceInfoRequest;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ServiceInfoResponse extends ServiceInfoRequest {
    Long id;
}
