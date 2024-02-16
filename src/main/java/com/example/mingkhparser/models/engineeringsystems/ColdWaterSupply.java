package com.example.mingkhparser.models.engineeringsystems;

import com.example.mingkhparser.models.IEnum;
import lombok.Getter;

@Getter
public enum ColdWaterSupply implements IEnum {
    DEADEND("Тупиковая"),
    CENTRAL("Центральное"),
    NONE("Отсутствует"),
    AUTONOMOUS("Автономное");

    private final String name;

    ColdWaterSupply(String name) {
        this.name = name;
    }

}
