package com.example.mingkhparser.models.hotwatersupplysystem;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class HotWaterSupplySystem {
    private HotWaterSystemType hotWaterSystemType;
    private Double physicalDeterioration;
    private NetworkMaterial networkMaterial;
    private NetworkThermalInsulationMaterial networkThermalInsulationMaterial;
    private RisersMaterial risersMaterial;

}
