package com.example.mingkhparser.models.engineeringsystems;

import com.example.mingkhparser.models.IEnum;
import lombok.Getter;

@Getter
public enum ElectricitySupply implements IEnum {
    CENTRAL("Центральное");

    private final String name;

    ElectricitySupply(String name) {
        this.name = name;
    }

}
