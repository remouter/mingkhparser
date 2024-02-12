package com.example.mingkhparser.models.engineeringsystems;

import com.example.mingkhparser.models.hotwatersupplysystem.HotWaterSystemType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class EngineeringSystems {
    private Ventilation ventilation;
    private WaterDisposal waterDisposal;
    private Boolean guttersSystem;
    private GasSupply gasSupply;
    private HotWaterSystemType hotWaterSystemType;
    private Boolean fireExtinguishingSystem;
    private HeatSupply heatSupply;
    private ColdWaterSupply coldWaterSupply;
    private ElectricitySupply electricitySupply;
    private Integer numberOfEntriesIntoHouse;
}
