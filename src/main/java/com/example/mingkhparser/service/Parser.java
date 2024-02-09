package com.example.mingkhparser.service;

import com.example.mingkhparser.models.*;
import com.example.mingkhparser.models.coldwatersupplysystemrisers.ColdWaterSupplySystemRisers;
import com.example.mingkhparser.models.coldwatersystem.ColdWaterSystem;
import com.example.mingkhparser.models.constructionelements.ConstructionElements;
import com.example.mingkhparser.models.constructionelements.FloorType;
import com.example.mingkhparser.models.constructionelements.Foundation;
import com.example.mingkhparser.models.doors.Doors;
import com.example.mingkhparser.models.drainagesystem.DrainageSystem;
import com.example.mingkhparser.models.drainagesystem.DrainageSystemType;
import com.example.mingkhparser.models.electricitysupplysystem.ElectricitySupplySystem;
import com.example.mingkhparser.models.engineeringsystems.*;
import com.example.mingkhparser.models.facade.ExternalInsulationType;
import com.example.mingkhparser.models.facade.Facade;
import com.example.mingkhparser.models.facade.FacadeFinishingMaterial;
import com.example.mingkhparser.models.floors.Floors;
import com.example.mingkhparser.models.foundation.FoundationMaterial;
import com.example.mingkhparser.models.foundation.FoundationType;
import com.example.mingkhparser.models.gassupplysystem.GasSupplySystem;
import com.example.mingkhparser.models.gassupplysystem.GasSupplySystemType;
import com.example.mingkhparser.models.generalinfo.*;
import com.example.mingkhparser.models.heatingdevices.HeatingDevices;
import com.example.mingkhparser.models.heatingdevices.HeatingDevicesType;
import com.example.mingkhparser.models.heatingsystem.HeatingSystem;
import com.example.mingkhparser.models.heatingsystem.ThermalInsulationMaterial;
import com.example.mingkhparser.models.heatingsystemrisers.ApartmentWiringType;
import com.example.mingkhparser.models.heatingsystemrisers.HeatingSystemRisers;
import com.example.mingkhparser.models.hotwatersupplysystem.*;
import com.example.mingkhparser.models.roof.*;
import com.example.mingkhparser.models.shutoffvalves.coldwater.ShutoffValvesColdWaterSupplySystem;
import com.example.mingkhparser.models.shutoffvalves.heating.ShutoffValvesHeatingSystem;
import com.example.mingkhparser.models.shutoffvalves.hotwater.ShutoffValvesHotWaterSupplySystem;
import com.example.mingkhparser.models.windows.Windows;
import com.example.mingkhparser.models.windows.WindowsType;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

@Service
@Slf4j
public class Parser {
    private static Integer COUNTER = 0;

    private InputStream getInputStream(String url) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
            connection.connect();
            return connection.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getHouses(String url) {
        List<String> houses = new ArrayList<>();
        IntStream.range(1, 18).forEach(i -> {
            Scanner scanner = new Scanner(new BufferedInputStream(getInputStream(url + "?page=" + i)));
            Document doc = Jsoup.parseBodyFragment(scanner.nextLine());
            for (Element element : doc.select(".table-responsive").get(0).children().get(0).children().get(1).children()) {
                String address = element.children().get(1).children().get(0).attribute("href").getValue();
                houses.add("https://dom.mingkh.ru" + address);
            }
        });

        return houses;
    }

    public synchronized void process(String url) {
        log.trace("{}. working on {}", COUNTER++, url);
        Scanner scanner = new Scanner(new BufferedInputStream(getInputStream(url)));
        Document doc = Jsoup.parseBodyFragment(scanner.nextLine());
        HouseInfo houseInfo = new HouseInfo();

        setInfo(houseInfo, doc);
        setGeneralInfo(houseInfo, doc);
        setDetailedInfo(houseInfo, doc);
        setDetailedInfo2(houseInfo, doc);

        log.info("houseInfo: {}", houseInfo);
    }

    private void setDetailedInfo2(HouseInfo houseInfo, Document doc) {
        Floors floors = houseInfo.getFloors() == null ? new Floors() : houseInfo.getFloors();
        Roof roof = houseInfo.getRoof() == null ? new Roof() : houseInfo.getRoof();
        Windows windows = new Windows();
        Doors doors = new Doors();
        CommonAreasFinishingCoatings commonAreasFinishingCoatings = new CommonAreasFinishingCoatings();
        HeatingSystem heatingSystem = new HeatingSystem();
        HeatingSystemRisers heatingSystemRisers = new HeatingSystemRisers();
        ShutoffValvesHeatingSystem shutoffValvesHeatingSystem = new ShutoffValvesHeatingSystem();
        HeatingDevices heatingDevices = new HeatingDevices();
        ColdWaterSystem coldWaterSystem = new ColdWaterSystem();
        ColdWaterSupplySystemRisers coldWaterSupplySystemRisers = new ColdWaterSupplySystemRisers();
        ShutoffValvesColdWaterSupplySystem shutoffValvesColdWaterSupplySystem = new ShutoffValvesColdWaterSupplySystem();
        ShutoffValvesHotWaterSupplySystem shutoffValvesHotWaterSupplySystem = new ShutoffValvesHotWaterSupplySystem();

        Elements table = doc.select("body .outer .main-block .container .margin-bottom-20");
        for (int i = 12; i < table.size(); i++) {
            Element element = table.get(i);
            element.children();
            for (int j = 0; j < element.select(".col-md-6").size(); j++) {
                String partition = element.select(".col-md-12").get(0).text().trim();
                String tag = element.select(".col-md-6").get(j).text().trim();
                String value = element.select(".col-md-6").get(++j).text().trim();

                switch (partition) {
                    case "Перекрытия":
                        setFloors(tag, value, floors);
                        break;
                    case "Крыша":
                        setRoof(tag, value, roof);
                        break;
                    case "Окна":
                        setWindows(tag, value, windows);
                        break;
                    case "Двери":
                        setDoors(tag, value, doors);
                        break;
                    case "Отделочные покрытия помещений общего пользования":
                        setCommonAreasFinishingCoatings(tag, value, commonAreasFinishingCoatings);
                        break;
                    case "Система отопления":
                        setHeatingSystem(tag, value, heatingSystem);
                        break;
                    case "Стояки системы отопления":
                        setHeatingSystemRisers(tag, value, heatingSystemRisers);
                        break;
                    case "Запорная арматура системы отопления":
                        setShutoffValvesHeatingSystem(tag, value, shutoffValvesHeatingSystem);
                        break;
                    case "Отопительные приборы":
                        setHeatingDevices(tag, value, heatingDevices);
                        break;
                    case "Система холодного водоснабжения":
                        setColdWaterSystem(tag, value, coldWaterSystem);
                        break;
                    case "Стояки системы холодного водоснабжения":
                        setColdWaterSupplySystemRisers(tag, value, coldWaterSupplySystemRisers);
                        break;
                    case "Запорная арматура системы холодного водоснабжения":
                        setShutoffValvesColdWaterSupplySystem(tag, value, shutoffValvesColdWaterSupplySystem);
                        break;
                    case "Запорная арматура системы горячего водоснабжения":
                        setShutoffValvesHotWaterSupplySystem(tag, value, shutoffValvesHotWaterSupplySystem);
                        break;
                    default:
                        throw new IllegalArgumentException(partition);
                }
            }
        }

        houseInfo.setFloors(floors);
        houseInfo.setRoof(roof);
        houseInfo.setWindows(windows);
        houseInfo.setDoors(doors);
        houseInfo.setCommonAreasFinishingCoatings(commonAreasFinishingCoatings);
        houseInfo.setHeatingSystem(heatingSystem);
        houseInfo.setHeatingSystemRisers(heatingSystemRisers);
        houseInfo.setShutoffValvesHeatingSystem(shutoffValvesHeatingSystem);
        houseInfo.setHeatingDevices(heatingDevices);
        houseInfo.setColdWaterSystem(coldWaterSystem);
        houseInfo.setColdWaterSupplySystemRisers(coldWaterSupplySystemRisers);
        houseInfo.setShutoffValvesColdWaterSupplySystem(shutoffValvesColdWaterSupplySystem);
        houseInfo.setShutoffValvesHotWaterSupplySystem(shutoffValvesHotWaterSupplySystem);
    }

    private void setShutoffValvesHotWaterSupplySystem(String tag, String value, ShutoffValvesHotWaterSupplySystem shutoffValvesHotWaterSupplySystem) {
        switch (tag) {
            case "Физический износ":
                shutoffValvesHotWaterSupplySystem.setPhysicalDeterioration(Integer.valueOf(value.split(" ")[0]));
                break;
            default:
                throw new IllegalArgumentException(tag);
        }
    }

    private void setShutoffValvesColdWaterSupplySystem(String tag, String value, ShutoffValvesColdWaterSupplySystem shutoffValvesColdWaterSupplySystem) {
        switch (tag) {
            case "Физический износ":
                shutoffValvesColdWaterSupplySystem.setPhysicalDeterioration(Integer.valueOf(value.split(" ")[0]));
                break;
            default:
                throw new IllegalArgumentException(tag);
        }
    }

    private void setColdWaterSupplySystemRisers(String tag, String value, ColdWaterSupplySystemRisers coldWaterSupplySystemRisers) {
        switch (tag) {
            case "Физический износ":
                coldWaterSupplySystemRisers.setPhysicalDeterioration(Integer.valueOf(value.split(" ")[0]));
                break;
            case "Материал сети":
                com.example.mingkhparser.models.coldwatersupplysystemrisers.NetworkMaterial networkMaterial;
                switch (value) {
                    case "Сталь оцинкованная":
                        networkMaterial = com.example.mingkhparser.models.coldwatersupplysystemrisers.NetworkMaterial.GALVANIZEDSTEEL;
                        break;
                    case "Полимер":
                        networkMaterial = com.example.mingkhparser.models.coldwatersupplysystemrisers.NetworkMaterial.POLYMER;
                        break;
                    case "Нет":
                        networkMaterial = com.example.mingkhparser.models.coldwatersupplysystemrisers.NetworkMaterial.NONE;
                        break;
                    case "Металлополимер":
                        networkMaterial = com.example.mingkhparser.models.coldwatersupplysystemrisers.NetworkMaterial.METALPOLYMER;
                        break;
                    case "Сталь":
                        networkMaterial = com.example.mingkhparser.models.coldwatersupplysystemrisers.NetworkMaterial.STEEL;
                        break;
                    default:
                        throw new IllegalArgumentException(value);
                }
                coldWaterSupplySystemRisers.setNetworkMaterial(networkMaterial);
                break;
            default:
                throw new IllegalArgumentException(tag);
        }
    }

    private void setColdWaterSystem(String tag, String value, ColdWaterSystem coldWaterSystem) {
        switch (tag) {
            case "Физический износ":
                coldWaterSystem.setPhysicalDeterioration(Integer.valueOf(value.split(" ")[0]));
                break;
            case "Материал сети":
                com.example.mingkhparser.models.coldwatersystem.NetworkMaterial networkMaterial;
                switch (value) {
                    case "Сталь оцинкованная":
                        networkMaterial = com.example.mingkhparser.models.coldwatersystem.NetworkMaterial.GALVANIZEDSTEEL;
                        break;
                    case "Полимер":
                        networkMaterial = com.example.mingkhparser.models.coldwatersystem.NetworkMaterial.POLYMER;
                        break;
                    case "Нет":
                        networkMaterial = com.example.mingkhparser.models.coldwatersystem.NetworkMaterial.NONE;
                        break;
                    case "Сталь":
                        networkMaterial = com.example.mingkhparser.models.coldwatersystem.NetworkMaterial.STEEL;
                        break;
                    case "Металлополимер":
                        networkMaterial = com.example.mingkhparser.models.coldwatersystem.NetworkMaterial.METALPOLYMER;
                        break;
                    default:
                        throw new IllegalArgumentException(value);
                }
                coldWaterSystem.setNetworkMaterial(networkMaterial);
                break;
            default:
                throw new IllegalArgumentException(tag);
        }
    }

    private void setHeatingDevices(String tag, String value, HeatingDevices heatingDevices) {
        switch (tag) {
            case "Физический износ":
                heatingDevices.setPhysicalDeterioration(Integer.valueOf(value.split(" ")[0]));
                break;
            case "Тип отопительных приборов":
                HeatingDevicesType heatingDevicesType;
                switch (value) {
                    case "Радиатор":
                        heatingDevicesType = HeatingDevicesType.RADIATOR;
                        break;
                    case "Регистр":
                        heatingDevicesType = HeatingDevicesType.REGISTER;
                        break;
                    case "Нет":
                        heatingDevicesType = HeatingDevicesType.NONE;
                        break;
                    default:
                        throw new IllegalArgumentException(value);
                }
                heatingDevices.setHeatingDevicesType(heatingDevicesType);
                break;
            default:
                throw new IllegalArgumentException(tag);
        }
    }

    private void setShutoffValvesHeatingSystem(String tag, String value, ShutoffValvesHeatingSystem shutoffValvesHeatingSystem) {
        switch (tag) {
            case "Физический износ":
                shutoffValvesHeatingSystem.setPhysicalDeterioration(Integer.valueOf(value.split(" ")[0]));
                break;
            default:
                throw new IllegalArgumentException(tag);
        }
    }

    private void setHeatingSystemRisers(String tag, String value, HeatingSystemRisers heatingSystemRisers) {
        switch (tag) {
            case "Физический износ":
                heatingSystemRisers.setPhysicalDeterioration(Integer.valueOf(value.split(" ")[0]));
                break;
            case "Тип поквартирной разводки внутридомовой системы отопления":
                ApartmentWiringType apartmentWiringType;
                switch (value) {
                    case "Вертикальная":
                        apartmentWiringType = ApartmentWiringType.VERTICAL;
                        break;
                    case "Горизонтальная":
                        apartmentWiringType = ApartmentWiringType.HORIZONTAL;
                        break;
                    case "Нет":
                        apartmentWiringType = ApartmentWiringType.NONE;
                        break;
                    default:
                        throw new IllegalArgumentException(value);
                }
                heatingSystemRisers.setApartmentWiringType(apartmentWiringType);
                break;
            case "Материал":
                com.example.mingkhparser.models.heatingsystemrisers.MaterialType materialType;
                switch (value) {
                    case "Сталь оцинкованная":
                        materialType = com.example.mingkhparser.models.heatingsystemrisers.MaterialType.GALVANIZEDSTEEL;
                        break;
                    case "Полимер":
                        materialType = com.example.mingkhparser.models.heatingsystemrisers.MaterialType.POLYMER;
                        break;
                    case "Нет":
                        materialType = com.example.mingkhparser.models.heatingsystemrisers.MaterialType.NONE;
                        break;
                    case "Сталь":
                        materialType = com.example.mingkhparser.models.heatingsystemrisers.MaterialType.STEEL;
                        break;
                    case "Чугун":
                        materialType = com.example.mingkhparser.models.heatingsystemrisers.MaterialType.CASTIRON;
                        break;
                    default:
                        throw new IllegalArgumentException(value);
                }
                heatingSystemRisers.setMaterialType(materialType);
                break;
            default:
                throw new IllegalArgumentException(tag);
        }
    }

    private void setHeatingSystem(String tag, String value, HeatingSystem heatingSystem) {
        switch (tag) {
            case "Физический износ":
                heatingSystem.setPhysicalDeterioration(Integer.valueOf(value.split(" ")[0]));
                break;
            case "Материал сети":
                com.example.mingkhparser.models.heatingsystem.NetworkMaterial networkMaterial;
                switch (value) {
                    case "Сталь оцинкованная":
                        networkMaterial = com.example.mingkhparser.models.heatingsystem.NetworkMaterial.GALVANIZEDSTEEL;
                        break;
                    case "Нет":
                        networkMaterial = com.example.mingkhparser.models.heatingsystem.NetworkMaterial.NONE;
                        break;
                    case "Полимер":
                        networkMaterial = com.example.mingkhparser.models.heatingsystem.NetworkMaterial.POLYMER;
                        break;
                    case "Сталь":
                        networkMaterial = com.example.mingkhparser.models.heatingsystem.NetworkMaterial.STEEL;
                        break;
                    default:
                        throw new IllegalArgumentException(value);
                }
                heatingSystem.setNetworkMaterial(networkMaterial);
                break;
            case "Материал теплоизоляции сети":
                ThermalInsulationMaterial thermalInsulationMaterial;
                switch (value) {
                    case "Вспененный полиэтилен (энергофлекс)":
                        thermalInsulationMaterial = ThermalInsulationMaterial.FOAMEDPOLYETHYLENE;
                        break;
                    case "Нет":
                        thermalInsulationMaterial = ThermalInsulationMaterial.NONE;
                        break;
                    default:
                        throw new IllegalArgumentException(value);
                }
                heatingSystem.setThermalInsulationMaterial(thermalInsulationMaterial);
                break;
            default:
                throw new IllegalArgumentException(tag);
        }
    }

    private void setCommonAreasFinishingCoatings(String tag, String value, CommonAreasFinishingCoatings commonAreasFinishingCoatings) {
        switch (tag) {
            case "Физический износ":
                commonAreasFinishingCoatings.setPhysicalDeterioration(Integer.valueOf(value.split(" ")[0]));
                break;
            default:
                throw new IllegalArgumentException(tag);
        }
    }

    private void setDoors(String tag, String value, Doors doors) {
        switch (tag) {
            case "Физический износ":
                doors.setPhysicalDeterioration(Integer.valueOf(value.split(" ")[0]));
                break;
            default:
                throw new IllegalArgumentException(tag);
        }
    }

    private void setWindows(String tag, String value, Windows windows) {
        switch (tag) {
            case "Физический износ":
                windows.setPhysicalDeterioration(Integer.valueOf(value.split(" ")[0]));
                break;
            case "Материал окон":
                WindowsType windowsType;
                switch (value) {
                    case "Пластиковые, Деревянные":
                        windowsType = WindowsType.PLASTICWOODEN;
                        break;
                    case "Деревянные":
                        windowsType = WindowsType.WOODEN;
                        break;
                    case "Пластиковые":
                        windowsType = WindowsType.PLASTIC;
                        break;
                    default:
                        throw new IllegalArgumentException(value);
                }
                windows.setWindowsType(windowsType);
                break;
            default:
                throw new IllegalArgumentException(tag);
        }
    }

    private void setRoof(String tag, String value, Roof roof) {
        switch (tag) {
            case "Форма крыши":
                RoofShape roofShape;
                switch (value) {
                    case "Плоская":
                        roofShape = RoofShape.FLAT;
                        break;
                    case "Скатная":
                        roofShape = RoofShape.SLOPING;
                        break;
                    case "Двускатная":
                        roofShape = RoofShape.GABLE;
                        break;
                    default:
                        throw new IllegalArgumentException(value);
                }
                roof.setRoofShape(roofShape);
                break;
            case "Утепляющие слои чердачных перекрытий":
                InsulatingLayers insulatingLayers;
                switch (value) {
                    case "Керамзит или шлак":
                        insulatingLayers = InsulatingLayers.EXPANDEDCLAYSLAG;
                        break;
                    default:
                        throw new IllegalArgumentException(value);
                }
                roof.setInsulatingLayers(insulatingLayers);
                break;
            case "Вид несущей части":
                BearingType bearingType;
                switch (value) {
                    case "Железобетонные сборные (чердачные)":
                        bearingType = BearingType.PREFABRICATEDREINFORCEDCONCRETE;
                        break;
                    case "Деревянные":
                        bearingType = BearingType.WOODEN;
                        break;
                    case "Стропильная":
                        bearingType = BearingType.RAFTER;
                        break;
                    default:
                        throw new IllegalArgumentException(value);
                }
                roof.setBearingType(bearingType);
                break;
            case "Тип кровли":
                RoofType roofType;
                switch (value) {
                    case "Рулонная":
                        roofType = RoofType.ROLL;
                        break;
                    case "Волнистые листы":
                        roofType = RoofType.CORRUGATEDSHEETS;
                        break;
                    case "Шиферная":
                        roofType = RoofType.SLATE;
                        break;
                    default:
                        throw new IllegalArgumentException(value);
                }
                roof.setRoofType(roofType);
                break;
            case "Физический износ кровли":
                roof.setPhysicalDeterioration(Integer.valueOf(value.split(" ")[0]));
                break;
            case "Год проведения последнего капитального ремонта кровли":
                roof.setLastOverhaulYear(Integer.valueOf(value));
                break;
            default:
                throw new IllegalArgumentException(tag);
        }
    }

    private void setFloors(String tag, String value, Floors floors) {
        switch (tag) {
            case "Тип перекрытия":
                FloorType floorType;
                switch (value) {
                    case "Перекрытия из железобетонных плит":
                        floorType = FloorType.REINFORCEDCONCRETESLABS;
                        break;
                    case "Перекрытия деревянные неоштукатуренные":
                        floorType = FloorType.WOODENUNPLASTERED;
                        break;
                    case "Перекрытия деревянные оштукатуренные":
                        floorType = FloorType.WOODENPLASTERED;
                        break;
                    default:
                        throw new IllegalArgumentException(value);
                }
                floors.setFloorType(floorType);
                break;
            case "Физический износ":
                floors.setPhysicalDeterioration(Integer.valueOf(value.split(" ")[0]));
                break;
            default:
                throw new IllegalArgumentException(tag);
        }
    }

    private void setDetailedInfo(HouseInfo houseInfo, Document doc) {
        HotWaterSupplySystem hotWaterSupplySystem = new HotWaterSupplySystem();
        DrainageSystem drainageSystem = new DrainageSystem();
        GasSupplySystem gasSupplySystem = new GasSupplySystem();
        ElectricitySupplySystem electricitySupplySystem = new ElectricitySupplySystem();
        com.example.mingkhparser.models.foundation.Foundation foundation = new com.example.mingkhparser.models.foundation.Foundation();
        InnerWalls innerWalls = new InnerWalls();
        Facade facade = new Facade();
        Floors floors = new Floors();
        Roof roof = new Roof();

        for (Element element : doc.select("body .outer .main-block .container .row").get(7).children().get(0).children()) {
            for (int i = 0; i < element.select(".col-md-6").size(); i++) {
                String tag = element.select(".col-md-6").get(i).text().trim();
                String value = element.select(".col-md-6").get(++i).text().trim();
                String partition = element.select(".col-md-12").get(0).text().trim();

                switch (partition) {
                    case "Cистема горячего водоснабжения":
                        setHotWaterSupplySystem(tag, value, hotWaterSupplySystem);
                        break;
                    case "Система водоотведения":
                        setDrainageSystem(tag, value, drainageSystem);
                        break;
                    case "Система газоснабжения":
                        setGasSupplySystem(tag, value, gasSupplySystem);
                        break;
                    case "Система электроснабжения":
                        setElectricitySupplySystem(tag, value, electricitySupplySystem);
                        break;
                    case "Фундамент":
                        setFoundation(tag, value, foundation);
                        break;
                    case "Внутренние стены":
                        setInnerWalls(tag, value, innerWalls);
                        break;
                    case "Фасад":
                        setFacade(tag, value, facade);
                        break;
                    case "Перекрытия":
                        setFloors(tag, value, floors);
                        break;
                    case "Крыша":
                        setRoof(tag, value, roof);
                        break;
                    default:
                        throw new IllegalArgumentException(partition);
                }
            }
        }

        houseInfo.setHotWaterSupplySystem(hotWaterSupplySystem);
        houseInfo.setDrainageSystem(drainageSystem);
        houseInfo.setGasSupplySystem(gasSupplySystem);
        houseInfo.setElectricitySupplySystem(electricitySupplySystem);
        houseInfo.setFoundation(foundation);
        houseInfo.setInnerWalls(innerWalls);
        houseInfo.setFacade(facade);
        houseInfo.setFloors(floors);
        houseInfo.setRoof(roof);
    }

    private void setFacade(String tag, String value, Facade facade) {
        switch (tag) {
            case "Тип наружных стен":
                WallMaterial wallMaterial;
                switch (value) {
                    case "Стены кирпичные":
                        wallMaterial = WallMaterial.BRICK;
                        break;
                    case "Стены деревянные каркасные":
                        wallMaterial = WallMaterial.WOODENFRAME;
                        break;
                    case "Стены рубленные из бревен и брусчатые":
                        wallMaterial = WallMaterial.LOGSTIMBER;
                        break;
                    case "Стены деревянные, сборно-щитовые":
                        wallMaterial = WallMaterial.WOODENPREFABRICATEDPANELS;
                        break;
                    default:
                        throw new IllegalArgumentException(value);
                }
                facade.setOuterWallsMaterial(wallMaterial);
                break;
            case "Тип наружного утепления фасада":
                ExternalInsulationType externalInsulationType;
                switch (value) {
                    case "Нет":
                        externalInsulationType = ExternalInsulationType.NONE;
                        break;
                    default:
                        throw new IllegalArgumentException(value);
                }
                facade.setExternalInsulationType(externalInsulationType);
                break;
            case "Материал отделки фасада":
                FacadeFinishingMaterial facadeFinishingMaterial;
                switch (value) {
                    case "без отделки":
                        facadeFinishingMaterial = FacadeFinishingMaterial.WITHOUTFINISHING;
                        break;
                    case "окраска":
                        facadeFinishingMaterial = FacadeFinishingMaterial.PAINTING;
                        break;
                    case "Иной":
                        facadeFinishingMaterial = FacadeFinishingMaterial.OTHER;
                        break;
                    default:
                        throw new IllegalArgumentException(value);
                }
                facade.setFacadeFinishingMaterial(facadeFinishingMaterial);
                break;
            case "Физический износ":
                facade.setPhysicalDeterioration(Integer.valueOf(value.split(" ")[0]));
                break;
            case "Год проведения последнего капитального ремонта":
                facade.setLastOverhaulYear(Integer.valueOf(value));
                break;
            default:
                throw new IllegalArgumentException(tag);
        }
    }

    private void setInnerWalls(String tag, String value, InnerWalls innerWalls) {
        switch (tag) {
            case "Тип внутренних стен":
                WallMaterial wallMaterial;
                switch (value) {
                    case "Стены кирпичные":
                        wallMaterial = WallMaterial.BRICK;
                        break;
                    case "Стены деревянные каркасные":
                        wallMaterial = WallMaterial.WOODENFRAME;
                        break;
                    case "Стены рубленные из бревен и брусчатые":
                        wallMaterial = WallMaterial.LOGSTIMBER;
                        break;
                    case "Стены деревянные, сборно-щитовые":
                        wallMaterial = WallMaterial.WOODENPREFABRICATEDPANELS;
                        break;
                    default:
                        throw new IllegalArgumentException(value);
                }
                innerWalls.setWallMaterial(wallMaterial);
                break;
            case "Физический износ":
                innerWalls.setPhysicalDeterioration(Integer.valueOf(value.split(" ")[0]));
                break;
            default:
                throw new IllegalArgumentException(tag);
        }
    }

    private void setFoundation(String tag, String value, com.example.mingkhparser.models.foundation.Foundation foundation) {
        switch (tag) {
            case "Тип фундамента":
                FoundationType foundationType;
                switch (value) {
                    case "Ленточный":
                        foundationType = FoundationType.TAPE;
                        break;
                    case "Ж/б крупноблочный":
                        foundationType = FoundationType.REINFORCEDCONCRETELARGEBLOCK;
                        break;
                    default:
                        throw new IllegalArgumentException(value);
                }
                foundation.setFoundationType(foundationType);
                break;
            case "Материал фундамента":
                FoundationMaterial foundationMaterial;
                switch (value) {
                    case "Железобетонные блоки":
                        foundationMaterial = FoundationMaterial.REINFORCEDCONCRETEBLOCKS;
                        break;
                    case "Кирпич керамический":
                        foundationMaterial = FoundationMaterial.CERAMICBRICK;
                        break;
                    case "Монолитный железобетон":
                        foundationMaterial = FoundationMaterial.REINFORCEDCONCRETESMONOLITHIC;
                        break;
                    case "Сборный железобетон":
                        foundationMaterial = FoundationMaterial.PRECASTCONCRETE;
                        break;
                    case "Нет":
                        foundationMaterial = FoundationMaterial.NONE;
                        break;
                    default:
                        throw new IllegalArgumentException(value);
                }
                foundation.setFoundationMaterial(foundationMaterial);
                break;
            case "Площадь отмостки":
                foundation.setBlindArea(Double.valueOf(value.split(" ")[0]));
                break;
            case "Физический износ":
                foundation.setPhysicalDeterioration(Integer.valueOf(value.split(" ")[0]));
                break;
            case "Год проведения последнего капитального ремонта":
                foundation.setLastOverhaulYear(Integer.valueOf(value));
                break;
            default:
                throw new IllegalArgumentException(tag);
        }
    }

    private void setElectricitySupplySystem(String tag, String value, ElectricitySupplySystem electricitySupplySystem) {
        switch (tag) {
            case "Физический износ":
                electricitySupplySystem.setPhysicalDeterioration(Integer.valueOf(value.split(" ")[0]));
                break;
            case "Год проведения последнего капитального ремонта":
                electricitySupplySystem.setLastOverhaulYear(Integer.valueOf(value));
                break;
            case "Количество вводов системы электроснабжения":
                electricitySupplySystem.setPowerSupplyInputNumbers(Integer.valueOf(value.split(" ")[0]));
                break;
            default:
                throw new IllegalArgumentException(tag);
        }
    }

    private void setGasSupplySystem(String tag, String value, GasSupplySystem gasSupplySystem) {
        switch (tag) {
            case "Год проведения последнего капитального ремонта":
                gasSupplySystem.setLastOverhaulYear(Integer.valueOf(value));
                break;
            case "Тип системы газоснабжения":
                GasSupplySystemType gasSupplySystemType;
                switch (value) {
                    case "центральное":
                        gasSupplySystemType = GasSupplySystemType.CENTRAL;
                        break;
                    case "нет":
                        gasSupplySystemType = GasSupplySystemType.NONE;
                        break;
                    default:
                        throw new IllegalArgumentException(value);
                }
                gasSupplySystem.setGasSupplySystemType(gasSupplySystemType);
                break;
            case "Количество вводов системы газоснабжения":
                gasSupplySystem.setGasSupplySystemInletsNumber(Integer.valueOf(value.split(" ")[0]));
                break;
            default:
                throw new IllegalArgumentException(tag);
        }
    }

    private void setDrainageSystem(String tag, String value, DrainageSystem drainageSystem) {
        switch (tag) {
//Система водоотведения
            case "Физический износ":
                drainageSystem.setPhysicalDeterioration(Integer.valueOf(value.split(" ")[0]));
                break;
            case "Год проведения последнего капитального ремонта":
                drainageSystem.setLastOverhaulYear(Integer.valueOf(value));
                break;
            case "Тип системы водоотведения":
                DrainageSystemType drainageSystemType;
                switch (value) {
                    case "Централизованная канализация":
                        drainageSystemType = DrainageSystemType.CENTRALIZEDSEWERAGE;
                        break;
                    case "Нет":
                        drainageSystemType = DrainageSystemType.NONE;
                        break;
                    case "Выгребная яма":
                        drainageSystemType = DrainageSystemType.CESSPOOL;
                        break;
                    default:
                        throw new IllegalArgumentException(value);
                }
                drainageSystem.setDrainageSystemType(drainageSystemType);
                break;
            case "Материал сети":
                com.example.mingkhparser.models.drainagesystem.NetworkMaterial networkMaterial;
                switch (value) {
                    case "чугун":
                        networkMaterial = com.example.mingkhparser.models.drainagesystem.NetworkMaterial.CASTIRON;
                        break;
                    case "Нет":
                        networkMaterial = com.example.mingkhparser.models.drainagesystem.NetworkMaterial.NONE;
                        break;
                    case "пластик":
                        networkMaterial = com.example.mingkhparser.models.drainagesystem.NetworkMaterial.PLASTIC;
                        break;
                    case "чугун, пластик":
                        networkMaterial = com.example.mingkhparser.models.drainagesystem.NetworkMaterial.CASTIRONPLASTIC;
                        break;
                    default:
                        throw new IllegalArgumentException(value);
                }
                drainageSystem.setNetworkMaterial(networkMaterial);
                break;
            default:
                throw new IllegalArgumentException(tag);
        }
    }

    private void setHotWaterSupplySystem(String tag, String value, HotWaterSupplySystem hotWaterSupplySystem) {
        switch (tag) {
//Cистема горячего водоснабжения
            case "Тип системы горячего водоснабжения":
                HotWaterSystemType hotWaterSystemType;
                switch (value) {
                    case "Нет":
                        hotWaterSystemType = HotWaterSystemType.NONE;
                        break;
                    default:
                        throw new IllegalArgumentException(value);
                }
                hotWaterSupplySystem.setHotWaterSystemType(hotWaterSystemType);
                break;
            case "Физический износ":
                hotWaterSupplySystem.setPhysicalDeterioration(Integer.valueOf(value.split(" ")[0]));
                break;
            case "Материал сети":
                NetworkMaterial networkMaterial;
                switch (value) {
                    case "Нет":
                        networkMaterial = NetworkMaterial.NONE;
                        break;
                    case "Металлополимер":
                        networkMaterial = NetworkMaterial.METALPOLYMER;
                        break;
                    default:
                        throw new IllegalArgumentException(value);
                }
                hotWaterSupplySystem.setNetworkMaterial(networkMaterial);
                break;
            case "Материал теплоизоляции сети":
                NetworkThermalInsulationMaterial networkThermalInsulationMaterial;
                switch (value) {
                    case "Нет":
                        networkThermalInsulationMaterial = NetworkThermalInsulationMaterial.NONE;
                        break;
                    case "Минеральная вата с покрытием из алюминиевой фольги":
                        networkThermalInsulationMaterial = NetworkThermalInsulationMaterial.MINERALWOOLCOATEDWITHALUMINUMFOIL;
                        break;
                    default:
                        throw new IllegalArgumentException(value);
                }
                hotWaterSupplySystem.setNetworkThermalInsulationMaterial(networkThermalInsulationMaterial);
                break;
            case "Материал стояков":
                RisersMaterial risersMaterial;
                switch (value) {
                    case "Нет":
                        risersMaterial = RisersMaterial.NONE;
                        break;
                    default:
                        throw new IllegalArgumentException(value);
                }
                hotWaterSupplySystem.setRisersMaterial(risersMaterial);
                break;
            default:
                throw new IllegalArgumentException(tag);
        }
    }

    private static void setGeneralInfo(HouseInfo houseInfo, Document doc) {
        GeneralInfo generalInfo = new GeneralInfo();
        EngineeringSystems engineeringSystems = new EngineeringSystems();
        ConstructionElements constructionElements = new ConstructionElements();

        for (Element outerElement : doc.select(".table-responsive")) {
            for (Element element : outerElement.children().get(0).children().get(0).children()) {
                String tag = ((Element) element.childNodes().get(1)).text();
                String value = ((Element) element.childNodes().get(3)).text();

                switch (tag) {
                    case "Год ввода в эксплуатацию":
                        generalInfo.setYear(Integer.valueOf(value));
                        break;
                    case "Дом признан аварийным":
                        switch (value) {
                            case "Нет":
                                generalInfo.setIsEmergency(false);
                                break;
                            case "Да":
                                generalInfo.setIsEmergency(true);
                                break;
                            default:
                                throw new IllegalArgumentException(value);
                        }
                        break;
                    case "Состояние дома":
                        HouseCondition houseCondition;
                        switch (value) {
                            case "Исправный":
                                houseCondition = HouseCondition.SERVICEABLE;
                                break;
                            case "Аварийный":
                                houseCondition = HouseCondition.EMERGENCY;
                                break;
                            default:
                                throw new IllegalArgumentException(value);
                        }
                        generalInfo.setHouseCondition(houseCondition);
                        break;
                    case "Количество квартир":
                        generalInfo.setApartmentsCount(Integer.valueOf(value));
                        break;
                    case "Количество нежилых помещений":
                        generalInfo.setNonResidentialPremises(Integer.valueOf(value));
                        break;
                    case "Количество балконов":
                        generalInfo.setBalconyCount(Integer.valueOf(value));
                        break;
                    case "Класс энергетической эффективности":
                        EnergyEfficiencyClass energyEfficiencyClass;
                        switch (value) {
                            case "Не присвоен":
                                energyEfficiencyClass = EnergyEfficiencyClass.NOTASSIGNED;
                                break;
                            default:
                                throw new IllegalArgumentException(value);
                        }
                        generalInfo.setEnergyEfficiencyClass(energyEfficiencyClass);
                        break;
                    case "Количество подъездов":
                        generalInfo.setNumberOfEntrances(Integer.valueOf(value));
                        break;
                    case "Наибольшее количество этажей":
                        generalInfo.setMaxFloor(Integer.valueOf(value));
                        break;
                    case "Наименьшее количество этажей":
                        generalInfo.setMinFloor(Integer.valueOf(value));
                        break;
                    case "Подземных этажей":
                        generalInfo.setUndergroundFloors(Integer.valueOf(value));
                        break;
                    case "Формирование фонда кап. ремонта":
                        RepairFormation repairFormation;
                        switch (value) {
                            case "На счете регионального оператора":
                                repairFormation = RepairFormation.REGIONAL;
                                break;
                            default:
                                throw new IllegalArgumentException(value);
                        }
                        generalInfo.setRepairFormation(repairFormation);
                        break;
                    case "Наличие в подъездах приспособлений для нужд маломобильных групп населения":
                        switch (value) {
                            case "Нет":
                                generalInfo.setDisablePeopleDevices(false);
                                break;
                            default:
                                throw new IllegalArgumentException(value);
                        }
                        break;
                    case "Тип дома":
                        HouseType houseType;
                        switch (value) {
                            case "Многоквартирный дом":
                                houseType = HouseType.MANYAPPARTMENTS;
                                break;
                            default:
                                throw new IllegalArgumentException(value);
                        }
                        generalInfo.setHouseType(houseType);
                        break;
                    case "Дата документа о признании дома аварийным":
                        generalInfo.setEmergencyDate(LocalDate.parse(value, DateTimeFormatter.ofPattern("dd.MM.yyyy")));
                        break;
                    case "Номер документа о признании дома аварийным":
                        generalInfo.setEmergencyDocumentNumber(value);
                        break;
                    case "Износ здания, %":
                        generalInfo.setWearOfBuilding(Integer.valueOf(value));
                        break;
                    case "Дата, на которую установлен износ здания":
                        generalInfo.setWearCalculationDate(LocalDate.parse(value, DateTimeFormatter.ofPattern("dd.MM.yyyy")));
                        break;
                    case "Площадь многоквартирного дома, кв.м":
                        generalInfo.setBuildingSquare(Double.valueOf(value));
                        break;
                    case "Площадь жилых помещений м2":
                        generalInfo.setBuildingResidentialSquare(Double.valueOf(value));
                        break;
                    case "Площадь нежилых помещений м2":
                        generalInfo.setBuildingNonResidentialSquare(Double.valueOf(value));
                        break;
                    case "Площадь помещений общего имущества м2":
                        generalInfo.setBuildingCommonPropertySquare(Double.valueOf(value));
                        break;
                    case "Площадь зем. участка общего имущества м2":
                        generalInfo.setLandCommonPropertySquare(Double.valueOf(value));
                        break;
                    case "Серия, тип постройки здания":
                        MaterialType materialType;
                        switch (value) {
                            case "Кирпичный":
                                materialType = MaterialType.BRICK;
                                break;
                            case "нет":
                                materialType = MaterialType.NONE;
                                break;
                            case "дом":
                                materialType = MaterialType.HOUSE;
                                break;
                            default:
                                throw new IllegalArgumentException(value);
                        }
                        generalInfo.setMaterialType(materialType);
                        break;
                    case "Статус объекта культурного наследия":
                        switch (value) {
                            case "Нет":
                                generalInfo.setIsCulturalHeritage(false);
                                break;
                            default:
                                throw new IllegalArgumentException(value);
                        }
                        break;
//Инженерные системы
                    case "Вентиляция":
                        Ventilation ventilation;
                        switch (value) {
                            case "Приточно-вытяжная вентиляция":
                                ventilation = Ventilation.SUPPLYANDEXHAUSTVENTILATION;
                                break;
                            default:
                                throw new IllegalArgumentException(value);
                        }
                        engineeringSystems.setVentilation(ventilation);
                        break;
                    case "Водоотведение":
                        WaterDisposal waterDisposal;
                        switch (value) {
                            case "Центральное":
                            case "Централизованная канализация":
                                waterDisposal = WaterDisposal.CENTRAL;
                                break;
                            case "Нет":
                                waterDisposal = WaterDisposal.NONE;
                                break;
                            case "Выгребная яма":
                                waterDisposal = WaterDisposal.CESSPOOL;
                                break;
                            default:
                                throw new IllegalArgumentException(value);
                        }
                        engineeringSystems.setWaterDisposal(waterDisposal);
                        break;
                    case "Система водостоков":
                        switch (value) {
                            case "Отсутствует":
                                engineeringSystems.setGuttersSystem(false);
                                break;
                            default:
                                throw new IllegalArgumentException(value);
                        }
                        break;
                    case "Газоснабжение":
                        GasSupply gasSupply;
                        switch (value) {
                            case "Центральное":
                            case "центральное":
                                gasSupply = GasSupply.CENTRAL;
                                break;
                            case "нет":
                                gasSupply = GasSupply.NONE;
                                break;
                            default:
                                throw new IllegalArgumentException(value);
                        }
                        engineeringSystems.setGasSupply(gasSupply);
                        break;
                    case "Горячее водоснабжение":
                        switch (value) {
                            case "Нет":
                                engineeringSystems.setHotWaterSupply(false);
                                break;
                            default:
                                throw new IllegalArgumentException(value);
                        }
                        break;
                    case "Система пожаротушения":
                        switch (value) {
                            case "Отсутствует":
                                engineeringSystems.setFireExtinguishingSystem(false);
                                break;
                            default:
                                throw new IllegalArgumentException(value);
                        }
                        break;
                    case "Теплоснабжение":
                        HeatSupply heatSupply;
                        switch (value) {
                            case "Центральная":
                                heatSupply = HeatSupply.CENTRAL;
                                break;
                            case "Нет":
                                heatSupply = HeatSupply.NONE;
                                break;
                            case "Квартирное отопление (котел)":
                                heatSupply = HeatSupply.BOILER;
                                break;
                            default:
                                throw new IllegalArgumentException(value);
                        }
                        engineeringSystems.setHeatSupply(heatSupply);
                        break;
                    case "Холодное водоснабжение":
                        ColdWaterSupply coldWaterSupply;
                        switch (value) {
                            case "Тупиковая":
                                coldWaterSupply = ColdWaterSupply.DEADEND;
                                break;
                            case "Центральное":
                            case "Централизованная (от городской сети)":
                                coldWaterSupply = ColdWaterSupply.CENTRAL;
                                break;
                            case "Нет":
                                coldWaterSupply = ColdWaterSupply.NONE;
                                break;
                            case "Автономное":
                                coldWaterSupply = ColdWaterSupply.NONE;
                                break;
                            default:
                                throw new IllegalArgumentException(value);
                        }
                        engineeringSystems.setColdWaterSupply(coldWaterSupply);
                        break;
                    case "Электроснабжение":
                        ElectricitySupply electricitySupply;
                        switch (value) {
                            case "Центральное":
                                electricitySupply = ElectricitySupply.CENTRAL;
                                break;
                            default:
                                throw new IllegalArgumentException(value);
                        }
                        engineeringSystems.setElectricitySupply(electricitySupply);
                        break;
                    case "Количество вводов в дом, ед.":
                        engineeringSystems.setNumberOfEntriesIntoHouse(Integer.valueOf(value));
                        break;

//Конструктивные элементы
                    case "Мусоропровод":
                        switch (value) {
                            case "Отсутствует":
                                constructionElements.setGarbageChute(false);
                                break;
                            default:
                                throw new IllegalArgumentException(value);
                        }
                        break;
                    case "Несущие стены":
                        LoadBearingWalls loadBearingWalls;
                        switch (value) {
                            case "Стены кирпичные":
                                loadBearingWalls = LoadBearingWalls.BRICK;
                                break;
                            case "Стены деревянные каркасные":
                                loadBearingWalls = LoadBearingWalls.WOODENFRAME;
                                break;
                            case "Стены рубленные из бревен и брусчатые":
                                loadBearingWalls = LoadBearingWalls.LOGSTIMBER;
                                break;
                            case "Стены деревянные, сборно-щитовые":
                                loadBearingWalls = LoadBearingWalls.WOODENPREFABRICATEDPANELS;
                                break;
                            default:
                                throw new IllegalArgumentException(value);
                        }
                        constructionElements.setLoadBearingWalls(loadBearingWalls);
                        break;
                    case "Площадь подвала, кв.м":
                        constructionElements.setBasementArea(Integer.valueOf(value));
                        break;
                    case "Фундамент":
                        Foundation foundation;
                        switch (value) {
                            case "Ленточный":
                                foundation = Foundation.TAPE;
                                break;
                            case "Нет":
                                foundation = Foundation.NONE;
                                break;
                            case "Ж/б крупноблочный":
                                foundation = Foundation.REINFORCEDCONCRETELARGEBLOCK;
                                break;
                            default:
                                throw new IllegalArgumentException(value);
                        }
                        constructionElements.setFoundation(foundation);
                        break;
                    case "Перекрытия":
                        FloorType floorType;
                        switch (value) {
                            case "Перекрытия из железобетонных плит":
                                floorType = FloorType.REINFORCEDCONCRETESLABS;
                                break;
                            case "Перекрытия деревянные неоштукатуренные":
                                floorType = FloorType.WOODENUNPLASTERED;
                                break;
                            case "Перекрытия деревянные оштукатуренные":
                                floorType = FloorType.WOODENPLASTERED;
                                break;
                            default:
                                throw new IllegalArgumentException(value);
                        }
                        constructionElements.setFloorType(floorType);
                        break;


                    default:
                        throw new IllegalArgumentException(tag);
                }
            }
        }
        houseInfo.setGeneralInfo(generalInfo);
        houseInfo.setEngineeringSystems(engineeringSystems);
        houseInfo.setConstructionElements(constructionElements);
    }

    private static void setInfo(HouseInfo houseInfo, Document doc) {
        int index = 0;
        for (Element element : doc.select(".col-md-7").get(1).select("dl").get(0).children()) {
            Elements dt = element.getElementsByTag("dt");
            index += 1;
            if (dt.size() > 0) {
                String tag = ((TextNode) dt
                        .get(0)
                        .getElementsByTag("dt")
                        .get(0)
                        .childNodes()
                        .get(0))
                        .text()
                        .trim();
                if (StringUtils.isEmpty(tag)) {
                    continue;
                }

                String value;
                if (tag.equals("Управляющая компания")) {
                    value = ((TextNode) doc
                            .select(".col-md-7")
                            .get(1)
                            .select("dl")
                            .get(0)
                            .children()
                            .get(index)
                            .childNode(0)
                            .childNodes().get(0))
                            .text()
                            .trim();
                } else {
                    value = ((TextNode) doc
                            .select(".col-md-7")
                            .get(1)
                            .select("dl")
                            .get(0)
                            .children()
                            .get(index)
                            .childNode(0))
                            .text()
                            .trim();
                }

                switch (tag) {
                    case "Адрес":
                        houseInfo.setAddress(value);
                        break;
                    case "Год постройки":
                    case "Год ввода в эксплуатацию":
                        houseInfo.setYear(Integer.valueOf(value));
                        break;
                    case "Количество этажей":
                        houseInfo.setFloor(Integer.valueOf(value));
                        break;
                    case "Тип дома":
                        HouseType houseType;
                        switch (value) {
                            case "Многоквартирный дом":
                                houseType = HouseType.MANYAPPARTMENTS;
                                break;
                            default:
                                throw new IllegalArgumentException(value);
                        }
                        houseInfo.setHouseType(houseType);
                        break;
                    case "Жилых помещений":
                        houseInfo.setApartmentsCount(Integer.valueOf(value));
                        break;
                    case "Серия, тип постройки":
                        MaterialType materialType;
                        switch (value) {
                            case "Кирпичный":
                                materialType = MaterialType.BRICK;
                                break;
                            case "нет":
                                materialType = MaterialType.NONE;
                                break;
                            case "дом":
                                materialType = MaterialType.HOUSE;
                                break;
                            default:
                                throw new IllegalArgumentException(value);
                        }
                        houseInfo.setMaterialType(materialType);
                        break;
                    case "Тип перекрытий":
                        FloorType floorType;
                        switch (value) {
                            case "Перекрытия из железобетонных плит":
                                floorType = FloorType.REINFORCEDCONCRETESLABS;
                                break;
                            case "Перекрытия деревянные неоштукатуренные":
                                floorType = FloorType.WOODENUNPLASTERED;
                                break;
                            case "Перекрытия деревянные оштукатуренные":
                                floorType = FloorType.WOODENPLASTERED;
                                break;
                            default:
                                throw new IllegalArgumentException(value);
                        }
                        houseInfo.setFloorType(floorType);
                        break;
                    case "Материал несущих стен":
                        WallMaterial wallMaterial;
                        switch (value) {
                            case "Стены кирпичные":
                                wallMaterial = WallMaterial.BRICK;
                                break;
                            case "Стены деревянные каркасные":
                                wallMaterial = WallMaterial.WOODENFRAME;
                                break;
                            case "Стены деревянные, сборно-щитовые":
                                wallMaterial = WallMaterial.WOODENPREFABRICATEDPANELS;
                                break;
                            case "Стены рубленные из бревен и брусчатые":
                                wallMaterial = WallMaterial.LOGSTIMBER;
                                break;
                            default:
                                throw new IllegalArgumentException(value);
                        }
                        houseInfo.setWallMaterial(wallMaterial);
                        break;
                    case "Тип мусоропровода":
                        switch (value) {
                            case "Отсутствует":
                                houseInfo.setGarbageChute(false);
                                break;
                            default:
                                throw new IllegalArgumentException(value);
                        }
                        break;
                    case "Дом признан аварийным":
                        switch (value) {
                            case "Нет":
                                houseInfo.setIsEmergency(false);
                                break;
                            case "Да":
                                houseInfo.setIsEmergency(true);
                                break;
                            default:
                                throw new IllegalArgumentException(value);
                        }
                        break;
                    case "Выписка из ЕГРН":
                        break;
                    case "Кадастровый номер":
                        houseInfo.setCadastralNumber(value);
                        break;
                    case "Код ОКТМО":
                        houseInfo.setOktmoCode(value);
                        break;
                    case "Управляющая компания":
                        houseInfo.setManagementCompany(value);
                        break;
                    default:
                        throw new IllegalArgumentException(tag);
                }
            }
        }
    }
}
