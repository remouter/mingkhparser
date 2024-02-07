package com.example.mingkhparser.models.facade;

import com.example.mingkhparser.models.WallMaterial;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Facade {
    private WallMaterial outerWallsMaterial;
    private ExternalInsulationType externalInsulationType;
    private FacadeFinishingMaterial facadeFinishingMaterial;
    private Integer physicalDeterioration;
    private Integer lastOverhaulYear;
}
