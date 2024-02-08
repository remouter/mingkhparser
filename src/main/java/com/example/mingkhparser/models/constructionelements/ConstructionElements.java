package com.example.mingkhparser.models.constructionelements;

import com.example.mingkhparser.models.LoadBearingWalls;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ConstructionElements {
    private Boolean garbageChute;
    private LoadBearingWalls loadBearingWalls;
    private Integer basementArea;
    private Foundation foundation;
    private FloorType floorType;
}
