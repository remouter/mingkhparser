package com.example.mingkhparser.models.hotwatersupplysystem;

import lombok.Getter;

@Getter
public enum RisersMaterial {
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
