package com.example.mingkhparser.models.coldwatersystem;

import lombok.Getter;

@Getter
public enum NetworkMaterial {
    GALVANIZEDSTEEL("Сталь оцинкованная"),
    POLYMER("Полимер"),
    STEEL("Сталь"),
    NONE("Нет"),
    METALPOLYMER("Металлополимер"),
    CASTIRON("Чугун"),
    BLACKSTEEL("Сталь черная"),
    POLYPROPYLENE("Полипропилен"),
    POLYETHYLENE("Полиэтилен");

    private final String name;

    NetworkMaterial(String name) {
        this.name = name;
    }

}
