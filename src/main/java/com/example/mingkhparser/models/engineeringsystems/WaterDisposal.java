package com.example.mingkhparser.models.engineeringsystems;

import com.example.mingkhparser.models.IEnum;
import lombok.Getter;

@Getter
public enum WaterDisposal implements IEnum {
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
