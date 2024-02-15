package com.example.mingkhparser.models.engineeringsystems;

import lombok.Getter;

@Getter
public enum Ventilation {
    SUPPLYANDEXHAUSTVENTILATION("Приточно-вытяжная вентиляция"),
    EXHAUSTVENTILATION("Вытяжная вентиляция"),
    NONE("Отсутствует");

    private final String name;

    Ventilation(String name) {
        this.name = name;
    }

}
