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
import com.example.mingkhparser.models.hotwatersupplysystemrisers.HotWaterSupplySystemRisers;
import com.example.mingkhparser.models.roof.*;
import com.example.mingkhparser.models.shutoffvalves.coldwater.ShutoffValvesColdWaterSupplySystem;
import com.example.mingkhparser.models.shutoffvalves.heating.ShutoffValvesHeatingSystem;
import com.example.mingkhparser.models.shutoffvalves.hotwater.ShutoffValvesHotWaterSupplySystem;
import com.example.mingkhparser.models.windows.Windows;
import com.example.mingkhparser.models.windows.WindowsType;
import com.example.mingkhparser.utils.SplitConvertUtils;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
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
import java.util.*;
import java.util.stream.IntStream;

@Service
@Slf4j
@Getter
public class Parser {
    private static Integer COUNTER = 0;
    private final List<HouseInfo> result = new ArrayList<>();

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
        houseInfo.setUrl(url);

        setInfo(houseInfo, doc);
        setGeneralInfoEngineeringSystemsConstructionElements(houseInfo, doc);
        setDetailedInfo(houseInfo, doc);
        setDetailedInfo2(houseInfo, doc);
        result.add(houseInfo);
        log.debug("houseInfo: {}", houseInfo);
    }

    private void setDetailedInfo2(HouseInfo houseInfo, Document doc) {
        Floors floors = houseInfo.getFloors() == null ? new Floors() : houseInfo.getFloors();
        Roof roof = houseInfo.getRoof() == null ? new Roof() : houseInfo.getRoof();
        Windows windows = houseInfo.getWindows() == null ? new Windows() : houseInfo.getWindows();
        Doors doors = new Doors();
        CommonAreasFinishingCoatings commonAreasFinishingCoatings = new CommonAreasFinishingCoatings();
        HeatingSystem heatingSystem = houseInfo.getHeatingSystem() == null ? new HeatingSystem() : houseInfo.getHeatingSystem();
        HeatingSystemRisers heatingSystemRisers = new HeatingSystemRisers();
        ShutoffValvesHeatingSystem shutoffValvesHeatingSystem = new ShutoffValvesHeatingSystem();
        HeatingDevices heatingDevices = new HeatingDevices();
        ColdWaterSystem coldWaterSystem = new ColdWaterSystem();
        ColdWaterSupplySystemRisers coldWaterSupplySystemRisers = new ColdWaterSupplySystemRisers();
        ShutoffValvesColdWaterSupplySystem shutoffValvesColdWaterSupplySystem = new ShutoffValvesColdWaterSupplySystem();
        HotWaterSupplySystemRisers hotWaterSupplySystemRisers = new HotWaterSupplySystemRisers();
        ShutoffValvesHotWaterSupplySystem shutoffValvesHotWaterSupplySystem = new ShutoffValvesHotWaterSupplySystem();
        HotWaterSupplySystem hotWaterSupplySystem = houseInfo.getHotWaterSupplySystem() == null ? new HotWaterSupplySystem() : houseInfo.getHotWaterSupplySystem();

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
                    case "Стояки системы горячего водоснабжения":
                        setHotWaterSupplySystemRisers(tag, value, hotWaterSupplySystemRisers);
                        break;
                    case "Запорная арматура системы горячего водоснабжения":
                        setShutoffValvesHotWaterSupplySystem(tag, value, shutoffValvesHotWaterSupplySystem);
                        break;
                    case "Cистема горячего водоснабжения":
                        setHotWaterSupplySystem(tag, value, hotWaterSupplySystem);
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
        houseInfo.setHotWaterSupplySystemRisers(hotWaterSupplySystemRisers);
        houseInfo.setShutoffValvesHotWaterSupplySystem(shutoffValvesHotWaterSupplySystem);
        houseInfo.setHotWaterSupplySystem(hotWaterSupplySystem);
    }

    private void setShutoffValvesHotWaterSupplySystem(String tag, String value, ShutoffValvesHotWaterSupplySystem shutoffValvesHotWaterSupplySystem) {
        switch (tag) {
            case "Физический износ":
                shutoffValvesHotWaterSupplySystem.setPhysicalDeterioration(SplitConvertUtils.stringToInteger(value));
                break;
            default:
                throw new IllegalArgumentException(tag);
        }
    }

    private void setShutoffValvesColdWaterSupplySystem(String tag, String value, ShutoffValvesColdWaterSupplySystem shutoffValvesColdWaterSupplySystem) {
        switch (tag) {
            case "Физический износ":
                shutoffValvesColdWaterSupplySystem.setPhysicalDeterioration(SplitConvertUtils.stringToInteger(value));
                break;
            default:
                throw new IllegalArgumentException(tag);
        }
    }

    private void setColdWaterSupplySystemRisers(String tag, String value, ColdWaterSupplySystemRisers coldWaterSupplySystemRisers) {
        switch (tag) {
            case "Физический износ":
                coldWaterSupplySystemRisers.setPhysicalDeterioration(SplitConvertUtils.stringToInteger(value));
                break;
            case "Материал сети":
                Set<com.example.mingkhparser.models.coldwatersupplysystemrisers.NetworkMaterial> networkMaterials = new HashSet<>();
                for (String str : value.split(", ")) {
                    switch (str) {
                        case "Сталь оцинкованная":
                            networkMaterials.add(com.example.mingkhparser.models.coldwatersupplysystemrisers.NetworkMaterial.GALVANIZEDSTEEL);
                            break;
                        case "Полимер":
                            networkMaterials.add(com.example.mingkhparser.models.coldwatersupplysystemrisers.NetworkMaterial.POLYMER);
                            break;
                        case "Нет":
                            networkMaterials.add(com.example.mingkhparser.models.coldwatersupplysystemrisers.NetworkMaterial.NONE);
                            break;
                        case "Металлополимер":
                            networkMaterials.add(com.example.mingkhparser.models.coldwatersupplysystemrisers.NetworkMaterial.METALPOLYMER);
                            break;
                        case "Сталь":
                            networkMaterials.add(com.example.mingkhparser.models.coldwatersupplysystemrisers.NetworkMaterial.STEEL);
                            break;
                        case "Чугун":
                            networkMaterials.add(com.example.mingkhparser.models.coldwatersupplysystemrisers.NetworkMaterial.CASTIRON);
                            break;
                        case "Сталь черная":
                            networkMaterials.add(com.example.mingkhparser.models.coldwatersupplysystemrisers.NetworkMaterial.BLACKSTEEL);
                            break;
                        case "Полипропилен":
                            networkMaterials.add(com.example.mingkhparser.models.coldwatersupplysystemrisers.NetworkMaterial.POLYPROPYLENE);
                            break;
                        default:
                            throw new IllegalArgumentException(str);
                    }
                }
                coldWaterSupplySystemRisers.setNetworkMaterials(networkMaterials);
                break;
            default:
                throw new IllegalArgumentException(tag);
        }
    }

    private void setHotWaterSupplySystemRisers(String tag, String value, HotWaterSupplySystemRisers hotWaterSupplySystemRisers) {
        switch (tag) {
            case "Физический износ":
                hotWaterSupplySystemRisers.setPhysicalDeterioration(SplitConvertUtils.stringToInteger(value));
                break;
            default:
                throw new IllegalArgumentException(tag);
        }
    }

    private void setColdWaterSystem(String tag, String value, ColdWaterSystem coldWaterSystem) {
        switch (tag) {
            case "Физический износ":
                coldWaterSystem.setPhysicalDeterioration(SplitConvertUtils.stringToDouble(value));
                break;
            case "Материал сети":
                Set<com.example.mingkhparser.models.coldwatersystem.NetworkMaterial> networkMaterials = new HashSet<>();
                for (String str : value.split(", ")) {
                    switch (str) {
                        case "Сталь оцинкованная":
                            networkMaterials.add(com.example.mingkhparser.models.coldwatersystem.NetworkMaterial.GALVANIZEDSTEEL);
                            break;
                        case "Полимер":
                            networkMaterials.add(com.example.mingkhparser.models.coldwatersystem.NetworkMaterial.POLYMER);
                            break;
                        case "Нет":
                            networkMaterials.add(com.example.mingkhparser.models.coldwatersystem.NetworkMaterial.NONE);
                            break;
                        case "Сталь":
                            networkMaterials.add(com.example.mingkhparser.models.coldwatersystem.NetworkMaterial.STEEL);
                            break;
                        case "Металлополимер":
                            networkMaterials.add(com.example.mingkhparser.models.coldwatersystem.NetworkMaterial.METALPOLYMER);
                            break;
                        case "Чугун":
                            networkMaterials.add(com.example.mingkhparser.models.coldwatersystem.NetworkMaterial.CASTIRON);
                            break;
                        case "Сталь черная":
                            networkMaterials.add(com.example.mingkhparser.models.coldwatersystem.NetworkMaterial.BLACKSTEEL);
                            break;
                        case "Полипропилен":
                            networkMaterials.add(com.example.mingkhparser.models.coldwatersystem.NetworkMaterial.POLYPROPYLENE);
                            break;
                        case "Полиэтилен":
                            networkMaterials.add(com.example.mingkhparser.models.coldwatersystem.NetworkMaterial.POLYETHYLENE);
                            break;
                        default:
                            throw new IllegalArgumentException(str);
                    }
                }
                coldWaterSystem.setNetworkMaterials(networkMaterials);
                break;
            default:
                throw new IllegalArgumentException(tag);
        }
    }

    private void setHeatingDevices(String tag, String value, HeatingDevices heatingDevices) {
        switch (tag) {
            case "Физический износ":
                heatingDevices.setPhysicalDeterioration(SplitConvertUtils.stringToInteger(value));
                break;
            case "Тип отопительных приборов":
                Set<HeatingDevicesType> heatingDevicesTypes = new HashSet<>();
                for (String str : value.split(", ")) {
                    switch (str) {
                        case "Радиатор":
                            heatingDevicesTypes.add(HeatingDevicesType.RADIATOR);
                            break;
                        case "Регистр":
                            heatingDevicesTypes.add(HeatingDevicesType.REGISTER);
                            break;
                        case "Нет":
                            heatingDevicesTypes.add(HeatingDevicesType.NONE);
                            break;
                        case "Конвектор":
                            heatingDevicesTypes.add(HeatingDevicesType.CONVECTOR);
                            break;
                        default:
                            throw new IllegalArgumentException(str);
                    }
                }
                heatingDevices.setHeatingDevicesTypes(heatingDevicesTypes);
                break;
            default:
                throw new IllegalArgumentException(tag);
        }
    }

    private void setShutoffValvesHeatingSystem(String tag, String value, ShutoffValvesHeatingSystem shutoffValvesHeatingSystem) {
        switch (tag) {
            case "Физический износ":
                shutoffValvesHeatingSystem.setPhysicalDeterioration(SplitConvertUtils.stringToInteger(value));
                break;
            default:
                throw new IllegalArgumentException(tag);
        }
    }

    private void setHeatingSystemRisers(String tag, String value, HeatingSystemRisers heatingSystemRisers) {
        switch (tag) {
            case "Физический износ":
                heatingSystemRisers.setPhysicalDeterioration(SplitConvertUtils.stringToInteger(value));
                break;
            case "Тип поквартирной разводки внутридомовой системы отопления":
                ApartmentWiringType apartmentWiringType = switch (value) {
                    case "Вертикальная" -> ApartmentWiringType.VERTICAL;
                    case "Горизонтальная" -> ApartmentWiringType.HORIZONTAL;
                    case "Нет" -> ApartmentWiringType.NONE;
                    default -> throw new IllegalArgumentException(value);
                };
                heatingSystemRisers.setApartmentWiringType(apartmentWiringType);
                break;
            case "Материал":
                Set<com.example.mingkhparser.models.heatingsystemrisers.MaterialType> materialTypes = new HashSet<>();
                for (String str : value.split(", ")) {
                    switch (str) {
                        case "Сталь оцинкованная":
                            materialTypes.add(com.example.mingkhparser.models.heatingsystemrisers.MaterialType.GALVANIZEDSTEEL);
                            break;
                        case "Полимер":
                            materialTypes.add(com.example.mingkhparser.models.heatingsystemrisers.MaterialType.POLYMER);
                            break;
                        case "Нет":
                            materialTypes.add(com.example.mingkhparser.models.heatingsystemrisers.MaterialType.NONE);
                            break;
                        case "Сталь":
                        case "Сталь черная":
                            materialTypes.add(com.example.mingkhparser.models.heatingsystemrisers.MaterialType.STEEL);
                            break;
                        case "Чугун":
                            materialTypes.add(com.example.mingkhparser.models.heatingsystemrisers.MaterialType.CASTIRON);
                            break;
                        case "Металлополимер":
                            materialTypes.add(com.example.mingkhparser.models.heatingsystemrisers.MaterialType.METALPOLYMER);
                            break;
                        case "Полипропилен":
                            materialTypes.add(com.example.mingkhparser.models.heatingsystemrisers.MaterialType.POLYPROPYLENE);
                            break;
                        default:
                            throw new IllegalArgumentException(str);
                    }
                }
                heatingSystemRisers.setMaterialTypes(materialTypes);
                break;
            default:
                throw new IllegalArgumentException(tag);
        }
    }

    private void setHeatingSystem(String tag, String value, HeatingSystem heatingSystem) {
        switch (tag) {
            case "Физический износ":
                heatingSystem.setPhysicalDeterioration(SplitConvertUtils.stringToDouble(value));
                break;
            case "Материал сети":
                Set<com.example.mingkhparser.models.heatingsystem.NetworkMaterial> networkMaterials = new HashSet<>();
                for (String str : value.split(", ")) {
                    switch (str) {
                        case "Сталь оцинкованная":
                            networkMaterials.add(com.example.mingkhparser.models.heatingsystem.NetworkMaterial.GALVANIZEDSTEEL);
                            break;
                        case "Нет":
                            networkMaterials.add(com.example.mingkhparser.models.heatingsystem.NetworkMaterial.NONE);
                            break;
                        case "Полимер":
                            networkMaterials.add(com.example.mingkhparser.models.heatingsystem.NetworkMaterial.POLYMER);
                            break;
                        case "Сталь":
                        case "Сталь черная":
                            networkMaterials.add(com.example.mingkhparser.models.heatingsystem.NetworkMaterial.STEEL);
                            break;
                        case "Чугун":
                            networkMaterials.add(com.example.mingkhparser.models.heatingsystem.NetworkMaterial.CASTIRON);
                            break;
                        case "Металлополимер":
                            networkMaterials.add(com.example.mingkhparser.models.heatingsystem.NetworkMaterial.METALPOLYMER);
                            break;
                        case "Полипропилен":
                            networkMaterials.add(com.example.mingkhparser.models.heatingsystem.NetworkMaterial.POLYPROPYLENE);
                            break;
                        default:
                            throw new IllegalArgumentException(str);
                    }
                }
                heatingSystem.setNetworkMaterials(networkMaterials);
                break;
            case "Материал теплоизоляции сети":
                Set<ThermalInsulationMaterial> thermalInsulationMaterials = new HashSet<>();
                for (String str : value.split(", ")) {
                    switch (str) {
                        case "Вспененный полиэтилен (энергофлекс)":
                            thermalInsulationMaterials.add(ThermalInsulationMaterial.FOAMEDPOLYETHYLENE);
                            break;
                        case "Нет":
                            thermalInsulationMaterials.add(ThermalInsulationMaterial.NONE);
                            break;
                        case "Минеральная вата с покрытием из алюминиевой фольги":
                            thermalInsulationMaterials.add(ThermalInsulationMaterial.MINERALWOOLCOATEDWITHALUMINUMFOIL);
                            break;
                        case "Минеральная вата с покрытием из оцинкованной стали":
                            thermalInsulationMaterials.add(ThermalInsulationMaterial.MINERALWOOLCOATEDWITHGALVANIZEDSTEEL);
                            break;
                        case "Минеральная вата с покрытием":
                            thermalInsulationMaterials.add(ThermalInsulationMaterial.MINERALWOOLCOATED);
                            break;
                        case "Асбест под деревянной основой (устар.)":
                            thermalInsulationMaterials.add(ThermalInsulationMaterial.ASBESTOSUNDERWOODENBASE);
                            break;
                        case "Полимер":
                            thermalInsulationMaterials.add(ThermalInsulationMaterial.POLYMER);
                            break;
                        case "Сталь оцинкованная":
                            thermalInsulationMaterials.add(ThermalInsulationMaterial.GALVANIZEDSTEEL);
                            break;
                        case "Пенополиуретановое напыление":
                            thermalInsulationMaterials.add(ThermalInsulationMaterial.POLYURETHANEFOAMSPRAYING);
                            break;
                        default:
                            throw new IllegalArgumentException(str);
                    }
                }
                heatingSystem.setThermalInsulationMaterials(thermalInsulationMaterials);
                break;
            default:
                throw new IllegalArgumentException(tag);
        }
    }

    private void setCommonAreasFinishingCoatings(String tag, String value, CommonAreasFinishingCoatings commonAreasFinishingCoatings) {
        switch (tag) {
            case "Физический износ":
                commonAreasFinishingCoatings.setPhysicalDeterioration(SplitConvertUtils.stringToDouble(value));
                break;
            default:
                throw new IllegalArgumentException(tag);
        }
    }

    private void setDoors(String tag, String value, Doors doors) {
        switch (tag) {
            case "Физический износ":
                doors.setPhysicalDeterioration(SplitConvertUtils.stringToDouble(value));
                break;
            default:
                throw new IllegalArgumentException(tag);
        }
    }

    private void setWindows(String tag, String value, Windows windows) {
        switch (tag) {
            case "Физический износ":
                windows.setPhysicalDeterioration(SplitConvertUtils.stringToDouble(value));
                break;
            case "Материал окон":
                Set<WindowsType> windowsTypes = new HashSet<>();
                for (String str : value.split(", ")) {
                    switch (str) {
                        case "Деревянные":
                            windowsTypes.add(WindowsType.WOODEN);
                            break;
                        case "Пластиковые":
                            windowsTypes.add(WindowsType.PLASTIC);
                            break;
                        case "нет":
                            windowsTypes.add(WindowsType.NONE);
                            break;
                        default:
                            throw new IllegalArgumentException(str);
                    }
                }
                windows.setWindowsTypes(windowsTypes);
                break;
            default:
                throw new IllegalArgumentException(tag);
        }
    }

    private void setRoof(String tag, String value, Roof roof) {
        switch (tag) {
            case "Форма крыши":
                RoofShape roofShape = switch (value) {
                    case "Плоская" -> RoofShape.FLAT;
                    case "Скатная", "Односкатная" -> RoofShape.SLOPING;
                    case "Двускатная" -> RoofShape.GABLE;
                    case "Вальмовая" -> RoofShape.HIP;
                    case "Вальмовая сложной формы" -> RoofShape.HIPCOMPLEXSHAPE;
                    case "Полувальмовая" -> RoofShape.HALFHIP;
                    case "Шатровая" -> RoofShape.TENT;
                    case "Нет" -> RoofShape.NONE;
                    default -> throw new IllegalArgumentException(value);
                };
                roof.setRoofShape(roofShape);
                break;
            case "Утепляющие слои чердачных перекрытий":
                Set<InsulatingLayers> insulatingLayers = new HashSet<>();
                for (String str : value.split(", ")) {
                    switch (str) {
                        case "Керамзит или шлак":
                            insulatingLayers.add(InsulatingLayers.EXPANDEDCLAYSLAG);
                            break;
                        case "Минеральная вата":
                        case "Минераловатные плиты":
                            insulatingLayers.add(InsulatingLayers.MINERALWOOL);
                            break;
                        case "нет":
                            insulatingLayers.add(InsulatingLayers.NONE);
                            break;
                        case "Пенобетон":
                            insulatingLayers.add(InsulatingLayers.FOAMCONCRETE);
                            break;
                        default:
                            throw new IllegalArgumentException(str);
                    }
                }
                roof.setInsulatingLayers(insulatingLayers);
                break;
            case "Вид несущей части":
                Set<BearingType> bearingTypes = new HashSet<>();
                for (String str : value.split(", ")) {
                    switch (str) {
                        case "Железобетонные сборные (чердачные)":
                            bearingTypes.add(BearingType.PREFABRICATEDREINFORCEDCONCRETE);
                            break;
                        case "Деревянные":
                            bearingTypes.add(BearingType.WOODEN);
                            break;
                        case "Стропильная":
                            bearingTypes.add(BearingType.RAFTER);
                            break;
                        case "Нет":
                            bearingTypes.add(BearingType.NONE);
                            break;
                        case "Совмещенные из сборных железобетонных слоистых панелей":
                        case "Железобетонная совмещенная":
                            bearingTypes.add(BearingType.COMBINEDPRECASTCONCRETELAMINATEDPANELS);
                            break;
                        case "Ж/б плиты":
                            bearingTypes.add(BearingType.REINFORCEDCONCRETESLABS);
                            break;
                        default:
                            throw new IllegalArgumentException(str);
                    }
                }
                roof.setBearingTypes(bearingTypes);
                break;
            case "Тип кровли":
                Set<RoofType> roofTypes = new HashSet<>();
                for (String str : value.split(", ")) {
                    switch (str) {
                        case "Рулонная":
                            roofTypes.add(RoofType.ROLL);
                            break;
                        case "Волнистые листы":
                            roofTypes.add(RoofType.CORRUGATEDSHEETS);
                            break;
                        case "Шиферная":
                            roofTypes.add(RoofType.SLATE);
                            break;
                        case "Металлическая фальцевая":
                            roofTypes.add(RoofType.METALSEAM);
                            break;
                        case "Железо":
                            roofTypes.add(RoofType.IRON);
                            break;
                        case "Стальная (металлическая)":
                            roofTypes.add(RoofType.STEELMETAL);
                            break;
                        case "Железо по деревянной обрешетке":
                            roofTypes.add(RoofType.IRONONWOODSHEATHING);
                            break;
                        case "Металлический профлист":
                            roofTypes.add(RoofType.METALPROFILEDSHEET);
                            break;
                        case "Мягкая":
                            roofTypes.add(RoofType.SOFT);
                            break;
                        case "Металлическая волнистая":
                            roofTypes.add(RoofType.METALWAVY);
                            break;
                        case "Рубероид":
                            roofTypes.add(RoofType.RUBEROID);
                            break;
                        case "Рулонная по железобетонным плитам":
                            roofTypes.add(RoofType.ROLLEDONREINFORCEDCONCRETESLABS);
                            break;
                        case "Оцинкованный профлист":
                            roofTypes.add(RoofType.GALVANIZEDCORRUGATEDSHEET);
                            break;
                        case "Профнастил":
                            roofTypes.add(RoofType.CORRUGATEDSHEET);
                            break;
                        case "Нет":
                            roofTypes.add(RoofType.NONE);
                            break;
                        default:
                            throw new IllegalArgumentException(str);
                    }
                }
                roof.setRoofTypes(roofTypes);
                break;
            case "Физический износ кровли":
                roof.setPhysicalDeterioration(SplitConvertUtils.stringToInteger(value));
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
                FloorType floorType = switch (value) {
                    case "Перекрытия из железобетонных плит", "Перекрытия железобетонные", "Железобетон", "Плоские железобетонные плиты" ->
                            FloorType.REINFORCEDCONCRETESLABS;
                    case "Перекрытия деревянные неоштукатуренные" -> FloorType.WOODENUNPLASTERED;
                    case "Перекрытия деревянные оштукатуренные" -> FloorType.WOODENPLASTERED;
                    case "Деревянные отепленные" -> FloorType.WOODENHEATED;
                    case "Иные" -> FloorType.OTHER;
                    case "Перекрытия из сборного железобетонного настила" -> FloorType.PRECASTCONCRETESLABS;
                    case "Перекрытия из сборных и монолитных сплошных плит" ->
                            FloorType.PREFABRICATEDANDMONOLITHICSOLIDSLABS;
                    case "Нет" -> FloorType.NONE;
                    default -> throw new IllegalArgumentException(value);
                };
                floors.setFloorType(floorType);
                break;
            case "Физический износ":
                floors.setPhysicalDeterioration(SplitConvertUtils.stringToDouble(value));
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
        HeatingSystem heatingSystem = new HeatingSystem();
        Windows windows = new Windows();

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
                    case "Система отопления":
                        setHeatingSystem(tag, value, heatingSystem);
                        break;
                    case "Окна":
                        setWindows(tag, value, windows);
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
        houseInfo.setHeatingSystem(heatingSystem);
        houseInfo.setWindows(windows);
    }

    private void setFacade(String tag, String value, Facade facade) {
        switch (tag) {
            case "Тип наружных стен":
                Set<WallMaterial> wallMaterials = new HashSet<>();
                String[] values = value.split(", ");
                for (String string : values) {
                    switch (string) {
                        case "Стены кирпичные":
                            wallMaterials.add(WallMaterial.BRICK);
                            break;
                        case "Стены деревянные каркасные":
                            wallMaterials.add(WallMaterial.WOODENFRAME);
                            break;
                        case "Стены рубленные из бревен и брусчатые":
                            wallMaterials.add(WallMaterial.LOGSTIMBER);
                            break;
                        case "сборно-щитовые":
                            wallMaterials.add(WallMaterial.PREFABRICATEDPANELS);
                            break;
                        case "Стены деревянные":
                            wallMaterials.add(WallMaterial.WOODEN);
                            break;
                        case "Стены железобетонные":
                        case "Железобетонные панели":
                            wallMaterials.add(WallMaterial.REINFORCEDCONCRETE);
                            break;
                        case "Стены панельные":
                        case "Стены из несущих панелей":
                        case "Крупнопанельные":
                            wallMaterials.add(WallMaterial.PANEL);
                            break;
                        case "Стены из крупноразмерных блоков и однослойных несущих панелей":
                            wallMaterials.add(WallMaterial.LARGEBLOCKSANDSINGLELAYERLOADBEARINGPANELS);
                            break;
                        case "Ж/б блоки":
                            wallMaterials.add(WallMaterial.REINFORCEDCONCRETEBLOCK);
                            break;
                        case "Железобетонные плиты":
                            wallMaterials.add(WallMaterial.LAMINATEDREINFORCEDCONCRETEPANELS);
                            break;
                        default:
                            throw new IllegalArgumentException(value);
                    }
                }

                facade.setOuterWallsMaterials(wallMaterials);
                break;
            case "Тип наружного утепления фасада":
                ExternalInsulationType externalInsulationType = switch (value) {
                    case "Нет" -> ExternalInsulationType.NONE;
                    case "Утепление с защитным штукатурным слоем" ->
                            ExternalInsulationType.INSULATIONWITHAPROTECTIVEPLASTERLAYER;
                    case "Минвата", "Слой из минеральных плит" -> ExternalInsulationType.MINERALWOOL;
                    case "Навесной вентилируемый фасад" -> ExternalInsulationType.HINGEDVENTILATEDFACADE;
                    default -> throw new IllegalArgumentException(value);
                };
                facade.setExternalInsulationType(externalInsulationType);
                break;
            case "Материал отделки фасада":
                Set<FacadeFinishingMaterial> facadeFinishingMaterials = new HashSet<>();
                for (String str : value.split(", ")) {
                    switch (str) {
                        case "без отделки":
                        case "Нет":
                            facadeFinishingMaterials.add(FacadeFinishingMaterial.WITHOUTFINISHING);
                            break;
                        case "окраска":
                            facadeFinishingMaterials.add(FacadeFinishingMaterial.PAINTING);
                            break;
                        case "Иной":
                            facadeFinishingMaterials.add(FacadeFinishingMaterial.OTHER);
                            break;
                        case "окраска по штукатурке":
                            facadeFinishingMaterials.add(FacadeFinishingMaterial.PAINTINGONPLASTER);
                            break;
                        case "Штукатурка":
                            facadeFinishingMaterials.add(FacadeFinishingMaterial.PLASTER);
                            break;
                        case "Обшивка тёсом":
                            facadeFinishingMaterials.add(FacadeFinishingMaterial.PANELING);
                            break;
                        case "обшивочная доска окрашенная":
                            facadeFinishingMaterials.add(FacadeFinishingMaterial.PANELINGPAINTING);
                            break;
                        case "обшивочная доска не окрашенная":
                            facadeFinishingMaterials.add(FacadeFinishingMaterial.PANELINGUNPAINTING);
                            break;
                        case "Дерево":
                            facadeFinishingMaterials.add(FacadeFinishingMaterial.WOOD);
                            break;
                        case "панель с заводской отделкой":
                            facadeFinishingMaterials.add(FacadeFinishingMaterial.FACTORYFINISHED);
                            break;
                        case "Сайдинг":
                            facadeFinishingMaterials.add(FacadeFinishingMaterial.SIDING);
                            break;
                        case "наружная облицовка кирпичом":
                            facadeFinishingMaterials.add(FacadeFinishingMaterial.BRICK);
                            break;
                        case "облицовка керамической плиткой":
                            facadeFinishingMaterials.add(FacadeFinishingMaterial.CERAMICTILE);
                            break;
                        default:
                            throw new IllegalArgumentException(str);
                    }
                }
                facade.setFacadeFinishingMaterials(facadeFinishingMaterials);
                break;
            case "Физический износ":
                facade.setPhysicalDeterioration(SplitConvertUtils.stringToDouble(value));
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
                Set<WallMaterial> wallMaterials = new HashSet<>();
                String[] values = value.split(", ");
                for (String string : values) {
                    switch (string) {
                        case "Стены кирпичные":
                            wallMaterials.add(WallMaterial.BRICK);
                            break;
                        case "Стены деревянные каркасные":
                            wallMaterials.add(WallMaterial.WOODENFRAME);
                            break;
                        case "Стены рубленные из бревен и брусчатые":
                        case "Бревенчатые":
                            wallMaterials.add(WallMaterial.LOGSTIMBER);
                            break;
                        case "сборно-щитовые":
                        case "Стены из сборно-щитовых панелей":
                            wallMaterials.add(WallMaterial.PREFABRICATEDPANELS);
                            break;
                        case "Стены деревянные":
                            wallMaterials.add(WallMaterial.WOODEN);
                            break;
                        case "Железобетонные":
                            wallMaterials.add(WallMaterial.REINFORCEDCONCRETE);
                            break;
                        case "Стены из слоистых железобетонных панелей":
                        case "Стены из ж/б панелей":
                        case "Железобетонные плиты":
                            wallMaterials.add(WallMaterial.LAMINATEDREINFORCEDCONCRETEPANELS);
                            break;
                        case "Панельные":
                        case "Стены из несущих панелей":
                        case "Стены крупнопанельные":
                            wallMaterials.add(WallMaterial.PANEL);
                            break;
                        case "Стены из крупноразмерных блоков и однослойных несущих панелей":
                            wallMaterials.add(WallMaterial.LARGEBLOCKSANDSINGLELAYERLOADBEARINGPANELS);
                            break;
                        case "Каркасно-засыпные":
                            wallMaterials.add(WallMaterial.FRAMEFILL);
                            break;
                        case "Арболитовые панели":
                            wallMaterials.add(WallMaterial.ARBOLITEPANELS);
                            break;
                        case "null":
                            wallMaterials.add(WallMaterial.NULL);
                            break;
                        case "Гипсолитовые":
                            wallMaterials.add(WallMaterial.GYPSOLITIC);
                            break;
                        default:
                            throw new IllegalArgumentException(string);
                    }
                }
                innerWalls.setWallMaterials(wallMaterials);
                break;
            case "Физический износ":
                innerWalls.setPhysicalDeterioration(SplitConvertUtils.stringToDouble(value));
                break;
            default:
                throw new IllegalArgumentException(tag);
        }
    }

    private void setFoundation(String tag, String value, com.example.mingkhparser.models.foundation.Foundation foundation) {
        switch (tag) {
            case "Тип фундамента":
                FoundationType foundationType = switch (value) {
                    case "Ленточный" -> FoundationType.TAPE;
                    case "Ж/б крупноблочный", "Ж/б" -> FoundationType.REINFORCEDCONCRETELARGEBLOCK;
                    case "Монолитный ленточный железобетонный и столбчатый ростверк по свайному основанию" ->
                            FoundationType.MONOLITHICSTRIPREINFORCEDCONCRETEANDCOLUMNARGRILLAGEONAPILE;
                    case "Столбчатый (столбовой)" -> FoundationType.COLUMNARPILLAR;
                    case "Нет" -> FoundationType.NONE;
                    default -> throw new IllegalArgumentException(value);
                };
                foundation.setFoundationType(foundationType);
                break;
            case "Материал фундамента":
                FoundationMaterial foundationMaterial = switch (value) {
                    case "Железобетонные блоки" -> FoundationMaterial.REINFORCEDCONCRETEBLOCKS;
                    case "Кирпич керамический" -> FoundationMaterial.CERAMICBRICK;
                    case "Монолитный железобетон" -> FoundationMaterial.REINFORCEDCONCRETESMONOLITHIC;
                    case "Сборный железобетон" -> FoundationMaterial.PRECASTREINFORCEDCONCRETE;
                    case "Нет" -> FoundationMaterial.NONE;
                    case "Железобетон" -> FoundationMaterial.REINFORCEDCONCRETE;
                    case "Кирпич" -> FoundationMaterial.BRICK;
                    case "Бутобетон" -> FoundationMaterial.RUBBLECONCRETE;
                    case "Бутовый камень" -> FoundationMaterial.RUBBLESTONE;
                    case "Бетон" -> FoundationMaterial.CONCRETE;
                    default -> throw new IllegalArgumentException(value);
                };
                foundation.setFoundationMaterial(foundationMaterial);
                break;
            case "Площадь отмостки":
                foundation.setBlindArea(SplitConvertUtils.stringToDouble(value));
                break;
            case "Физический износ":
                foundation.setPhysicalDeterioration(SplitConvertUtils.stringToDouble(value));
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
                electricitySupplySystem.setPhysicalDeterioration(SplitConvertUtils.stringToInteger(value));
                break;
            case "Год проведения последнего капитального ремонта":
                electricitySupplySystem.setLastOverhaulYear(Integer.valueOf(value));
                break;
            case "Количество вводов системы электроснабжения":
                electricitySupplySystem.setPowerSupplyInputNumbers(SplitConvertUtils.stringToInteger(value));
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
                GasSupplySystemType gasSupplySystemType = switch (value) {
                    case "центральное" -> GasSupplySystemType.CENTRAL;
                    case "нет" -> GasSupplySystemType.NONE;
                    case "Газопровод низкого давления – подача природного газа в крышную котельную" ->
                            GasSupplySystemType.LOWPRESSUREGASPIPELINEROOFBOILERROOM;
                    case "баллонный газ" -> GasSupplySystemType.BOTTLEDGAS;
                    default -> throw new IllegalArgumentException(value);
                };
                gasSupplySystem.setGasSupplySystemType(gasSupplySystemType);
                break;
            case "Количество вводов системы газоснабжения":
                gasSupplySystem.setGasSupplySystemInletsNumber(SplitConvertUtils.stringToInteger(value));
                break;
            default:
                throw new IllegalArgumentException(tag);
        }
    }

    private void setDrainageSystem(String tag, String value, DrainageSystem drainageSystem) {
        switch (tag) {
//Система водоотведения
            case "Физический износ":
                drainageSystem.setPhysicalDeterioration(SplitConvertUtils.stringToInteger(value));
                break;
            case "Год проведения последнего капитального ремонта":
                drainageSystem.setLastOverhaulYear(Integer.valueOf(value));
                break;
            case "Тип системы водоотведения":
                DrainageSystemType drainageSystemType = switch (value) {
                    case "Централизованная канализация" -> DrainageSystemType.CENTRALIZEDSEWERAGE;
                    case "Нет" -> DrainageSystemType.NONE;
                    case "Выгребная яма" -> DrainageSystemType.CESSPOOL;
                    case "Локальная канализация (септик)" -> DrainageSystemType.LOCALSEWERAGESEPTIC;
                    default -> throw new IllegalArgumentException(value);
                };
                drainageSystem.setDrainageSystemType(drainageSystemType);
                break;
            case "Материал сети":
                Set<com.example.mingkhparser.models.drainagesystem.NetworkMaterial> networkMaterials = new HashSet<>();
                for (String str : value.split(", ")) {
                    switch (str) {
                        case "чугун":
                            networkMaterials.add(com.example.mingkhparser.models.drainagesystem.NetworkMaterial.CASTIRON);
                            break;
                        case "Нет":
                            networkMaterials.add(com.example.mingkhparser.models.drainagesystem.NetworkMaterial.NONE);
                            break;
                        case "пластик":
                            networkMaterials.add(com.example.mingkhparser.models.drainagesystem.NetworkMaterial.PLASTIC);
                            break;
                        case "асбестоцемент":
                            networkMaterials.add(com.example.mingkhparser.models.drainagesystem.NetworkMaterial.ASBESTOSCEMENT);
                            break;
                        default:
                            throw new IllegalArgumentException(str);
                    }
                }
                drainageSystem.setNetworkMaterials(networkMaterials);
                break;
            default:
                throw new IllegalArgumentException(tag);
        }
    }

    private void setHotWaterSupplySystem(String tag, String value, HotWaterSupplySystem hotWaterSupplySystem) {
        switch (tag) {
//Cистема горячего водоснабжения
            case "Тип системы горячего водоснабжения":
                Set<HotWaterSystemType> hotWaterSystemTypes = new HashSet<>();
                for (String string : value.split(", ")) {
                    switch (string) {
                        case "Нет":
                            hotWaterSystemTypes.add(HotWaterSystemType.NONE);
                            break;
                        case "Индивидуальный котел":
                            hotWaterSystemTypes.add(HotWaterSystemType.INDIVIDUALBOILER);
                            break;
                        case "Кольцевая или с закольцованными вводами":
                            hotWaterSystemTypes.add(HotWaterSystemType.RINGORWITHLOOPEDINPUTS);
                            break;
                        case "не известен":
                            hotWaterSystemTypes.add(HotWaterSystemType.UNKNOWN);
                            break;
                        case "Центральное":
                        case "Центральное (закрытая система)":
                            hotWaterSystemTypes.add(HotWaterSystemType.CENTRAL);
                            break;
                        case "Газовые колонки (ВДГО)":
                            hotWaterSystemTypes.add(HotWaterSystemType.GASWATERHEATERS);
                            break;
                        case "Тупиковая":
                            hotWaterSystemTypes.add(HotWaterSystemType.DEADEND);
                            break;
                        case "П-образная":
                            hotWaterSystemTypes.add(HotWaterSystemType.USHAPED);
                            break;
                        case "с нижней разводкой магистралей":
                            hotWaterSystemTypes.add(HotWaterSystemType.LOWERROUTINGOFHIGHWAYS);
                            break;
                        default:
                            throw new IllegalArgumentException(string);
                    }
                }
                hotWaterSupplySystem.setHotWaterSystemTypes(hotWaterSystemTypes);
                break;
            case "Физический износ":
                hotWaterSupplySystem.setPhysicalDeterioration(SplitConvertUtils.stringToDouble(value));
                break;
            case "Материал сети":
                Set<NetworkMaterial> networkMaterials = new HashSet<>();
                for (String str : value.split(", ")) {
                    switch (str) {
                        case "Нет":
                            networkMaterials.add(NetworkMaterial.NONE);
                            break;
                        case "Металлополимер":
                            networkMaterials.add(NetworkMaterial.METALPOLYMER);
                            break;
                        case "Чугун":
                            networkMaterials.add(NetworkMaterial.CASTIRON);
                            break;
                        case "Сталь":
                        case "Сталь черная":
                            networkMaterials.add(NetworkMaterial.STEEL);
                            break;
                        case "Полимер":
                            networkMaterials.add(NetworkMaterial.POLYMER);
                            break;
                        case "Сталь оцинкованная":
                            networkMaterials.add(NetworkMaterial.GALVANIZEDSTEEL);
                            break;
                        case "Полипропилен":
                            networkMaterials.add(NetworkMaterial.POLYPROPYLENE);
                            break;
                        case "Медь":
                            networkMaterials.add(NetworkMaterial.COPPER);
                            break;
                        default:
                            throw new IllegalArgumentException(str);
                    }
                }
                hotWaterSupplySystem.setNetworkMaterials(networkMaterials);
                break;
            case "Материал теплоизоляции сети":
                NetworkThermalInsulationMaterial networkThermalInsulationMaterial = switch (value) {
                    case "Нет" -> NetworkThermalInsulationMaterial.NONE;
                    case "Минеральная вата с покрытием из алюминиевой фольги" ->
                            NetworkThermalInsulationMaterial.MINERALWOOLCOATEDWITHALUMINUMFOIL;
                    case "Вспененный полиэтилен (энергофлекс)" -> NetworkThermalInsulationMaterial.FOAMEDPOLYETHYLENE;
                    case "Минеральная вата с покрытием" -> NetworkThermalInsulationMaterial.MINERALWOOLCOATED;
                    default -> throw new IllegalArgumentException(value);
                };
                hotWaterSupplySystem.setNetworkThermalInsulationMaterial(networkThermalInsulationMaterial);
                break;
            case "Материал стояков":
                Set<RisersMaterial> risersMaterials = new HashSet<>();
                for (String str : value.split(", ")) {
                    switch (str) {
                        case "Нет":
                            risersMaterials.add(RisersMaterial.NONE);
                            break;
                        case "Чугун":
                            risersMaterials.add(RisersMaterial.CASTIRON);
                            break;
                        case "Сталь черная":
                        case "Сталь":
                            risersMaterials.add(RisersMaterial.STEEL);
                            break;
                        case "Полимер":
                            risersMaterials.add(RisersMaterial.POLYMER);
                            break;
                        case "Сталь оцинкованная":
                            risersMaterials.add(RisersMaterial.GALVANIZEDSTEEL);
                            break;
                        case "Полипропилен":
                            risersMaterials.add(RisersMaterial.POLYPROPYLENE);
                            break;
                        default:
                            throw new IllegalArgumentException(str);
                    }
                }
                hotWaterSupplySystem.setRisersMaterials(risersMaterials);
                break;
            default:
                throw new IllegalArgumentException(tag);
        }
    }

    private static void setGeneralInfoEngineeringSystemsConstructionElements(HouseInfo houseInfo, Document doc) {
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
                        HouseCondition houseCondition = switch (value) {
                            case "Исправный" -> HouseCondition.SERVICEABLE;
                            case "Аварийный" -> HouseCondition.EMERGENCY;
                            default -> throw new IllegalArgumentException(value);
                        };
                        generalInfo.setHouseCondition(houseCondition);
                        break;
                    case "Количество квартир":
                        generalInfo.setApartmentsCount(Integer.valueOf(value));
                        break;
                    case "Количество нежилых помещений":
                        generalInfo.setNonResidentialPremises(Integer.valueOf(value));
                        break;
                    case "Количество лоджий":
                        generalInfo.setLoggiasNumber(Integer.valueOf(value));
                        break;
                    case "Количество балконов":
                        generalInfo.setBalconyNumber(Integer.valueOf(value));
                        break;
                    case "Класс энергетической эффективности":
                        EnergyEfficiencyClass energyEfficiencyClass = switch (value) {
                            case "Не присвоен", "Нет" -> EnergyEfficiencyClass.NOTASSIGNED;
                            default -> throw new IllegalArgumentException(value);
                        };
                        generalInfo.setEnergyEfficiencyClass(energyEfficiencyClass);
                        break;
                    case "Количество подъездов":
                        generalInfo.setNumberOfEntrances(Integer.valueOf(value));
                        break;
                    case "Количество лифтов":
                        generalInfo.setElevatorsNumber(Integer.valueOf(value));
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
                        RepairFormation repairFormation = switch (value) {
                            case "На счете регионального оператора", "На специальном счете у регионального оператора" ->
                                    RepairFormation.REGIONALOPERATORACCOUNT;
                            case "На специальном счете организации" -> RepairFormation.ORGANIZATIONSPECIALACCOUNT;
                            case "Не определен" -> RepairFormation.INDEFINED;
                            default -> throw new IllegalArgumentException(value);
                        };
                        generalInfo.setRepairFormation(repairFormation);
                        break;
                    case "Площадь парковки м2":
                        generalInfo.setParkingArea(Double.parseDouble(value));
                        break;
                    case "Наличие в подъездах приспособлений для нужд маломобильных групп населения":
                        switch (value) {
                            case "Нет":
                                generalInfo.setDisablePeopleDevices(false);
                                break;
                            case "Да":
                                generalInfo.setDisablePeopleDevices(true);
                                break;
                            default:
                                throw new IllegalArgumentException(value);
                        }
                        break;
                    case "Тип дома":
                        HouseType houseType;
                        if (value.equals("Многоквартирный дом")) {
                            houseType = HouseType.MANYAPPARTMENTS;
                        } else {
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
                        generalInfo.setWearOfBuilding(Double.valueOf(value));
                        break;
                    case "Дата, на которую установлен износ здания":
                        generalInfo.setWearCalculationDate(LocalDate.parse(value, DateTimeFormatter.ofPattern("dd.MM.yyyy")));
                        break;
                    case "Основание признания дома аварийным":
                        generalInfo.setUnsafeRecognizingReason(value);
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
                        Set<MaterialType> materialTypes = new HashSet<>();
                        for (String str : value.split(", ")) {
                            switch (str) {
                                case "Кирпичный":
                                case "кирпичный":
                                case "Жилой дом кирпичный":
                                    materialTypes.add(MaterialType.BRICK);
                                    break;
                                case "нет":
                                case "ytn":
                                    materialTypes.add(MaterialType.NONE);
                                    break;
                                case "дом":
                                case "жилое":
                                case "жилой дом":
                                case "Жилой дом":
                                    materialTypes.add(MaterialType.HOUSERESIDENTIAL);
                                    break;
                                case "информация отсутствует":
                                case "byajhvfwbz jncencndetn":
                                case "информация отсутсвует":
                                case "не известен":
                                case "нет информации":
                                case "нет данных":
                                case "нет сведений":
                                case "информация отсутвует":
                                case "-":
                                    materialTypes.add(MaterialType.UNKNOWN);
                                    break;
                                case "Панельный":
                                case "панельный":
                                    materialTypes.add(MaterialType.PANEL);
                                    break;
                                case "проект на строительство 06/13-с-ПЗ":
                                    materialTypes.add(MaterialType.PROJECT0613);
                                    break;
                                case "Деревянные":
                                    materialTypes.add(MaterialType.WOODEN);
                                    break;
                                case "индивидуальный":
                                case "индивидуальное":
                                    materialTypes.add(MaterialType.INDIVIDUAL);
                                    break;
                                case "многоквартирный дом блокированной застройки":
                                case "дом блокированной застройки":
                                    materialTypes.add(MaterialType.BLOCKOFFLATS);
                                    break;
                                case "двухквартирный жилой дом блокированной застройки":
                                    materialTypes.add(MaterialType.TWOBLOCKOFFLATS);
                                    break;
                                default:
                                    throw new IllegalArgumentException(str);
                            }
                        }
                        generalInfo.setMaterialTypes(materialTypes);
                        break;
                    case "Статус объекта культурного наследия":
                        switch (value) {
                            case "Нет":
                                generalInfo.setIsCulturalHeritage(false);
                                break;
                            case "Да":
                                generalInfo.setIsCulturalHeritage(true);
                                break;
                            default:
                                throw new IllegalArgumentException(value);
                        }
                        break;
//Инженерные системы
                    case "Вентиляция":
                        Ventilation ventilation = switch (value) {
                            case "Приточно-вытяжная вентиляция", "Приточная вентиляция" ->
                                    Ventilation.SUPPLYANDEXHAUSTVENTILATION;
                            case "Вытяжная вентиляция" -> Ventilation.EXHAUSTVENTILATION;
                            case "Отсутствует" -> Ventilation.NONE;
                            default -> throw new IllegalArgumentException(value);
                        };
                        engineeringSystems.setVentilation(ventilation);
                        break;
                    case "Водоотведение":
                        WaterDisposal waterDisposal = switch (value) {
                            case "Центральное", "Централизованная канализация" -> WaterDisposal.CENTRAL;
                            case "Нет", "Отсутствует" -> WaterDisposal.NONE;
                            case "Выгребная яма" -> WaterDisposal.CESSPOOL;
                            case "Автономное" -> WaterDisposal.AUTONOMOUS;
                            case "Локальная канализация (септик)" -> WaterDisposal.LOCALSEWERAGESEPTIC;
                            default -> throw new IllegalArgumentException(value);
                        };
                        engineeringSystems.setWaterDisposal(waterDisposal);
                        break;
                    case "Система водостоков":
                        switch (value) {
                            case "Отсутствует":
                                engineeringSystems.setGuttersSystem(GuttersSystem.NONE);
                                break;
                            case "Внутренние водостоки":
                                engineeringSystems.setGuttersSystem(GuttersSystem.INNER);
                                break;
                            case "Наружные водостоки":
                                engineeringSystems.setGuttersSystem(GuttersSystem.EXTERNAL);
                                break;
                            default:
                                throw new IllegalArgumentException(value);
                        }
                        break;
                    case "Газоснабжение":
                        GasSupply gasSupply = switch (value) {
                            case "Центральное", "центральное" -> GasSupply.CENTRAL;
                            case "нет", "Отсутствует" -> GasSupply.NONE;
                            case "Газопровод низкого давления – подача природного газа в крышную котельную" ->
                                    GasSupply.LOWPRESSUREGASPIPELINEROOFBOILERROOM;
                            case "баллонный газ" -> GasSupply.BOTTLEDGAS;
                            case "Автономное" -> GasSupply.AUTONOMOUS;
                            default -> throw new IllegalArgumentException(value);
                        };
                        engineeringSystems.setGasSupply(gasSupply);
                        break;
                    case "Горячее водоснабжение":
                        String[] values = value.split(", ");
                        Set<HotWaterSystemType> hotWaterSystemTypes = new HashSet<>();
                        for (String string : values) {
                            switch (string) {
                                case "Нет":
                                case "Отсутствует":
                                    hotWaterSystemTypes.add(HotWaterSystemType.NONE);
                                    break;
                                case "Индивидуальный котел":
                                case "Квартирное (квартирный котел)":
                                    hotWaterSystemTypes.add(HotWaterSystemType.INDIVIDUALBOILER);
                                    break;
                                case "Кольцевая или с закольцованными вводами":
                                    hotWaterSystemTypes.add(HotWaterSystemType.RINGORWITHLOOPEDINPUTS);
                                    break;
                                case "Центральное":
                                case "Центральное (закрытая система)":
                                    hotWaterSystemTypes.add(HotWaterSystemType.CENTRAL);
                                    break;
                                case "Газовые колонки (ВДГО)":
                                    hotWaterSystemTypes.add(HotWaterSystemType.GASWATERHEATERS);
                                    break;
                                case "Закрытая с приготовлением горячей воды на ЦТП":
                                    hotWaterSystemTypes.add(HotWaterSystemType.CLOSEDWITHPREPARATIONATTHECENTRALHEATINGSTATION);
                                    break;
                                case "Тупиковая":
                                    hotWaterSystemTypes.add(HotWaterSystemType.DEADEND);
                                    break;
                                case "П-образная":
                                    hotWaterSystemTypes.add(HotWaterSystemType.USHAPED);
                                    break;
                                case "с нижней разводкой магистралей":
                                    hotWaterSystemTypes.add(HotWaterSystemType.LOWERROUTINGOFHIGHWAYS);
                                    break;
                                default:
                                    throw new IllegalArgumentException(value);
                            }
                        }
                        engineeringSystems.setHotWaterSystemTypes(hotWaterSystemTypes);
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
                        HeatSupply heatSupply = switch (value) {
                            case "Центральная", "Центральное" -> HeatSupply.CENTRAL;
                            case "Нет" -> HeatSupply.NONE;
                            case "Квартирное отопление (котел)", "Квартирное отопление (квартирный котел)" ->
                                    HeatSupply.BOILER;
                            case "Домовая котельная" -> HeatSupply.HOUSEBOILER;
                            case "Печная", "Печное" -> HeatSupply.STOVE;
                            case "Газовая колонка" -> HeatSupply.GEYSER;
                            default -> throw new IllegalArgumentException(value);
                        };
                        engineeringSystems.setHeatSupply(heatSupply);
                        break;
                    case "Холодное водоснабжение":
                        ColdWaterSupply coldWaterSupply = switch (value) {
                            case "Тупиковая" -> ColdWaterSupply.DEADEND;
                            case "Центральное", "Централизованная (от городской сети)" -> ColdWaterSupply.CENTRAL;
                            case "Нет", "Отсутствует" -> ColdWaterSupply.NONE;
                            case "Автономное" -> ColdWaterSupply.AUTONOMOUS;
                            default -> throw new IllegalArgumentException(value);
                        };
                        engineeringSystems.setColdWaterSupply(coldWaterSupply);
                        break;
                    case "Электроснабжение":
                        ElectricitySupply electricitySupply;
                        if (value.equals("Центральное")) {
                            electricitySupply = ElectricitySupply.CENTRAL;
                        } else {
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
                    case "Количество мусоропроводов, ед.":
                        constructionElements.setGarbageChuteNumber(Integer.valueOf(value));
                        break;
                    case "Несущие стены":
                        Set<LoadBearingWalls> loadBearingWalls = new HashSet<>();
                        String[] string = value.split(", ");
                        for (String str : string) {
                            switch (str) {
                                case "Стены кирпичные":
                                case "Кирпич":
                                    loadBearingWalls.add(LoadBearingWalls.BRICK);
                                    break;
                                case "Стены деревянные каркасные":
                                    loadBearingWalls.add(LoadBearingWalls.WOODENFRAME);
                                    break;
                                case "Стены рубленные из бревен и брусчатые":
                                    loadBearingWalls.add(LoadBearingWalls.LOGSTIMBER);
                                    break;
                                case "сборно-щитовые":
                                    loadBearingWalls.add(LoadBearingWalls.PREFABRICATEDPANELS);
                                    break;
                                case "Стены деревянные":
                                case "Деревянные":
                                    loadBearingWalls.add(LoadBearingWalls.WOODEN);
                                    break;
                                case "Стены железобетонные":
                                case "Железобетонные панели":
                                    loadBearingWalls.add(LoadBearingWalls.REINFORCEDCONCRETE);
                                    break;
                                case "Смешанные":
                                    loadBearingWalls.add(LoadBearingWalls.MIXED);
                                    break;
                                case "Стены панельные":
                                case "Панельные":
                                case "Стены из несущих панелей":
                                case "Крупнопанельные":
                                    loadBearingWalls.add(LoadBearingWalls.PANEL);
                                    break;
                                case "Стены из крупноразмерных блоков и однослойных несущих панелей":
                                    loadBearingWalls.add(LoadBearingWalls.LARGEBLOCKSANDSINGLELAYERLOADBEARINGPANELS);
                                    break;
                                case "Ж/б блоки":
                                    loadBearingWalls.add(LoadBearingWalls.REINFORCEDCONCRETEBLOCK);
                                    break;
                                case "Железобетонные плиты":
                                    loadBearingWalls.add(LoadBearingWalls.REINFORCEDCONCRETESLABS);
                                    break;
                                default:
                                    throw new IllegalArgumentException(str);
                            }
                        }
                        constructionElements.setLoadBearingWalls(loadBearingWalls);
                        break;
                    case "Площадь подвала, кв.м":
                        constructionElements.setBasementArea(Double.valueOf(value));
                        break;
                    case "Фундамент":
                        Foundation foundation = switch (value) {
                            case "Ленточный" -> Foundation.TAPE;
                            case "Нет" -> Foundation.NONE;
                            case "Ж/б крупноблочный", "Ж/б" -> Foundation.REINFORCEDCONCRETELARGEBLOCK;
                            case "Монолитный ленточный железобетонный и столбчатый ростверк по свайному основанию" ->
                                    Foundation.MONOLITHICSTRIPREINFORCEDCONCRETEANDCOLUMNARGRILLAGEONAPILE;
                            case "Столбчатый (столбовой)" -> Foundation.COLUMNARPILLAR;
                            case "Иной" -> Foundation.OTHER;
                            default -> throw new IllegalArgumentException(value);
                        };
                        constructionElements.setFoundation(foundation);
                        break;
                    case "Перекрытия":
                        FloorType floorType = switch (value) {
                            case "Перекрытия из железобетонных плит", "Перекрытия железобетонные", "Железобетонные", "Железобетон" ->
                                    FloorType.REINFORCEDCONCRETESLABS;
                            case "Перекрытия деревянные неоштукатуренные" -> FloorType.WOODENUNPLASTERED;
                            case "Перекрытия деревянные оштукатуренные" -> FloorType.WOODENPLASTERED;
                            case "Деревянные отепленные" -> FloorType.WOODENHEATED;
                            case "Деревянные" -> FloorType.WOODEN;
                            case "Иные" -> FloorType.OTHER;
                            case "Перекрытия из сборного железобетонного настила" -> FloorType.PRECASTCONCRETESLABS;
                            case "Перекрытия из сборных и монолитных сплошных плит" ->
                                    FloorType.PREFABRICATEDANDMONOLITHICSOLIDSLABS;
                            case "Плоские железобетонные плиты" -> FloorType.FLATREINFORCEDCONCRETE;
                            case "Нет" -> FloorType.NONE;
                            case "Смешанные" -> FloorType.MIXED;
                            default -> throw new IllegalArgumentException(value);
                        };
                        constructionElements.setFloorType(floorType);
                        break;
                    case "Вид услуги (работы)":
                    case "Подъезд":
                        //todo - https://dom.mingkh.ru/ivanovskaya-oblast/furmanov/92788, https://dom.mingkh.ru/ivanovskaya-oblast/furmanov/1148044
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
            if (!dt.isEmpty()) {
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
                } else if (tag.equals("Капитальный ремонт")) {
                    try {
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
                    } catch (ClassCastException e) {
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
                    }
                } else {
                    Node node = Optional.ofNullable(doc
                                    .select(".col-md-7")
                                    .get(1)
                                    .select("dl")
                                    .get(0)
                                    .children()
                                    .get(index))
                            .filter(v -> !v.childNodes().isEmpty())
                            .map(Element::childNodes)
                            .map(v -> v.get(0))
                            .orElse(null);

                    value = node == null ? null : ((TextNode) node)
                            .text()
                            .trim();
                }

                switch (tag) {
                    case "Адрес":
                        houseInfo.setAddress(value);
                        break;
                    case "Год постройки":
                    case "Год ввода в эксплуатацию":
                        assert value != null;
                        houseInfo.setYear(Integer.valueOf(value));
                        break;
                    case "Количество этажей":
                        assert value != null;
                        houseInfo.setFloor(Integer.valueOf(value));
                        break;
                    case "Тип дома":
                        assert value != null;
                        HouseType houseType = switch (value) {
                            case "Многоквартирный дом" -> HouseType.MANYAPPARTMENTS;
                            default -> throw new IllegalArgumentException(value);
                        };
                        houseInfo.setHouseType(houseType);
                        break;
                    case "Жилых помещений":
                        assert value != null;
                        houseInfo.setApartmentsCount(Integer.valueOf(value));
                        break;
                    case "Капитальный ремонт":
                        houseInfo.setMajorRenovation(value);
                        break;
                    case "Серия, тип постройки":
                        if (value == null) {
                            houseInfo.setMaterialTypes(null);
                            break;
                        }
                        Set<MaterialType> materialTypes = new HashSet<>();
                        for (String str : value.split(", ")) {
                            switch (str) {
                                case "Кирпичный":
                                case "кирпичный":
                                case "Жилой дом кирпичный":
                                    materialTypes.add(MaterialType.BRICK);
                                    break;
                                case "нет":
                                case "ytn":
                                    materialTypes.add(MaterialType.NONE);
                                    break;
                                case "дом":
                                case "жилой дом":
                                case "жилое":
                                case "Жилой дом":
                                    materialTypes.add(MaterialType.HOUSERESIDENTIAL);
                                    break;
                                case "информация отсутствует":
                                case "информация отсутвует":
                                case "byajhvfwbz jncencndetn":
                                case "информация отсутсвует":
                                case "не известен":
                                case "нет информации":
                                case "нет данных":
                                case "нет сведений":
                                    materialTypes.add(MaterialType.UNKNOWN);
                                    break;
                                case "Панельный":
                                case "панельный":
                                    materialTypes.add(MaterialType.PANEL);
                                    break;
                                case "проект на строительство 06/13-с-ПЗ":
                                    materialTypes.add(MaterialType.PROJECT0613);
                                    break;
                                case "Деревянные":
                                    materialTypes.add(MaterialType.WOODEN);
                                    break;
                                case "индивидуальный":
                                case "индивидуальное":
                                    materialTypes.add(MaterialType.INDIVIDUAL);
                                    break;
                                case "многоквартирный дом блокированной застройки":
                                case "дом блокированной застройки":
                                    materialTypes.add(MaterialType.BLOCKOFFLATS);
                                    break;
                                case "двухквартирный жилой дом блокированной застройки":
                                    materialTypes.add(MaterialType.TWOBLOCKOFFLATS);
                                    break;
                                default:
                                    throw new IllegalArgumentException(str);
                            }
                        }
                        houseInfo.setMaterialTypes(materialTypes);
                        break;
                    case "Тип перекрытий":
                        FloorType floorType;
                        switch (Objects.requireNonNull(value)) {
                            case "Перекрытия из железобетонных плит", "Перекрытия железобетонные", "Железобетонные", "Железобетон" ->
                                    floorType = FloorType.REINFORCEDCONCRETESLABS;
                            case "Перекрытия деревянные неоштукатуренные" -> floorType = FloorType.WOODENUNPLASTERED;
                            case "Перекрытия деревянные оштукатуренные" -> floorType = FloorType.WOODENPLASTERED;
                            case "Деревянные отепленные" -> floorType = FloorType.WOODENHEATED;
                            case "Деревянные" -> floorType = FloorType.WOODEN;
                            case "Иные" -> floorType = FloorType.OTHER;
                            case "Перекрытия из сборного железобетонного настила" ->
                                    floorType = FloorType.PRECASTCONCRETESLABS;
                            case "Перекрытия из сборных и монолитных сплошных плит" ->
                                    floorType = FloorType.PREFABRICATEDANDMONOLITHICSOLIDSLABS;
                            case "Плоские железобетонные плиты" -> floorType = FloorType.FLATREINFORCEDCONCRETE;
                            case "Нет" -> floorType = FloorType.NONE;
                            case "Смешанные" -> floorType = FloorType.MIXED;
                            default -> throw new IllegalArgumentException(value);
                        }
                        houseInfo.setFloorType(floorType);
                        break;
                    case "Материал несущих стен":
                        Set<WallMaterial> wallMaterials = new HashSet<>();
                        assert value != null;
                        String[] values = value.split(", ");
                        for (String str : values) {
                            switch (str) {
                                case "Стены кирпичные":
                                case "Кирпич":
                                    wallMaterials.add(WallMaterial.BRICK);
                                    break;
                                case "Стены деревянные каркасные":
                                    wallMaterials.add(WallMaterial.WOODENFRAME);
                                    break;
                                case "сборно-щитовые":
                                    wallMaterials.add(WallMaterial.PREFABRICATEDPANELS);
                                    break;
                                case "Стены рубленные из бревен и брусчатые":
                                    wallMaterials.add(WallMaterial.LOGSTIMBER);
                                    break;
                                case "Стены деревянные":
                                case "Деревянные":
                                    wallMaterials.add(WallMaterial.WOODEN);
                                    break;
                                case "Стены железобетонные":
                                case "Железобетонные панели":
                                    wallMaterials.add(WallMaterial.REINFORCEDCONCRETE);
                                    break;
                                case "Смешанные":
                                    wallMaterials.add(WallMaterial.MIXED);
                                    break;
                                case "Стены панельные":
                                case "Панельные":
                                case "Стены из несущих панелей":
                                case "Крупнопанельные":
                                    wallMaterials.add(WallMaterial.PANEL);
                                    break;
                                case "Стены из крупноразмерных блоков и однослойных несущих панелей":
                                    wallMaterials.add(WallMaterial.LARGEBLOCKSANDSINGLELAYERLOADBEARINGPANELS);
                                    break;
                                case "Ж/б блоки":
                                    wallMaterials.add(WallMaterial.REINFORCEDCONCRETEBLOCK);
                                    break;
                                case "Железобетонные плиты":
                                    wallMaterials.add(WallMaterial.LAMINATEDREINFORCEDCONCRETEPANELS);
                                    break;
                                default:
                                    throw new IllegalArgumentException(value);
                            }
                        }
                        houseInfo.setWallMaterials(wallMaterials);
                        break;
                    case "Тип мусоропровода":
                        switch (Objects.requireNonNull(value)) {
                            case "Отсутствует" -> houseInfo.setGarbageChute(false);
                            default -> throw new IllegalArgumentException(value);
                        }
                        break;
                    case "Дом признан аварийным":
                        switch (Objects.requireNonNull(value)) {
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
