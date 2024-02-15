package com.example.mingkhparser.models.hotwatersupplysystem;

public enum HotWaterSystemType {
    NONE("Нет"),
    INDIVIDUALBOILER("Индивидуальный котел"),
    RINGORWITHLOOPEDINPUTS("Кольцевая или с закольцованными вводами"),
    CENTRAL("Центральное"),
    GASWATERHEATERS("Газовые колонки (ВДГО)"),
    CLOSEDWITHPREPARATIONATTHECENTRALHEATINGSTATION("Закрытая с приготовлением горячей воды на ЦТП"),
    DEADEND("Тупиковая"),
    UNKNOWN("не известен"),
    USHAPED("П-образная"),
    LOWERROUTINGOFHIGHWAYS("с нижней разводкой магистралей");

    private String name;

    HotWaterSystemType(String name) {
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
