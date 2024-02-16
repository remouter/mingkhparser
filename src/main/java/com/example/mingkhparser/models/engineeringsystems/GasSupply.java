package com.example.mingkhparser.models.engineeringsystems;

import com.example.mingkhparser.models.IEnum;
import lombok.Getter;

@Getter
public enum GasSupply implements IEnum {
    CENTRAL("Центральное"),
    NONE("Отсутствует"),
    LOWPRESSUREGASPIPELINEROOFBOILERROOM("Газопровод низкого давления – подача природного газа в крышную котельную"),
    BOTTLEDGAS("баллонный газ"),
    AUTONOMOUS("Автономное");

    private final String name;

    GasSupply(String name) {
        this.name = name;
    }

}
