package com.example.mingkhparser.models.hotwatersupplysystem;

import com.example.mingkhparser.models.IEnum;
import lombok.Getter;

@Getter
public enum RisersMaterial implements IEnum {
    NONE("Нет"),
    CASTIRON("Чугун"),
    STEEL("Сталь"),
    POLYMER("Полимер"),
    GALVANIZEDSTEEL("Сталь оцинкованная"),
    POLYPROPYLENE("Полипропилен");

    private final String name;

    RisersMaterial(String name) {
        this.name = name;
    }

}
