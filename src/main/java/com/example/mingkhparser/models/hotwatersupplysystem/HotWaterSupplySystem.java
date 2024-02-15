package com.example.mingkhparser.models.hotwatersupplysystem;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@Getter
@Setter
@ToString
public class HotWaterSupplySystem {
    private Set<HotWaterSystemType> hotWaterSystemTypes;
    private Double physicalDeterioration;
    private Set<NetworkMaterial> networkMaterials;
    private NetworkThermalInsulationMaterial networkThermalInsulationMaterial;
    private Set<RisersMaterial> risersMaterials;
}
