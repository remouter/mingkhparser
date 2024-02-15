package com.example.mingkhparser.models.windows;

import lombok.Getter;

@Getter
public enum WindowsType {
    PLASTIC("Пластиковые"),
    WOODEN("Деревянные"),
    NONE("нет");

    private final String name;

    WindowsType(String name) {
        this.name = name;
    }
}
