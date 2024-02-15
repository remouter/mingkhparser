package com.example.mingkhparser.models.coldwatersystem;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@Getter
@Setter
@ToString
public class ColdWaterSystem {
    private Double physicalDeterioration;
    private Set<NetworkMaterial> networkMaterials;
}
