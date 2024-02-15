package com.example.mingkhparser.models.roof;

public enum RoofShape {
    FLAT("Плоская"),
    GABLE("Двускатная"),
    SLOPING("Односкатная"),
    HIP("Вальмовая"),
    HIPCOMPLEXSHAPE("Вальмовая сложной формы"),
    HALFHIP("Полувальмовая"),
    TENT("Шатровая"),
    NONE("Нет");

    private String name;

    RoofShape(String name) {
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
