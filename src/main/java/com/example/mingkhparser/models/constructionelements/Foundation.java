package com.example.mingkhparser.models.constructionelements;

public enum Foundation {
    TAPE("Ленточный"),
    NONE("Нет"),
    REINFORCEDCONCRETELARGEBLOCK("Ж/б"),
    MONOLITHICSTRIPREINFORCEDCONCRETEANDCOLUMNARGRILLAGEONAPILE("Монолитный ленточный железобетонный и столбчатый ростверк по свайному основанию"),
    COLUMNARPILLAR("Столбчатый (столбовой)"),
    OTHER("Иной");

    private String name;

    Foundation(String name) {
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
