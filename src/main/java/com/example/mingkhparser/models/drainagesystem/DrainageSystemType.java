package com.example.mingkhparser.models.drainagesystem;

import lombok.Getter;

@Getter
public enum DrainageSystemType {
    CENTRALIZEDSEWERAGE("Централизованная канализация"),
    NONE("Нет"),
    CESSPOOL("Выгребная яма"),
    LOCALSEWERAGESEPTIC("Локальная канализация (септик)");

    private final String name;

    DrainageSystemType(String name) {
        this.name = name;
    }

}
