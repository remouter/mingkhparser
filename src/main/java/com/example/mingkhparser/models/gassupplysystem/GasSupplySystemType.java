package com.example.mingkhparser.models.gassupplysystem;

import com.example.mingkhparser.models.IEnum;
import lombok.Getter;

@Getter
public enum GasSupplySystemType implements IEnum {
    CENTRAL("центральное"),
    NONE("нет"),
    LOWPRESSUREGASPIPELINEROOFBOILERROOM("Газопровод низкого давления – подача природного газа в крышную котельную"),
    BOTTLEDGAS("баллонный газ");

    private final String name;

    GasSupplySystemType(String name) {
        this.name = name;
    }

}
