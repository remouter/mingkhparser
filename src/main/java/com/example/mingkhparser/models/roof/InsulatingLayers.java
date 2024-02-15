package com.example.mingkhparser.models.roof;

import lombok.Getter;

@Getter
public enum InsulatingLayers {
    EXPANDEDCLAYSLAG("Керамзит или шлак"),
    MINERALWOOL("Минеральная вата"),
    NONE("нет"),
    FOAMCONCRETE("Пенобетон");

    private final String name;

    InsulatingLayers(String name) {
        this.name = name;
    }
}
