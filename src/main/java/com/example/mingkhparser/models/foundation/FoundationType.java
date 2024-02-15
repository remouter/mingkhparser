package com.example.mingkhparser.models.foundation;

public enum FoundationType {
    TAPE("Ленточный"),
    REINFORCEDCONCRETELARGEBLOCK("Ж/б"),
    MONOLITHICSTRIPREINFORCEDCONCRETEANDCOLUMNARGRILLAGEONAPILE("Монолитный ленточный железобетонный и столбчатый ростверк по свайному основанию"),
    COLUMNARPILLAR("Столбчатый (столбовой)"),
    NONE("Нет");

    private String name;

    FoundationType(String name) {
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
