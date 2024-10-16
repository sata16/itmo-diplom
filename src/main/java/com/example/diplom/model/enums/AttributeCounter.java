package com.example.diplom.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AttributeCounter {
    COMMUNAL("ОДПУ"),
    INDIVIDUAL("ИПУ");

    private final String description;
}
