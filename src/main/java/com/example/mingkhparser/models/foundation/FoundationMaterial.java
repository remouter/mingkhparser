package com.example.mingkhparser.models.foundation;

import com.example.mingkhparser.models.IEnum;
import lombok.Getter;

@Getter
public enum FoundationMaterial implements IEnum {
    REINFORCEDCONCRETEBLOCKS("Железобетонные блоки"),
    CERAMICBRICK("Кирпич керамический"),
    REINFORCEDCONCRETESMONOLITHIC("Монолитный железобетон"),
    PRECASTREINFORCEDCONCRETE("Сборный железобетон"),
    NONE("Нет"),
    REINFORCEDCONCRETE("Железобетон"),
    BRICK("Кирпич"),
    RUBBLECONCRETE("Бутобетон"),
    RUBBLESTONE("Бутовый камень"),
    CONCRETE("Бетон");

    private final String name;

    FoundationMaterial(String name) {
        this.name = name;
    }

}
