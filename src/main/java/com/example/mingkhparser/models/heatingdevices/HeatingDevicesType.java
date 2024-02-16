package com.example.mingkhparser.models.heatingdevices;

import com.example.mingkhparser.models.IEnum;
import lombok.Getter;

@Getter
public enum HeatingDevicesType implements IEnum {
    RADIATOR("Радиатор"),
    REGISTER("Регистр"),
    NONE("Нет"),
    CONVECTOR("Конвектор");

    private final String name;

    HeatingDevicesType(String name) {
        this.name = name;
    }


}
