package com.example.mingkhparser.models.drainagesystem;

public enum DrainageSystemType {
    CENTRALIZEDSEWERAGE("Централизованная канализация"),
    NONE("Нет"),
    CESSPOOL("Выгребная яма"),
    LOCALSEWERAGESEPTIC("Локальная канализация (септик)");

    private String name;

    DrainageSystemType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


    @Override
    public String toString() {
        return name;
    }
}
