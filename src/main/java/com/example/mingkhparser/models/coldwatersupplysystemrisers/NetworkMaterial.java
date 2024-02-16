package com.example.mingkhparser.models.coldwatersupplysystemrisers;

import com.example.mingkhparser.models.IEnum;
import lombok.Getter;

@Getter
public enum NetworkMaterial implements IEnum {
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
