package com.example.mingkhparser.models.hotwatersupplysystem;

import lombok.Getter;

@Getter
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

    private final String name;

    HotWaterSystemType(String name) {
        this.name = name;
    }

}
