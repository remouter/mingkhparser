package com.example.mingkhparser.models.coldwatersystem;

import com.example.mingkhparser.models.IEnum;
import lombok.Getter;

@Getter
public enum NetworkMaterial implements IEnum {
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
