package com.example.mingkhparser.models.heatingsystem;

import com.example.mingkhparser.models.IEnum;
import lombok.Getter;

@Getter
public enum NetworkMaterial implements IEnum {
    GALVANIZEDSTEEL("Сталь оцинкованная"),
    NONE("Нет"),
    STEEL("Сталь"),
    POLYMER("Полимер"),
    CASTIRON("Чугун"),
    METALPOLYMER("Металлополимер"),
    POLYPROPYLENE("Полипропилен");

    private final String name;

    NetworkMaterial(String name) {
        this.name = name;
    }

}
