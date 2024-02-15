package com.example.mingkhparser.models.drainagesystem;

public enum NetworkMaterial {
    CASTIRON("чугун"),
    NONE("Нет"),
    PLASTIC("пластик"),
    ASBESTOSCEMENT("асбестоцемент");

    private String name;

    NetworkMaterial(String name) {
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
