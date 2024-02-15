package com.example.mingkhparser.models.engineeringsystems;

import lombok.Getter;

@Getter
public enum HeatSupply {
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
