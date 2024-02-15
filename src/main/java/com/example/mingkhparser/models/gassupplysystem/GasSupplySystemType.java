package com.example.mingkhparser.models.gassupplysystem;

public enum GasSupplySystemType {
    CENTRAL("центральное"),
    NONE("нет"),
    LOWPRESSUREGASPIPELINEROOFBOILERROOM("Газопровод низкого давления – подача природного газа в крышную котельную"),
    BOTTLEDGAS("баллонный газ");

    private String name;

    GasSupplySystemType(String name) {
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
