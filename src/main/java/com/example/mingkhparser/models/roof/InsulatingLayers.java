package com.example.mingkhparser.models.roof;

import com.example.mingkhparser.models.IEnum;
import lombok.Getter;

@Getter
public enum InsulatingLayers implements IEnum {
    EXPANDEDCLAYSLAG("Керамзит или шлак"),
    MINERALWOOL("Минеральная вата"),
    NONE("нет"),
    FOAMCONCRETE("Пенобетон");

    private final String name;

    InsulatingLayers(String name) {
        this.name = name;
    }
}
