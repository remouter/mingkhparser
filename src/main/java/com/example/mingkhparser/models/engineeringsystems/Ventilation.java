package com.example.mingkhparser.models.engineeringsystems;

import com.example.mingkhparser.models.IEnum;
import lombok.Getter;

@Getter
public enum Ventilation implements IEnum {
    SUPPLYANDEXHAUSTVENTILATION("Приточно-вытяжная вентиляция"),
    EXHAUSTVENTILATION("Вытяжная вентиляция"),
    NONE("Отсутствует");

    private final String name;

    Ventilation(String name) {
        this.name = name;
    }

}
