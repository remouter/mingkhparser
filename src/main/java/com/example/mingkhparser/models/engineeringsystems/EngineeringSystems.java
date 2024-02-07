package com.example.mingkhparser.models.engineeringsystems;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EngineeringSystems {
    private Ventilation ventilation;
    private WaterDisposal waterDisposal;
    private Boolean guttersSystem;
    private GasSupply gasSupply;
    private Boolean hotWaterSupply;
    private Boolean fireExtinguishingSystem;
    private HeatSupply heatSupply;
    private ColdWaterSupply coldWaterSupply;
    private ElectricitySupply electricitySupply;
    private Integer numberOfEntriesIntoHouse;
}
