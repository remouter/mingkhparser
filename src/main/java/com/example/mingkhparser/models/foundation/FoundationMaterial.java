package com.example.mingkhparser.models.foundation;

public enum FoundationMaterial {
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

    private String name;

    FoundationMaterial(String name) {
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
