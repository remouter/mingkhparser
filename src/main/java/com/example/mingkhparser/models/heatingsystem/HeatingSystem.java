package com.example.mingkhparser.models.heatingsystem;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class HeatingSystem {
    private Double physicalDeterioration;
    private NetworkMaterial networkMaterial;
    private ThermalInsulationMaterial thermalInsulationMaterial;
}
