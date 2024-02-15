package com.example.mingkhparser.models.engineeringsystems;

public enum Ventilation {
    SUPPLYANDEXHAUSTVENTILATION("Приточно-вытяжная вентиляция"),
    EXHAUSTVENTILATION("Вытяжная вентиляция"),
    NONE("Отсутствует");

    private String name;

    Ventilation(String name) {
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
