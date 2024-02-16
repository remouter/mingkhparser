package com.example.mingkhparser.models.engineeringsystems;

import com.example.mingkhparser.models.IEnum;
import lombok.Getter;

@Getter
public enum GuttersSystem implements IEnum {
    NONE("Отсутствует"),
    INNER("Внутренние водостоки"),
    EXTERNAL("Наружные водостоки");

    private final String name;

    GuttersSystem(String name) {
        this.name = name;
    }

}
