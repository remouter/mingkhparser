package com.example.mingkhparser.models.engineeringsystems;

import lombok.Getter;

@Getter
public enum ElectricitySupply {
    CENTRAL("Центральное");

    private final String name;

    ElectricitySupply(String name) {
        this.name = name;
    }

}
