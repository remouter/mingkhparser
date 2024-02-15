package com.example.mingkhparser.models.heatingdevices;

public enum HeatingDevicesType {
    RADIATOR("Радиатор"),
    REGISTER("Регистр"),
    NONE("Нет"),
    CONVECTOR("Конвектор");

    private String name;

    HeatingDevicesType(String name) {
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
