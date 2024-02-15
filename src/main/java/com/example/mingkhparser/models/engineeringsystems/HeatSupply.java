package com.example.mingkhparser.models.engineeringsystems;

public enum HeatSupply {
    CENTRAL("Центральное"),
    NONE("Нет"),
    BOILER("Квартирное отопление (квартирный котел)"),
    HOUSEBOILER("Домовая котельная"),
    STOVE("Печное"),
    GEYSER("Газовая колонка");

    private String name;

    HeatSupply(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


    @Override
    public String toString() {
        return name;
    }
}
