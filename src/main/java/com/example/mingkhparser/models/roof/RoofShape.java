package com.example.mingkhparser.models.roof;

import lombok.Getter;

@Getter
public enum RoofShape {
    FLAT("Плоская"),
    GABLE("Двускатная"),
    SLOPING("Односкатная"),
    HIP("Вальмовая"),
    HIPCOMPLEXSHAPE("Вальмовая сложной формы"),
    HALFHIP("Полувальмовая"),
    TENT("Шатровая"),
    NONE("Нет");

    private final String name;

    RoofShape(String name) {
        this.name = name;
    }
}
