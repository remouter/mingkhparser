package com.example.mingkhparser.models.facade;

public enum ExternalInsulationType {
    NONE("Нет"),
    INSULATIONWITHAPROTECTIVEPLASTERLAYER("Утепление с защитным штукатурным слоем"),
    MINERALWOOL("Минвата"),
    HINGEDVENTILATEDFACADE("Навесной вентилируемый фасад");

    private String name;

    ExternalInsulationType(String name) {
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
