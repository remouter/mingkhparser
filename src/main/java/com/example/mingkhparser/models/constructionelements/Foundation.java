package com.example.mingkhparser.models.constructionelements;

import lombok.Getter;

@Getter
public enum Foundation {
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
