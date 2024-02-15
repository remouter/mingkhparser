package com.example.mingkhparser.models.generalinfo;

public enum EnergyEfficiencyClass {
    NOTASSIGNED("");

    private String name;

    EnergyEfficiencyClass(String name) {
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
