package com.example.mingkhparser.models.heatingsystem;

public enum NetworkMaterial {
    GALVANIZEDSTEEL("Сталь оцинкованная"),
    NONE("Нет"),
    STEEL("Сталь"),
    POLYMER("Полимер"),
    CASTIRON("Чугун"),
    METALPOLYMER("Металлополимер"),
    POLYPROPYLENE("Полипропилен");

    private String name;

    NetworkMaterial(String name) {
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
