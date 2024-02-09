package com.example.mingkhparser.models.floors;

import com.example.mingkhparser.models.constructionelements.FloorType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Floors {
    private FloorType floorType;
    private Double physicalDeterioration;
}
