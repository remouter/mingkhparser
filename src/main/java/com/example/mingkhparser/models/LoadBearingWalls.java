package com.example.mingkhparser.models;

public enum LoadBearingWalls {
    BRICK("Кирпич"),
    WOODENFRAME("Стены деревянные каркасные"),
    LOGSTIMBER("Стены рубленные из бревен и брусчатые"),
    WOODEN("Деревянные"),
    REINFORCEDCONCRETE("Железобетонные панели"),
    MIXED("Смешанные"),
    PANEL("Панельные"),
    LARGEBLOCKSANDSINGLELAYERLOADBEARINGPANELS("Стены из крупноразмерных блоков и однослойных несущих панелей"),
    PREFABRICATEDPANELS("сборно-щитовые"),
    REINFORCEDCONCRETEBLOCK("Ж/б блоки"),
    REINFORCEDCONCRETESLABS("Железобетонные плиты");

    private String name;

    LoadBearingWalls(String name) {
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
