package com.example.mingkhparser.models.heatingsystemrisers;

import com.example.mingkhparser.models.IEnum;
import lombok.Getter;

@Getter
public enum MaterialType implements IEnum {
    GALVANIZEDSTEEL("Сталь оцинкованная"),
    POLYMER("Полимер"),
    STEEL("Сталь"),
    NONE("Нет"),
    CASTIRON("Чугун"),
    METALPOLYMER("Металлополимер"),
    POLYPROPYLENE("Полипропилен");

    private final String name;

    MaterialType(String name) {
        this.name = name;
    }

}
