package com.example.mingkhparser.models.heatingsystemrisers;

public enum MaterialType {
    GALVANIZEDSTEEL("Сталь оцинкованная"),
    POLYMER("Полимер"),
    STEEL("Сталь"),
    NONE("Нет"),
    CASTIRON("Чугун"),
    METALPOLYMER("Металлополимер"),
    POLYPROPYLENE("Полипропилен");

    private String name;

    MaterialType(String name) {
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
