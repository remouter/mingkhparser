package com.example.mingkhparser.models.hotwatersupplysystem;

import com.example.mingkhparser.models.hotwatersupplysystem.HotWaterSystemType;
import com.example.mingkhparser.models.hotwatersupplysystem.NetworkMaterial;
import com.example.mingkhparser.models.hotwatersupplysystem.NetworkThermalInsulationMaterial;
import com.example.mingkhparser.models.hotwatersupplysystem.RisersMaterial;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HotWaterSupplySystem {
    private HotWaterSystemType hotWaterSystemType;
    private NetworkMaterial networkMaterial;
    private NetworkThermalInsulationMaterial networkThermalInsulationMaterial;
    private RisersMaterial risersMaterial;

}
