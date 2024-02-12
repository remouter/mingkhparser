package com.example.mingkhparser.models.generalinfo;

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
    private Integer loggiasNumber;
    private Integer balconyNumber;
    private EnergyEfficiencyClass energyEfficiencyClass;
    private Integer numberOfEntrances;
    private Integer elevatorsNumber;
    private Integer maxFloor;
    private Integer minFloor;
    private Integer undergroundFloors;
    private RepairFormation repairFormation;
    private Double parkingArea;
    private Boolean disablePeopleDevices;
    private HouseType houseType;
    private Double wearOfBuilding;
    private LocalDate wearCalculationDate;
    private Double buildingSquare;
    private Double buildingResidentialSquare;
    private Double buildingNonResidentialSquare;
    private Double buildingCommonPropertySquare;
    private Double landCommonPropertySquare;
    private MaterialType materialType;
    private Boolean isCulturalHeritage;
    private LocalDate emergencyDate;
    private String emergencyDocumentNumber;
}
