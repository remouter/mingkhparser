package com.example.mingkhparser.models.engineeringsystems;

import com.example.mingkhparser.models.hotwatersupplysystem.HotWaterSystemType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@Getter
@Setter
@ToString
public class EngineeringSystems {
    private Ventilation ventilation;
    private WaterDisposal waterDisposal;
    private GuttersSystem guttersSystem;
    private GasSupply gasSupply;
    private Set<HotWaterSystemType> hotWaterSystemTypes;
    private Boolean fireExtinguishingSystem;
    private HeatSupply heatSupply;
    private ColdWaterSupply coldWaterSupply;
    private ElectricitySupply electricitySupply;
    private Integer numberOfEntriesIntoHouse;
}
