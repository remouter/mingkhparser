package com.example.mingkhparser.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@Getter
@Setter
@ToString
public class InnerWalls {
    private Set<WallMaterial> wallMaterials;
    private Double physicalDeterioration;
}
