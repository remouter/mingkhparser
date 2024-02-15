package com.example.mingkhparser.models.hotwatersupplysystem;

public enum NetworkMaterial {
    NONE("Нет"),
    METALPOLYMER("Металлополимер"),
    CASTIRON("Чугун"),
    STEEL("Сталь"),
    GALVANIZEDSTEEL("Сталь оцинкованная"),
    POLYPROPYLENE("Полипропилен"),
    POLYMER("Полимер"),
    COPPER("Медь");

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
