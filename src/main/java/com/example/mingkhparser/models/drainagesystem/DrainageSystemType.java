package com.example.mingkhparser.models.drainagesystem;

import com.example.mingkhparser.models.IEnum;
import lombok.Getter;

@Getter
public enum DrainageSystemType implements IEnum {
    CENTRALIZEDSEWERAGE("Централизованная канализация"),
    NONE("Нет"),
    CESSPOOL("Выгребная яма"),
    LOCALSEWERAGESEPTIC("Локальная канализация (септик)");

    private final String name;

    DrainageSystemType(String name) {
        this.name = name;
    }

}
