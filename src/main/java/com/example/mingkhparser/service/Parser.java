package com.example.mingkhparser.service;

import com.example.mingkhparser.models.HouseInfo;
import com.example.mingkhparser.models.WallMaterial;
import com.example.mingkhparser.models.constructionelements.FloorType;
import com.example.mingkhparser.models.engineeringsystems.*;
import com.example.mingkhparser.models.generalinfo.*;
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
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

@Service
@Slf4j
public class Parser {
    private final String URL = "https://dom.mingkh.ru/ivanovskaya-oblast/furmanov/1150084";

    private HttpURLConnection createConnection() throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(URL).openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
        connection.connect();
        return connection;
    }

    public void process() throws IOException {
        HttpURLConnection connection = createConnection();
        Scanner scanner = new Scanner(new BufferedInputStream(connection.getInputStream()));
        Document doc = Jsoup.parseBodyFragment(scanner.nextLine());
        HouseInfo houseInfo = new HouseInfo();

        setInfo(houseInfo, doc);
        setGeneralInfo(houseInfo, doc);

        connection.disconnect();
        log.info("houseInfo: {}", houseInfo);
    }

    private static void setGeneralInfo(HouseInfo houseInfo, Document doc) {
        GeneralInfo generalInfo = new GeneralInfo();
        EngineeringSystems engineeringSystems = new EngineeringSystems();

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
                                waterDisposal = WaterDisposal.CENTRAL;
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
                                gasSupply = GasSupply.CENTRAL;
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


                    default:
                        throw new IllegalArgumentException(tag);
                }
            }
        }
        houseInfo.setGeneralInfo(generalInfo);
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
                            default:
                                throw new IllegalArgumentException(value);
                        }
                        break;
                    case "Выписка из ЕГРН":
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
