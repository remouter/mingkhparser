package com.example.mingkhparser.models.heatingsystem;

import com.example.mingkhparser.models.IEnum;
import lombok.Getter;

@Getter
public enum ThermalInsulationMaterial implements IEnum {
    FOAMEDPOLYETHYLENE("Вспененный полиэтилен (энергофлекс)"),
    NONE("Нет"),
    MINERALWOOLCOATEDWITHALUMINUMFOIL("Минеральная вата с покрытием из алюминиевой фольги"),
    MINERALWOOLCOATEDWITHGALVANIZEDSTEEL("Минеральная вата с покрытием из оцинкованной стали"),
    MINERALWOOLCOATED("Минеральная вата с покрытием"),
    ASBESTOSUNDERWOODENBASE("Асбест под деревянной основой (устар.)"),
    POLYURETHANEFOAMSPRAYING("Пенополиуретановое напыление"),
    POLYMER("Полимер"),
    GALVANIZEDSTEEL("Сталь оцинкованная");

    private final String name;

    ThermalInsulationMaterial(String name) {
        this.name = name;
    }

}
