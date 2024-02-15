package com.example.mingkhparser.models.heatingsystem;

import lombok.Getter;

@Getter
public enum NetworkMaterial {
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
