package com.example.mingkhparser.models.facade;

import com.example.mingkhparser.models.IEnum;
import lombok.Getter;

@Getter
public enum ExternalInsulationType implements IEnum {
    NONE("Нет"),
    INSULATIONWITHAPROTECTIVEPLASTERLAYER("Утепление с защитным штукатурным слоем"),
    MINERALWOOL("Минвата"),
    HINGEDVENTILATEDFACADE("Навесной вентилируемый фасад");

    private final String name;

    ExternalInsulationType(String name) {
        this.name = name;
    }
}
