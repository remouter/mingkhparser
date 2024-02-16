package com.example.mingkhparser.models.generalinfo;

import com.example.mingkhparser.models.IEnum;
import lombok.Getter;

@Getter
public enum RepairFormation implements IEnum {
    REGIONALOPERATORACCOUNT("На счете регионального оператора"),
    ORGANIZATIONSPECIALACCOUNT("На специальном счете организации"),
    INDEFINED("Не определен");

    private final String name;

    RepairFormation(String name) {
        this.name = name;
    }

}
