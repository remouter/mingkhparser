package com.example.mingkhparser.models.hotwatersupplysystem;

import com.example.mingkhparser.models.IEnum;
import lombok.Getter;

@Getter
public enum NetworkThermalInsulationMaterial implements IEnum {
    NONE("Нет"),
    MINERALWOOLCOATEDWITHALUMINUMFOIL("Минеральная вата с покрытием из алюминиевой фольги"),
    FOAMEDPOLYETHYLENE("Вспененный полиэтилен (энергофлекс)"),
    MINERALWOOLCOATED("Минеральная вата с покрытием");

    private final String name;

    NetworkThermalInsulationMaterial(String name) {
        this.name = name;
    }
}
