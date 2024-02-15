package com.example.mingkhparser.models.generalinfo;

import lombok.Getter;

@Getter
public enum EnergyEfficiencyClass {
    NOTASSIGNED("");

    private final String name;

    EnergyEfficiencyClass(String name) {
        this.name = name;
    }

}
