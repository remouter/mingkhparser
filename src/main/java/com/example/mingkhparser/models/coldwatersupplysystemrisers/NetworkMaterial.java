package com.example.mingkhparser.models.coldwatersupplysystemrisers;

public enum NetworkMaterial {
    //todo enums set
    GALVANIZEDSTEEL("Сталь оцинкованная"),
    POLYMER("Полимер"),
    NONE("Нет"),
    STEEL("Сталь"),
    METALPOLYMER("Металлополимер"),
    CASTIRON("Чугун"),
    BLACKSTEEL("Сталь черная"), //Сталь черная
    POLYMERGALVANIZEDSTEEL("Сталь оцинкованная, Полимер"), //Полимер, Сталь оцинкованная
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
