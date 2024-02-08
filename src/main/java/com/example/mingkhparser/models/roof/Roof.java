package com.example.mingkhparser.models.roof;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Roof {
    private RoofShape roofShape;
    private InsulatingLayers insulatingLayers;
    private BearingType bearingType;
    private RoofType roofType;
    private Integer physicalDeterioration;
    private Integer lastOverhaulYear;
}
