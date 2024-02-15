package com.example.mingkhparser.models.hotwatersupplysystem;

import lombok.Getter;

@Getter
public enum NetworkMaterial {
    NONE("Нет"),
    METALPOLYMER("Металлополимер"),
    CASTIRON("Чугун"),
    STEEL("Сталь"),
    GALVANIZEDSTEEL("Сталь оцинкованная"),
    POLYPROPYLENE("Полипропилен"),
    POLYMER("Полимер"),
    COPPER("Медь");

    private final String name;

    NetworkMaterial(String name) {
        this.name = name;
    }

}
