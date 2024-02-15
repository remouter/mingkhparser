package com.example.mingkhparser.models.coldwatersupplysystemrisers;

import lombok.Getter;

@Getter
public enum NetworkMaterial {
    GALVANIZEDSTEEL("Сталь оцинкованная"),
    POLYMER("Полимер"),
    NONE("Нет"),
    STEEL("Сталь"),
    METALPOLYMER("Металлополимер"),
    CASTIRON("Чугун"),
    BLACKSTEEL("Сталь черная"),
    POLYPROPYLENE("Полипропилен");

    private final String name;

    NetworkMaterial(String name) {
        this.name = name;
    }

}
