package com.example.mingkhparser.models.foundation;

import lombok.Getter;

@Getter
public enum FoundationType {
    TAPE("Ленточный"),
    REINFORCEDCONCRETELARGEBLOCK("Ж/б"),
    MONOLITHICSTRIPREINFORCEDCONCRETEANDCOLUMNARGRILLAGEONAPILE("Монолитный ленточный железобетонный и столбчатый ростверк по свайному основанию"),
    COLUMNARPILLAR("Столбчатый (столбовой)"),
    NONE("Нет");

    private final String name;

    FoundationType(String name) {
        this.name = name;
    }
}
