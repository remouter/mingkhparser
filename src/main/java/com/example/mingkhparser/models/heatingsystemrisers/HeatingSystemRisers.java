package com.example.mingkhparser.models.heatingsystemrisers;

import com.example.mingkhparser.models.heatingsystemrisers.ApartmentWiringType;
import com.example.mingkhparser.models.heatingsystemrisers.MaterialType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HeatingSystemRisers {
    private Integer physicalDeterioration;
    private ApartmentWiringType apartmentWiringType;
    private MaterialType materialType;
}
