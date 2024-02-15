package com.example.mingkhparser.models.windows;

public enum WindowsType {
    PLASTIC("Пластиковые"),
    WOODEN("Деревянные"),
    NONE("нет");

    private String name;

    WindowsType(String name) {
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
