package com.example.mingkhparser.models.facade;

import com.example.mingkhparser.models.WallMaterial;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Facade {
    private WallMaterial outerWallsMaterial;
    private ExternalInsulationType externalInsulationType;
    private FacadeFinishingMaterial facadeFinishingMaterial;
    private Integer physicalDeterioration;
    private Integer lastOverhaulYear;
}
