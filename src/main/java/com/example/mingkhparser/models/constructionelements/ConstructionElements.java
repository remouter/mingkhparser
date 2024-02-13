package com.example.mingkhparser.models.constructionelements;

import com.example.mingkhparser.models.LoadBearingWalls;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@Getter
@Setter
@ToString
public class ConstructionElements {
    private Boolean garbageChute;
    private Set<LoadBearingWalls> loadBearingWalls;
    private Double basementArea;
    private Foundation foundation;
    private FloorType floorType;
}
