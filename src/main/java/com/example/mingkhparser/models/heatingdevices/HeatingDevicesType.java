package com.example.mingkhparser.models.heatingdevices;

import lombok.Getter;

@Getter
public enum HeatingDevicesType {
    RADIATOR("Радиатор"),
    REGISTER("Регистр"),
    NONE("Нет"),
    CONVECTOR("Конвектор");

    private final String name;

    HeatingDevicesType(String name) {
        this.name = name;
    }


}
