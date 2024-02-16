package com.example.mingkhparser.models.windows;

import com.example.mingkhparser.models.IEnum;
import lombok.Getter;

@Getter
public enum WindowsType implements IEnum {
    PLASTIC("Пластиковые"),
    WOODEN("Деревянные"),
    NONE("нет");

    private final String name;

    WindowsType(String name) {
        this.name = name;
    }
}
