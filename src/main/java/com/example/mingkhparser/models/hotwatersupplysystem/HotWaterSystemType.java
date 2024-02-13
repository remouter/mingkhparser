package com.example.mingkhparser.models.hotwatersupplysystem;

public enum HotWaterSystemType {
    NONE,
    INDIVIDUALBOILER, //Индивидуальный котел
    RINGORWITHLOOPEDINPUTS, //Кольцевая или с закольцованными вводами
    CENTRAL, //Центральное
    GASWATERHEATERS, //Газовые колонки (ВДГО)
    CLOSEDWITHPREPARATIONATTHECENTRALHEATINGSTATION, //Закрытая с приготовлением горячей воды на ЦТП
    DEADEND, //Тупиковая
    USHAPEDLOWERROUTINGOFHIGHWAYS, //П-образная, с нижней разводкой магистралей
}
