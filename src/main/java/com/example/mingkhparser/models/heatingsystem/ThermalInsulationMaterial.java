package com.example.mingkhparser.models.heatingsystem;

public enum ThermalInsulationMaterial {
    FOAMEDPOLYETHYLENE("Вспененный полиэтилен (энергофлекс)"),
    NONE("Нет"),
    MINERALWOOLCOATEDWITHALUMINUMFOIL("Минеральная вата с покрытием из алюминиевой фольги"),
    MINERALWOOLCOATEDWITHGALVANIZEDSTEEL("Минеральная вата с покрытием из оцинкованной стали"),
    MINERALWOOLCOATED("Минеральная вата с покрытием"),
    ASBESTOSUNDERWOODENBASE("Асбест под деревянной основой (устар.)"),
    POLYURETHANEFOAMSPRAYING("Пенополиуретановое напыление"),
    POLYMER("Полимер"),
    GALVANIZEDSTEEL("Сталь оцинкованная");

    private String name;

    ThermalInsulationMaterial(String name) {
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
