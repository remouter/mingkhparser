package com.example.mingkhparser.models.generalinfo;

public enum MaterialType {
    BRICK("Кирпичный"),
    NONE("нет"),
    HOUSERESIDENTIAL("Жилой дом"),
    UNKNOWN("информация отсутствует"),
    PANEL("Панельный"),
    PROJECT0613("проект на строительство 06/13-с-ПЗ"),
    WOODEN("Деревянные"),
    INDIVIDUAL("индивидуальный"),
    BLOCKOFFLATS("многоквартирный дом блокированной застройки"),
    TWOBLOCKOFFLATS("двухквартирный жилой дом блокированной застройки");

    private String name;

    MaterialType(String name) {
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
