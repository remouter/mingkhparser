package com.example.mingkhparser.models.heatingsystem;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HeatingSystem {
    private Integer physicalDeterioration;
    private NetworkMaterial networkMaterial;
    private ThermalInsulationMaterial thermalInsulationMaterial;
}
