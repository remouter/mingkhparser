package com.example.mingkhparser.models.facade;

import com.example.mingkhparser.models.WallMaterial;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@Getter
@Setter
@ToString
public class Facade {
    private Set<WallMaterial> outerWallsMaterials;
    private ExternalInsulationType externalInsulationType;
    private FacadeFinishingMaterial facadeFinishingMaterial;
    private Double physicalDeterioration;
    private Integer lastOverhaulYear;
}
