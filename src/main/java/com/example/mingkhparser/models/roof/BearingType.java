package com.example.mingkhparser.models.roof;

import com.example.mingkhparser.models.IEnum;
import lombok.Getter;

@Getter
public enum BearingType implements IEnum {
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
