package com.example.mingkhparser.models.drainagesystem;

import com.example.mingkhparser.models.IEnum;
import lombok.Getter;

@Getter
public enum NetworkMaterial implements IEnum {
    CASTIRON("чугун"),
    NONE("Нет"),
    PLASTIC("пластик"),
    ASBESTOSCEMENT("асбестоцемент");

    private final String name;

    NetworkMaterial(String name) {
        this.name = name;
    }

}
