package com.example.mingkhparser.models.heatingsystem;

public enum NetworkMaterial {
    //todo enum set
    GALVANIZEDSTEEL("Сталь оцинкованная"),
    NONE("Нет"),
    STEEL("Сталь"),
    POLYMER("Полимер"),
    CASTIRON("Чугун"),
    METALPOLYMER("Металлополимер"),
    POLYMERGALVANIZEDSTEEL("Полимер, Сталь оцинкованная"), //Полимер, Сталь оцинкованная
    POLYPROPYLENE("Полипропилен"),
    STEELPOLYPROPYLENE("Сталь, Полипропилен") //Сталь, Полипропилен
    ;

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
