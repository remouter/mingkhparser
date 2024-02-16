package com.example.mingkhparser.models.roof;

import com.example.mingkhparser.models.IEnum;
import lombok.Getter;

@Getter
public enum RoofType implements IEnum {
    ROLL("Рулонная"),
    CORRUGATEDSHEETS("Волнистые листы"),
    SLATE("Шиферная"),
    METALSEAM("Металлическая фальцевая"),
    IRON("Железо"),
    STEELMETAL("Стальная (металлическая)"),
    IRONONWOODSHEATHING("Железо по деревянной обрешетке"),
    METALPROFILEDSHEET("Металлический профлист"),
    SOFT("Мягкая"),
    METALWAVY("Металлическая волнистая"),
    RUBEROID("Рубероид"),
    ROLLEDONREINFORCEDCONCRETESLABS("Рулонная по железобетонным плитам"),
    GALVANIZEDCORRUGATEDSHEET("Оцинкованный профлист"),
    CORRUGATEDSHEET("Профнастил"),
    NONE("Нет");

    private final String name;

    RoofType(String name) {
        this.name = name;
    }
}
