package com.example.mingkhparser.models.engineeringsystems;

public enum ColdWaterSupply {
    DEADEND("Тупиковая"),
    CENTRAL("Центральное"),
    NONE("Отсутствует"),
    AUTONOMOUS("Автономное");

    private String name;

    ColdWaterSupply(String name) {
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
