package com.example.mingkhparser.models.generalinfo;

import com.example.mingkhparser.models.IEnum;
import lombok.Getter;

@Getter
public enum EnergyEfficiencyClass implements IEnum {
    NOTASSIGNED("");

    private final String name;

    EnergyEfficiencyClass(String name) {
        this.name = name;
    }

}
