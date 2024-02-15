package com.example.mingkhparser.models.drainagesystem;

import lombok.Getter;

@Getter
public enum NetworkMaterial {
    CASTIRON("чугун"),
    NONE("Нет"),
    PLASTIC("пластик"),
    ASBESTOSCEMENT("асбестоцемент");

    private final String name;

    NetworkMaterial(String name) {
        this.name = name;
    }

}
