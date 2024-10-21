package com.example.diplom.model.dto.request;

import com.example.diplom.model.enums.AttributeUser;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserInfoRequest {
    @NotEmpty
    String email;
    String password;
    String firstName;
    String lastName;
    String middleName;
    AttributeUser attributeUser;

}
