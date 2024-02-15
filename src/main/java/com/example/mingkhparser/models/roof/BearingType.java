package com.example.mingkhparser.models.roof;

import lombok.Getter;

@Getter
public enum BearingType {
    PREFABRICATEDREINFORCEDCONCRETE("Железобетонные сборные (чердачные)"),
    WOODEN("Деревянные"),
    RAFTER("Стропильная"),
    NONE("Нет"),
    COMBINEDPRECASTCONCRETELAMINATEDPANELS("Совмещенные из сборных железобетонных слоистых панелей"),
    REINFORCEDCONCRETESLABS("Ж/б плиты");

    private final String name;

    BearingType(String name) {
        this.name = name;
    }
}
