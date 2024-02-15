package com.example.mingkhparser.models.engineeringsystems;

import lombok.Getter;

@Getter
public enum GuttersSystem {
    NONE("Отсутствует"),
    INNER("Внутренние водостоки"),
    EXTERNAL("Наружные водостоки");

    private final String name;

    GuttersSystem(String name) {
        this.name = name;
    }

}
