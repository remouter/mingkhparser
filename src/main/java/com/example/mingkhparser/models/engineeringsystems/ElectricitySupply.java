package com.example.mingkhparser.models.engineeringsystems;

public enum ElectricitySupply {
    CENTRAL("Центральное");

    private String name;

    ElectricitySupply(String name) {
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
