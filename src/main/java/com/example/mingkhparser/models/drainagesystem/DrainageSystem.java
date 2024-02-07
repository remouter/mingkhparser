package com.example.mingkhparser.models.drainagesystem;

import com.example.mingkhparser.models.drainagesystem.DrainageSystemType;
import com.example.mingkhparser.models.drainagesystem.NetworkMaterial;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DrainageSystem {
    private Integer physicalDeterioration;
    private Integer lastOverhaulYear;
    private DrainageSystemType drainageSystemType;
    private NetworkMaterial networkMaterial;
}
