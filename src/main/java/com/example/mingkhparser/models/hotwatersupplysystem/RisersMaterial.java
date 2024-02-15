package com.example.mingkhparser.models.hotwatersupplysystem;

public enum RisersMaterial {
    //todo set of enums
    NONE("Нет"),
    CASTIRON("Чугун"),
    STEEL("Сталь"),
    STEELPOLYPROPYLENE("Сталь, Полипропилен"), //Сталь, Полипропилен
    POLYMER("Полимер"),
    POLYMERGALVANIZEDSTEEL("Полимер, Сталь оцинкованная"), //Полимер, Сталь оцинкованная
    POLYMERCASTIRON("Полимер, Чугун"), //Полимер, Чугун
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
