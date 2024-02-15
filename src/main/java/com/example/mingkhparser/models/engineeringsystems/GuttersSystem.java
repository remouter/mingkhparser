package com.example.mingkhparser.models.engineeringsystems;

public enum GuttersSystem {
    NONE("Отсутствует"),
    INNER("Внутренние водостоки"),
    EXTERNAL("Наружные водостоки");

    private String name;

    GuttersSystem(String name) {
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
