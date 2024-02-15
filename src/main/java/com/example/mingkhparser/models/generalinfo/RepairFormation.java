package com.example.mingkhparser.models.generalinfo;

public enum RepairFormation {
    REGIONALOPERATORACCOUNT("На счете регионального оператора"),
    ORGANIZATIONSPECIALACCOUNT("На специальном счете организации"),
    INDEFINED("Не определен");

    private String name;

    RepairFormation(String name) {
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
