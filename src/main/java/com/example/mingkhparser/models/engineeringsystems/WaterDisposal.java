package com.example.mingkhparser.models.engineeringsystems;

import lombok.Getter;

@Getter
public enum WaterDisposal {
    CENTRAL("Центральное"),
    NONE("Отсутствует"),
    CESSPOOL("Выгребная яма"),
    AUTONOMOUS("Автономное"),
    LOCALSEWERAGESEPTIC("Локальная канализация (септик)");

    private final String name;

    WaterDisposal(String name) {
        this.name = name;
    }

}
