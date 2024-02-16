package com.example.mingkhparser.models.engineeringsystems;

import com.example.mingkhparser.models.IEnum;
import lombok.Getter;

@Getter
public enum HeatSupply implements IEnum {
    CENTRAL("Центральное"),
    NONE("Нет"),
    BOILER("Квартирное отопление (квартирный котел)"),
    HOUSEBOILER("Домовая котельная"),
    STOVE("Печное"),
    GEYSER("Газовая колонка");

    private final String name;

    HeatSupply(String name) {
        this.name = name;
    }

}
