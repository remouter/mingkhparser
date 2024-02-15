package com.example.mingkhparser.models.roof;

public enum InsulatingLayers {
    //todo enum set
    EXPANDEDCLAYSLAG("Керамзит или шлак"),
    MINERALWOOL("Минеральная вата"),
    NONE("нет"),
    FOAMCONCRETE("Пенобетон");

    private String name;

    InsulatingLayers(String name) {
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
