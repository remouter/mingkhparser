package com.example.mingkhparser.models.generalinfo;

import lombok.Getter;

@Getter
public enum HouseType {
    MANYAPPARTMENTS("Многоквартирный дом");

    private final String name;

    HouseType(String name) {
        this.name = name;
    }

}
