package com.example.mingkhparser.models.gassupplysystem;

import com.example.mingkhparser.models.gassupplysystem.GasSupplySystemType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GasSupplySystem {
    private Integer lastOverhaulYear;
    private GasSupplySystemType gasSupplySystemType;
    private Integer gasSupplySystemInletsNumber;
}
