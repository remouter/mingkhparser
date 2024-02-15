package com.example.mingkhparser.models.heatingsystemrisers;

public enum MaterialType {
    //todo enums set
    GALVANIZEDSTEEL("Сталь оцинкованная"),
    POLYMER("Полимер"),
    STEEL("Сталь"),
    NONE("Нет"),
    CASTIRON("Чугун"),
    METALPOLYMER("Металлополимер"),
    NONESTEEL("Нет, Сталь черная"), //Нет, Сталь черная
    STEELPOLYPROPYLENE("Сталь, Полипропилен"), //Сталь, Полипропилен
    POLYMERGALVANIZEDSTEEL("Полимер, Сталь оцинкованная"), //Полимер, Сталь оцинкованная
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
