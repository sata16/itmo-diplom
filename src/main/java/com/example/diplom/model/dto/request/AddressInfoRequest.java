package com.example.diplom.model.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddressInfoRequest {
    String city;
    String nameDistrict;
    Integer codeDistrict;
    String street;
    String house;
    String korpus;
    String flat;
    //@NotNull
    String codeFias;

}
