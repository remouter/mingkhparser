package com.example.mingkhparser.models;

public enum WallMaterial implements IEnum{
    BRICK("Стены кирпичные"),
    WOODENFRAME("Стены деревянные каркасные"),
    LOGSTIMBER("Стены рубленные из бревен и брусчатые"),
    WOODEN("Стены деревянные"),
    REINFORCEDCONCRETE("Железобетонные панели"),
    MIXED("Смешанные"),
    PANEL("Стены панельные"),
    LAMINATEDREINFORCEDCONCRETEPANELS("Железобетонные плиты"),
    LARGEBLOCKSANDSINGLELAYERLOADBEARINGPANELS("Стены из крупноразмерных блоков и однослойных несущих панелей"),
    PREFABRICATEDPANELS("сборно-щитовые"),
    FRAMEFILL("Каркасно-засыпные"),
    ARBOLITEPANELS("Арболитовые панели"),
    NULL("null"),
    GYPSOLITIC("Гипсолитовые"),
    REINFORCEDCONCRETEBLOCK("Ж/б блоки");

    private String name;

    WallMaterial(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
