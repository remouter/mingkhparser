package com.example.mingkhparser.models.drainagesystem;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DrainageSystem {
    private Integer physicalDeterioration;
    private Integer lastOverhaulYear;
    private DrainageSystemType drainageSystemType;
    private NetworkMaterial networkMaterial;
}
