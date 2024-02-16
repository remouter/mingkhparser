package com.example.mingkhparser.models.generalinfo;

import com.example.mingkhparser.models.IEnum;
import lombok.Getter;

@Getter
public enum MaterialType implements IEnum {
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

    private final String name;

    MaterialType(String name) {
        this.name = name;
    }
}
