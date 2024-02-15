package com.example.mingkhparser.models.hotwatersupplysystem;

public enum RisersMaterial {
    NONE("Нет"),
    CASTIRON("Чугун"),
    STEEL("Сталь"),
    POLYMER("Полимер"),
    GALVANIZEDSTEEL("Сталь оцинкованная"),
    POLYPROPYLENE("Полипропилен");

    private String name;

    RisersMaterial(String name) {
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
