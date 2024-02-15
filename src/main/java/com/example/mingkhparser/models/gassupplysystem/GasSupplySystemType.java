package com.example.mingkhparser.models.gassupplysystem;

import lombok.Getter;

@Getter
public enum GasSupplySystemType {
    CENTRAL("центральное"),
    NONE("нет"),
    LOWPRESSUREGASPIPELINEROOFBOILERROOM("Газопровод низкого давления – подача природного газа в крышную котельную"),
    BOTTLEDGAS("баллонный газ");

    private final String name;

    GasSupplySystemType(String name) {
        this.name = name;
    }

}
