package com.example.mingkhparser.models;

import com.example.mingkhparser.models.coldwatersupplysystemrisers.ColdWaterSupplySystemRisers;
import com.example.mingkhparser.models.coldwatersystem.ColdWaterSystem;
import com.example.mingkhparser.models.constructionelements.ConstructionElements;
import com.example.mingkhparser.models.constructionelements.FloorType;
import com.example.mingkhparser.models.doors.Doors;
import com.example.mingkhparser.models.drainagesystem.DrainageSystem;
import com.example.mingkhparser.models.electricitysupplysystem.ElectricitySupplySystem;
import com.example.mingkhparser.models.engineeringsystems.ElectricitySupply;
import com.example.mingkhparser.models.engineeringsystems.EngineeringSystems;
import com.example.mingkhparser.models.facade.Facade;
import com.example.mingkhparser.models.floors.Floors;
import com.example.mingkhparser.models.foundation.Foundation;
import com.example.mingkhparser.models.gassupplysystem.GasSupplySystem;
import com.example.mingkhparser.models.generalinfo.GeneralInfo;
import com.example.mingkhparser.models.generalinfo.HouseType;
import com.example.mingkhparser.models.generalinfo.MaterialType;
import com.example.mingkhparser.models.heatingdevices.HeatingDevices;
import com.example.mingkhparser.models.heatingsystem.HeatingSystem;
import com.example.mingkhparser.models.heatingsystemrisers.HeatingSystemRisers;
import com.example.mingkhparser.models.hotwatersupplysystem.HotWaterSupplySystem;
import com.example.mingkhparser.models.hotwatersupplysystemrisers.HotWaterSupplySystemRisers;
import com.example.mingkhparser.models.roof.Roof;
import com.example.mingkhparser.models.shutoffvalves.coldwater.ShutoffValvesColdWaterSupplySystem;
import com.example.mingkhparser.models.shutoffvalves.heating.ShutoffValvesHeatingSystem;
import com.example.mingkhparser.models.shutoffvalves.hotwater.ShutoffValvesHotWaterSupplySystem;
import com.example.mingkhparser.models.windows.Windows;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@Setter
@Getter
@ToString
public class HouseInfo {
    private String address;
    private Integer year;
    private Integer floor;
    private HouseType houseType;
    private Integer apartmentsCount;
    private String majorRenovation;
    private Set<MaterialType> materialTypes;
    private FloorType floorType;
    private Set<WallMaterial> wallMaterials;
    private Boolean garbageChute;
    private Boolean isEmergency;
    private String cadastralNumber;
    private String oktmoCode;
    private String managementCompany;
    private GeneralInfo generalInfo; //Основные сведения
    private EngineeringSystems engineeringSystems; //Инженерные системы
    private ConstructionElements constructionElements; //Конструктивные элементы
    private HotWaterSupplySystem hotWaterSupplySystem; //Cистема горячего водоснабжения
    private DrainageSystem drainageSystem; //Система водоотведения
    private GasSupplySystem gasSupplySystem;
    private ElectricitySupplySystem electricitySupplySystem;
    private Foundation foundation;
    private InnerWalls innerWalls;
    private Facade facade;
    private Floors floors;
    private Roof roof;
    private Windows windows;
    private Doors doors;
    private CommonAreasFinishingCoatings commonAreasFinishingCoatings;
    private HeatingSystem heatingSystem;
    private HeatingSystemRisers heatingSystemRisers;
    private ShutoffValvesHeatingSystem shutoffValvesHeatingSystem;
    private HeatingDevices heatingDevices;
    private ColdWaterSystem coldWaterSystem;
    private ColdWaterSupplySystemRisers coldWaterSupplySystemRisers;
    private ShutoffValvesColdWaterSupplySystem shutoffValvesColdWaterSupplySystem;
    private HotWaterSupplySystemRisers hotWaterSupplySystemRisers;
    private ShutoffValvesHotWaterSupplySystem shutoffValvesHotWaterSupplySystem;
}

