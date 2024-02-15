package com.example.mingkhparser.models.hotwatersupplysystem;

public enum NetworkThermalInsulationMaterial {
    NONE("Нет"),
    MINERALWOOLCOATEDWITHALUMINUMFOIL("Минеральная вата с покрытием из алюминиевой фольги"),
    FOAMEDPOLYETHYLENE("Вспененный полиэтилен (энергофлекс)"),
    MINERALWOOLCOATED("Минеральная вата с покрытием");

    private String name;

    NetworkThermalInsulationMaterial(String name) {
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
