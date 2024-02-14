package com.example.mingkhparser.models.constructionelements;

public enum FloorType {
    REINFORCEDCONCRETESLABS("Перекрытия железобетонные"),
    WOODENUNPLASTERED("Перекрытия деревянные неоштукатуренные"),
    WOODENPLASTERED("Перекрытия деревянные оштукатуренные"),
    WOODENHEATED("Деревянные отепленные"),
    WOODEN("Деревянные"),
    OTHER("Иные"),
    PRECASTCONCRETESLABS("Перекрытия из сборного железобетонного настила"),
    PREFABRICATEDANDMONOLITHICSOLIDSLABS("Перекрытия из сборных и монолитных сплошных плит"),
    FLATREINFORCEDCONCRETE("Плоские железобетонные плиты"),
    NONE("Нет"),
    MIXED("Смешанные");

    private String name;

    FloorType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
