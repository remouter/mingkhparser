package com.example.mingkhparser.models.drainagesystem;

public enum NetworkMaterial {
    //todo enum set
    CASTIRON("чугун"),
    NONE("Нет"),
    PLASTIC("пластик"),
    CASTIRONPLASTIC("чугун, пластик"), //чугун, пластик
    ASBESTOSCEMENT("асбестоцемент");

    private String name;

    NetworkMaterial(String name) {
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
