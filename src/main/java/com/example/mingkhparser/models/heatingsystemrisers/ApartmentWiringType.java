package com.example.mingkhparser.models.heatingsystemrisers;

import com.example.mingkhparser.models.IEnum;
import lombok.Getter;

@Getter
public enum ApartmentWiringType implements IEnum {
    VERTICAL("Вертикальная"),
    HORIZONTAL("Горизонтальная"),
    NONE("Нет");

    private final String name;

    ApartmentWiringType(String name) {
        this.name = name;
    }

}
