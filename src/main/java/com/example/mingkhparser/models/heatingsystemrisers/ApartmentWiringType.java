package com.example.mingkhparser.models.heatingsystemrisers;

import lombok.Getter;

@Getter
public enum ApartmentWiringType {
    VERTICAL("Вертикальная"),
    HORIZONTAL("Горизонтальная"),
    NONE("Нет");

    private final String name;

    ApartmentWiringType(String name) {
        this.name = name;
    }

}
