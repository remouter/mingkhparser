package com.example.mingkhparser.models.gassupplysystem;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GasSupplySystem {
    private Integer lastOverhaulYear;
    private GasSupplySystemType gasSupplySystemType;
    private Integer gasSupplySystemInletsNumber;
}
