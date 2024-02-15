package com.example.mingkhparser.models.coldwatersupplysystemrisers;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@Getter
@Setter
@ToString
public class ColdWaterSupplySystemRisers {
    private Integer physicalDeterioration;
    private Set<NetworkMaterial> networkMaterials;
}
