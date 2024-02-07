package com.example.mingkhparser.models.floors;

import com.example.mingkhparser.models.constructionelements.FloorType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Floors {
    private FloorType floorType;
    private Integer physicalDeterioration;
}
