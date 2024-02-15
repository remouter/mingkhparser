package com.example.mingkhparser.models.roof;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@Getter
@Setter
@ToString
public class Roof {
    private RoofShape roofShape;
    private Set<InsulatingLayers> insulatingLayers;
    private Set<BearingType> bearingTypes;
    private Set<RoofType> roofTypes;
    private Integer physicalDeterioration;
    private Integer lastOverhaulYear;
}
