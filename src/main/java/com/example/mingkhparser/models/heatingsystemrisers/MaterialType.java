package com.example.mingkhparser.models.heatingsystemrisers;

import lombok.Getter;

@Getter
public enum MaterialType {
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
