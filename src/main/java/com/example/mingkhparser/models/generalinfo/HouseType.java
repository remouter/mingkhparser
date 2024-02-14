package com.example.mingkhparser.models.generalinfo;

public enum HouseType {
    MANYAPPARTMENTS("Многоквартирный дом");

    private String name;

    HouseType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
