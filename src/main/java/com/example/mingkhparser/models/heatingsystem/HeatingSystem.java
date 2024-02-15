package com.example.mingkhparser.models.heatingsystem;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@Getter
@Setter
@ToString
public class HeatingSystem {
    private Double physicalDeterioration;
    private Set<NetworkMaterial> networkMaterials;
    private Set<ThermalInsulationMaterial> thermalInsulationMaterials;
}
