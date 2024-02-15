package com.example.mingkhparser.models.coldwatersupplysystemrisers;

public enum NetworkMaterial {
    GALVANIZEDSTEEL("Сталь оцинкованная"),
    POLYMER("Полимер"),
    NONE("Нет"),
    STEEL("Сталь"),
    METALPOLYMER("Металлополимер"),
    CASTIRON("Чугун"),
    BLACKSTEEL("Сталь черная"),
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
