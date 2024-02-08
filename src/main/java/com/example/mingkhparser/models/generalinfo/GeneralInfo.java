package com.example.mingkhparser.models.generalinfo;

import com.example.mingkhparser.models.generalinfo.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Setter
@Getter
@ToString
public class GeneralInfo {
    private Integer year;
    private Boolean isEmergency;
    private HouseCondition houseCondition;
    private Integer apartmentsCount;
    private Integer nonResidentialPremises;
    private Integer balconyCount;
    private EnergyEfficiencyClass energyEfficiencyClass;
    private Integer numberOfEntrances;
    private Integer maxFloor;
    private Integer minFloor;
    private Integer undergroundFloors;
    private RepairFormation repairFormation;
    private Boolean disablePeopleDevices;
    private HouseType houseType;
    private Integer wearOfBuilding;
    private LocalDate wearCalculationDate;
    private Double buildingSquare;
    private Double buildingResidentialSquare;
    private Double buildingNonResidentialSquare;
    private Double buildingCommonPropertySquare;
    private Double landCommonPropertySquare;
    private MaterialType materialType;
    private Boolean isCulturalHeritage;
}
