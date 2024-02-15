package com.example.mingkhparser.models.generalinfo;

public enum HouseCondition {
    SERVICEABLE("Исправный"),
    EMERGENCY("Аварийный");

    private String name;

    HouseCondition(String name) {
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
