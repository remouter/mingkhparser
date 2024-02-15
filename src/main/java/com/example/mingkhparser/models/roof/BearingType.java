package com.example.mingkhparser.models.roof;

public enum BearingType {
    PREFABRICATEDREINFORCEDCONCRETE("Железобетонные сборные (чердачные)"),
    WOODEN("Деревянные"),
    RAFTER("Стропильная"),
    NONE("Нет"),
    COMBINEDPRECASTCONCRETELAMINATEDPANELS("Совмещенные из сборных железобетонных слоистых панелей"),
    REINFORCEDCONCRETESLABS("Ж/б плиты");

    private String name;

    BearingType(String name) {
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
