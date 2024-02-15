package com.example.mingkhparser.models.engineeringsystems;

public enum GasSupply {
    CENTRAL("Центральное"),
    NONE("Отсутствует"),
    LOWPRESSUREGASPIPELINEROOFBOILERROOM("Газопровод низкого давления – подача природного газа в крышную котельную"),
    BOTTLEDGAS("баллонный газ"),
    AUTONOMOUS("Автономное");

    private String name;

    GasSupply(String name) {
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
