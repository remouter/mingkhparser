package com.example.mingkhparser.models.engineeringsystems;

import lombok.Getter;

@Getter
public enum ColdWaterSupply {
    DEADEND("Тупиковая"),
    CENTRAL("Центральное"),
    NONE("Отсутствует"),
    AUTONOMOUS("Автономное");

    private final String name;

    ColdWaterSupply(String name) {
        this.name = name;
    }

}
