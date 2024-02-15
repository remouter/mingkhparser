package com.example.mingkhparser.models.engineeringsystems;

public enum WaterDisposal {
    CENTRAL("Центральное"),
    NONE("Отсутствует"),
    CESSPOOL("Выгребная яма"),
    AUTONOMOUS("Автономное"),
    LOCALSEWERAGESEPTIC("Локальная канализация (септик)");

    private String name;

    WaterDisposal(String name) {
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
