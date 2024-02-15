package com.example.mingkhparser.models.generalinfo;

import lombok.Getter;

@Getter
public enum HouseCondition {
    SERVICEABLE("Исправный"),
    EMERGENCY("Аварийный");

    private final String name;

    HouseCondition(String name) {
        this.name = name;
    }

}
