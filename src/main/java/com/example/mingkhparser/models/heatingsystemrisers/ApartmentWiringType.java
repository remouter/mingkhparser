package com.example.mingkhparser.models.heatingsystemrisers;

public enum ApartmentWiringType {
    VERTICAL("Вертикальная"),
    HORIZONTAL("Горизонтальная"),
    NONE("Нет");

    private String name;

    ApartmentWiringType(String name) {
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
