package com.example.mingkhparser.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class InnerWalls {
    private WallMaterial wallMaterial;
    private Integer physicalDeterioration;
}
