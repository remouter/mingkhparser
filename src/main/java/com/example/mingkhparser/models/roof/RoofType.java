package com.example.mingkhparser.models.roof;

public enum RoofType {
    //todo enum set
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
    CORRUGATEDSHEETSSLATE("Волнистые листы, Шиферная"), //Волнистые листы, Шиферная
    ROLLEDONREINFORCEDCONCRETESLABS("Рулонная по железобетонным плитам"),
    GALVANIZEDCORRUGATEDSHEET("Оцинкованный профлист"),
    CORRUGATEDSHEET("Профнастил"),
    NONE("Нет");

    private String name;

    RoofType(String name) {
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
