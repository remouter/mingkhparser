package com.example.mingkhparser.models.heatingsystemrisers;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class HeatingSystemRisers {
    private Integer physicalDeterioration;
    private ApartmentWiringType apartmentWiringType;
    private MaterialType materialType;
}
