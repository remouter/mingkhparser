package com.example.mingkhparser.models.roof;

import com.example.mingkhparser.models.IEnum;
import lombok.Getter;

@Getter
public enum RoofShape implements IEnum {
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
