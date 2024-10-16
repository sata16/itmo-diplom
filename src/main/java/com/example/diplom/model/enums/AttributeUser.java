package com.example.diplom.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AttributeUser {
    CONSUMER("Потребитель"),
    PROVIDER("Поставщик");

    private final String description;
}
