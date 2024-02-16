package com.example.mingkhparser.models.constructionelements;

import com.example.mingkhparser.models.IEnum;
import lombok.Getter;

@Getter
public enum Foundation implements IEnum {
    TAPE("Ленточный"),
    NONE("Нет"),
    REINFORCEDCONCRETELARGEBLOCK("Ж/б"),
    MONOLITHICSTRIPREINFORCEDCONCRETEANDCOLUMNARGRILLAGEONAPILE("Монолитный ленточный железобетонный и столбчатый ростверк по свайному основанию"),
    COLUMNARPILLAR("Столбчатый (столбовой)"),
    OTHER("Иной");

    private final String name;

    Foundation(String name) {
        this.name = name;
    }

}
