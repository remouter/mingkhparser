package com.example.mingkhparser.models.generalinfo;

import lombok.Getter;

@Getter
public enum RepairFormation {
    REGIONALOPERATORACCOUNT("На счете регионального оператора"),
    ORGANIZATIONSPECIALACCOUNT("На специальном счете организации"),
    INDEFINED("Не определен");

    private final String name;

    RepairFormation(String name) {
        this.name = name;
    }

}
