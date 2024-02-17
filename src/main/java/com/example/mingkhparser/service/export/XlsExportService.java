package com.example.mingkhparser.service.export;

import com.example.mingkhparser.models.CommonAreasFinishingCoatings;
import com.example.mingkhparser.models.HouseInfo;
import com.example.mingkhparser.models.InnerWalls;
import com.example.mingkhparser.models.coldwatersupplysystemrisers.ColdWaterSupplySystemRisers;
import com.example.mingkhparser.models.coldwatersystem.ColdWaterSystem;
import com.example.mingkhparser.models.constructionelements.ConstructionElements;
import com.example.mingkhparser.models.doors.Doors;
import com.example.mingkhparser.models.drainagesystem.DrainageSystem;
import com.example.mingkhparser.models.electricitysupplysystem.ElectricitySupplySystem;
import com.example.mingkhparser.models.engineeringsystems.EngineeringSystems;
import com.example.mingkhparser.models.facade.Facade;
import com.example.mingkhparser.models.floors.Floors;
import com.example.mingkhparser.models.foundation.Foundation;
import com.example.mingkhparser.models.gassupplysystem.GasSupplySystem;
import com.example.mingkhparser.models.generalinfo.GeneralInfo;
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
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import static com.example.mingkhparser.utils.NullCheckUtils.*;

@Service
@Slf4j
public class XlsExportService implements ExportService {
    @Value("${insertUrl:false}")
    private Boolean insertUrl;
    @Value("${exportUnused:true}")
    private Boolean exportUnused;

    @Override
    public void export(List<HouseInfo> source) {
        log.debug("Start export method");
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Houses");

            XSSFFont font = workbook.createFont();
            font.setFontName("Arial Narrow");
            font.setFontHeightInPoints((short) 10);
            font.setBold(true);

            CellStyle headerStyleGrey = workbook.createCellStyle();
            headerStyleGrey.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            headerStyleGrey.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyleGrey.setFont(font);

            CellStyle headerStyleGreen = workbook.createCellStyle();
            headerStyleGreen.setFillForegroundColor(IndexedColors.BRIGHT_GREEN.getIndex());
            headerStyleGreen.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyleGreen.setFont(font);

            int counter = 0;
            Row header0 = sheet.createRow(counter++);
            Row header1 = sheet.createRow(counter++);
            createHeader(header0, header1, headerStyleGrey, headerStyleGreen);

            for (HouseInfo houseInfo : source) {
                log.info("export: {}", houseInfo.getUrl());
                Row row = sheet.createRow(counter++);
                createInfoBlock(workbook, row, houseInfo);
            }
            setColumnsWidth(sheet);
            saveFile(workbook);
            log.debug("Finish export method");
        } catch (Exception exception) {
            log.error("{}", exception);
        }
    }

    private void setColumnsWidth(final Sheet sheet) {
        for (int i = 0; i < 120; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    private void createHeader(final Row header0, final Row header1,
                              final CellStyle headerStyleGrey, final CellStyle headerStyleGreen) {
        int headerRowIndex = 0;
        Cell headerCell0 = header0.createCell(headerRowIndex);
        Cell headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("№");
        headerCell0.setCellStyle(headerStyleGrey);
        headerCell.setCellStyle(headerStyleGrey);

        if (insertUrl) {
            headerCell0 = header0.createCell(headerRowIndex);
            headerCell = header1.createCell(headerRowIndex++);
            headerCell.setCellValue("URL");
            headerCell0.setCellStyle(headerStyleGrey);
            headerCell.setCellStyle(headerStyleGrey);
        }

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Адрес");
        headerCell0.setCellStyle(headerStyleGrey);
        headerCell.setCellStyle(headerStyleGrey);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Год постройки");
        headerCell0.setCellStyle(headerStyleGrey);
        headerCell.setCellStyle(headerStyleGrey);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Количество этажей");
        headerCell0.setCellStyle(headerStyleGrey);
        headerCell.setCellStyle(headerStyleGrey);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Тип дома");
        headerCell0.setCellStyle(headerStyleGrey);
        headerCell.setCellStyle(headerStyleGrey);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Жилых помещений");
        headerCell0.setCellStyle(headerStyleGrey);
        headerCell.setCellStyle(headerStyleGrey);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Капитальный ремонт");
        headerCell0.setCellStyle(headerStyleGrey);
        headerCell.setCellStyle(headerStyleGrey);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Серия, тип постройки");
        headerCell0.setCellStyle(headerStyleGrey);
        headerCell.setCellStyle(headerStyleGrey);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Тип перекрытий");
        headerCell0.setCellStyle(headerStyleGrey);
        headerCell.setCellStyle(headerStyleGrey);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Материал несущих стен");
        headerCell0.setCellStyle(headerStyleGrey);
        headerCell.setCellStyle(headerStyleGrey);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Тип мусоропровода");
        headerCell0.setCellStyle(headerStyleGrey);
        headerCell.setCellStyle(headerStyleGrey);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Дом признан аварийным");
        headerCell0.setCellStyle(headerStyleGrey);
        headerCell.setCellStyle(headerStyleGrey);

        if (exportUnused) {
            headerCell0 = header0.createCell(headerRowIndex);
            headerCell = header1.createCell(headerRowIndex++);
            headerCell.setCellValue("Выписка из ЕГРН");
            headerCell0.setCellStyle(headerStyleGrey);
            headerCell.setCellStyle(headerStyleGrey);
        }

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Кадастровый номер");
        headerCell0.setCellStyle(headerStyleGrey);
        headerCell.setCellStyle(headerStyleGrey);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Код ОКТМО");
        headerCell0.setCellStyle(headerStyleGrey);
        headerCell.setCellStyle(headerStyleGrey);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Управляющая компания");
        headerCell0.setCellStyle(headerStyleGrey);
        headerCell.setCellStyle(headerStyleGrey);

        //********************************Основные сведения**************************************
        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell0.setCellValue("Основные сведения");
        headerCell.setCellValue("Год ввода в эксплуатацию");
        headerCell0.setCellStyle(headerStyleGreen);
        headerCell.setCellStyle(headerStyleGreen);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Дом признан аварийным");
        headerCell0.setCellStyle(headerStyleGreen);
        headerCell.setCellStyle(headerStyleGreen);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Состояние дома");
        headerCell0.setCellStyle(headerStyleGreen);
        headerCell.setCellStyle(headerStyleGreen);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Количество квартир");
        headerCell0.setCellStyle(headerStyleGreen);
        headerCell.setCellStyle(headerStyleGreen);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Количество нежилых помещений");
        headerCell0.setCellStyle(headerStyleGreen);
        headerCell.setCellStyle(headerStyleGreen);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Количество лоджий");
        headerCell0.setCellStyle(headerStyleGreen);
        headerCell.setCellStyle(headerStyleGreen);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Количество балконов");
        headerCell0.setCellStyle(headerStyleGreen);
        headerCell.setCellStyle(headerStyleGreen);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Класс энергетической эффективности");
        headerCell0.setCellStyle(headerStyleGreen);
        headerCell.setCellStyle(headerStyleGreen);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Количество подъездов");
        headerCell0.setCellStyle(headerStyleGreen);
        headerCell.setCellStyle(headerStyleGreen);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Количество лифтов");
        headerCell0.setCellStyle(headerStyleGreen);
        headerCell.setCellStyle(headerStyleGreen);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Наибольшее количество этажей");
        headerCell0.setCellStyle(headerStyleGreen);
        headerCell.setCellStyle(headerStyleGreen);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Наименьшее количество этажей");
        headerCell0.setCellStyle(headerStyleGreen);
        headerCell.setCellStyle(headerStyleGreen);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Подземных этажей");
        headerCell0.setCellStyle(headerStyleGreen);
        headerCell.setCellStyle(headerStyleGreen);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Формирование фонда кап. ремонта");
        headerCell0.setCellStyle(headerStyleGreen);
        headerCell.setCellStyle(headerStyleGreen);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Площадь парковки м2");
        headerCell0.setCellStyle(headerStyleGreen);
        headerCell.setCellStyle(headerStyleGreen);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Наличие в подъездах приспособлений для нужд маломобильных групп населения");
        headerCell0.setCellStyle(headerStyleGreen);
        headerCell.setCellStyle(headerStyleGreen);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Тип дома");
        headerCell0.setCellStyle(headerStyleGreen);
        headerCell.setCellStyle(headerStyleGreen);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Дата документа о признании дома аварийным");
        headerCell0.setCellStyle(headerStyleGreen);
        headerCell.setCellStyle(headerStyleGreen);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Номер документа о признании дома аварийным");
        headerCell0.setCellStyle(headerStyleGreen);
        headerCell.setCellStyle(headerStyleGreen);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Износ здания, %");
        headerCell0.setCellStyle(headerStyleGreen);
        headerCell.setCellStyle(headerStyleGreen);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Дата, на которую установлен износ здания");
        headerCell0.setCellStyle(headerStyleGreen);
        headerCell.setCellStyle(headerStyleGreen);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Основание признания дома аварийным");
        headerCell0.setCellStyle(headerStyleGreen);
        headerCell.setCellStyle(headerStyleGreen);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Площадь многоквартирного дома, кв.м");
        headerCell0.setCellStyle(headerStyleGreen);
        headerCell.setCellStyle(headerStyleGreen);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Площадь жилых помещений м2");
        headerCell0.setCellStyle(headerStyleGreen);
        headerCell.setCellStyle(headerStyleGreen);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Площадь нежилых помещений м2");
        headerCell0.setCellStyle(headerStyleGreen);
        headerCell.setCellStyle(headerStyleGreen);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Площадь помещений общего имущества м2");
        headerCell0.setCellStyle(headerStyleGreen);
        headerCell.setCellStyle(headerStyleGreen);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Площадь зем. участка общего имущества м2");
        headerCell0.setCellStyle(headerStyleGreen);
        headerCell.setCellStyle(headerStyleGreen);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Серия, тип постройки здания");
        headerCell0.setCellStyle(headerStyleGreen);
        headerCell.setCellStyle(headerStyleGreen);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Статус объекта культурного наследия");
        headerCell0.setCellStyle(headerStyleGreen);
        headerCell.setCellStyle(headerStyleGreen);

        //**********************Инженерные системы**********************
        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell0.setCellValue("Инженерные системы");
        headerCell.setCellValue("Вентиляция");
        headerCell0.setCellStyle(headerStyleGrey);
        headerCell.setCellStyle(headerStyleGrey);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Водоотведение");
        headerCell0.setCellStyle(headerStyleGrey);
        headerCell.setCellStyle(headerStyleGrey);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Система водостоков");
        headerCell0.setCellStyle(headerStyleGrey);
        headerCell.setCellStyle(headerStyleGrey);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Газоснабжение");
        headerCell0.setCellStyle(headerStyleGrey);
        headerCell.setCellStyle(headerStyleGrey);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Горячее водоснабжение");
        headerCell0.setCellStyle(headerStyleGrey);
        headerCell.setCellStyle(headerStyleGrey);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Система пожаротушения");
        headerCell0.setCellStyle(headerStyleGrey);
        headerCell.setCellStyle(headerStyleGrey);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Теплоснабжение");
        headerCell0.setCellStyle(headerStyleGrey);
        headerCell.setCellStyle(headerStyleGrey);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Холодное водоснабжение");
        headerCell0.setCellStyle(headerStyleGrey);
        headerCell.setCellStyle(headerStyleGrey);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Электроснабжение");
        headerCell0.setCellStyle(headerStyleGrey);
        headerCell.setCellStyle(headerStyleGrey);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Количество вводов в дом, ед.");
        headerCell0.setCellStyle(headerStyleGrey);
        headerCell.setCellStyle(headerStyleGrey);

        //******************************Конструктивные элементы***********************
        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell0.setCellValue("Конструктивные элементы");
        headerCell.setCellValue("Мусоропровод");
        headerCell0.setCellStyle(headerStyleGreen);
        headerCell.setCellStyle(headerStyleGreen);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Количество мусоропроводов, ед.");
        headerCell0.setCellStyle(headerStyleGreen);
        headerCell.setCellStyle(headerStyleGreen);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Несущие стены");
        headerCell0.setCellStyle(headerStyleGreen);
        headerCell.setCellStyle(headerStyleGreen);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Площадь подвала, кв.м");
        headerCell0.setCellStyle(headerStyleGreen);
        headerCell.setCellStyle(headerStyleGreen);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Фундамент");
        headerCell0.setCellStyle(headerStyleGreen);
        headerCell.setCellStyle(headerStyleGreen);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Перекрытия");
        headerCell0.setCellStyle(headerStyleGreen);
        headerCell.setCellStyle(headerStyleGreen);

        if (exportUnused) {
            headerCell0 = header0.createCell(headerRowIndex);
            headerCell = header1.createCell(headerRowIndex++);
            headerCell.setCellValue("Вид услуги (работы)");
            headerCell0.setCellStyle(headerStyleGreen);
            headerCell.setCellStyle(headerStyleGreen);

            headerCell0 = header0.createCell(headerRowIndex);
            headerCell = header1.createCell(headerRowIndex++);
            headerCell.setCellValue("Подъезд");
            headerCell0.setCellStyle(headerStyleGreen);
            headerCell.setCellStyle(headerStyleGreen);
        }

        //******************************Cистема горячего водоснабжения******************************
        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell0.setCellValue("Cистема горячего водоснабжения");
        headerCell.setCellValue("Тип системы горячего водоснабжения");
        headerCell0.setCellStyle(headerStyleGrey);
        headerCell.setCellStyle(headerStyleGrey);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Физический износ");
        headerCell0.setCellStyle(headerStyleGrey);
        headerCell.setCellStyle(headerStyleGrey);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Материал сети");
        headerCell0.setCellStyle(headerStyleGrey);
        headerCell.setCellStyle(headerStyleGrey);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Материал теплоизоляции сети");
        headerCell0.setCellStyle(headerStyleGrey);
        headerCell.setCellStyle(headerStyleGrey);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Материал стояков");
        headerCell0.setCellStyle(headerStyleGrey);
        headerCell.setCellStyle(headerStyleGrey);

        //******************************Система водоотведения******************************
        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell0.setCellValue("Система водоотведения");
        headerCell.setCellValue("Физический износ");
        headerCell0.setCellStyle(headerStyleGreen);
        headerCell.setCellStyle(headerStyleGreen);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Год проведения последнего капитального ремонта");
        headerCell0.setCellStyle(headerStyleGreen);
        headerCell.setCellStyle(headerStyleGreen);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Тип системы водоотведения");
        headerCell0.setCellStyle(headerStyleGreen);
        headerCell.setCellStyle(headerStyleGreen);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Материал сети");
        headerCell0.setCellStyle(headerStyleGreen);
        headerCell.setCellStyle(headerStyleGreen);

        //******************************Система газоснабжения******************************
        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell0.setCellValue("Система газоснабжения");
        headerCell.setCellValue("Год проведения последнего капитального ремонта");
        headerCell0.setCellStyle(headerStyleGrey);
        headerCell.setCellStyle(headerStyleGrey);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Тип системы газоснабжения");
        headerCell0.setCellStyle(headerStyleGrey);
        headerCell.setCellStyle(headerStyleGrey);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Количество вводов системы газоснабжения");
        headerCell0.setCellStyle(headerStyleGrey);
        headerCell.setCellStyle(headerStyleGrey);

        //******************************Система электроснабжения******************************
        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell0.setCellValue("Система электроснабжения");
        headerCell.setCellValue("Физический износ");
        headerCell0.setCellStyle(headerStyleGreen);
        headerCell.setCellStyle(headerStyleGreen);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Год проведения последнего капитального ремонта");
        headerCell0.setCellStyle(headerStyleGreen);
        headerCell.setCellStyle(headerStyleGreen);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Количество вводов системы электроснабжения");
        headerCell0.setCellStyle(headerStyleGreen);
        headerCell.setCellStyle(headerStyleGreen);

        //******************************Фундамент******************************
        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell0.setCellValue("Фундамент");
        headerCell.setCellValue("Тип фундамента");
        headerCell0.setCellStyle(headerStyleGrey);
        headerCell.setCellStyle(headerStyleGrey);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Материал фундамента");
        headerCell0.setCellStyle(headerStyleGrey);
        headerCell.setCellStyle(headerStyleGrey);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Площадь отмостки");
        headerCell0.setCellStyle(headerStyleGrey);
        headerCell.setCellStyle(headerStyleGrey);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Физический износ");
        headerCell0.setCellStyle(headerStyleGrey);
        headerCell.setCellStyle(headerStyleGrey);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Год проведения последнего капитального ремонта");
        headerCell0.setCellStyle(headerStyleGrey);
        headerCell.setCellStyle(headerStyleGrey);

        //******************************Внутренние стены******************************
        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell0.setCellValue("Внутренние стены");
        headerCell.setCellValue("Тип внутренних стен");
        headerCell0.setCellStyle(headerStyleGreen);
        headerCell.setCellStyle(headerStyleGreen);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Физический износ");
        headerCell0.setCellStyle(headerStyleGreen);
        headerCell.setCellStyle(headerStyleGreen);

        //******************************Фасад******************************
        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell0.setCellValue("Фасад");
        headerCell.setCellValue("Тип наружных стен");
        headerCell0.setCellStyle(headerStyleGrey);
        headerCell.setCellStyle(headerStyleGrey);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Тип наружного утепления фасада");
        headerCell0.setCellStyle(headerStyleGrey);
        headerCell.setCellStyle(headerStyleGrey);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Материал отделки фасада");
        headerCell0.setCellStyle(headerStyleGrey);
        headerCell.setCellStyle(headerStyleGrey);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Физический износ");
        headerCell0.setCellStyle(headerStyleGrey);
        headerCell.setCellStyle(headerStyleGrey);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Год проведения последнего капитального ремонта");
        headerCell0.setCellStyle(headerStyleGrey);
        headerCell.setCellStyle(headerStyleGrey);

        //******************************Перекрытия******************************
        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell0.setCellValue("Перекрытия");
        headerCell.setCellValue("Тип перекрытия");
        headerCell0.setCellStyle(headerStyleGreen);
        headerCell.setCellStyle(headerStyleGreen);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Физический износ");
        headerCell0.setCellStyle(headerStyleGreen);
        headerCell.setCellStyle(headerStyleGreen);

        //******************************Крыша******************************
        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell0.setCellValue("Крыша");
        headerCell.setCellValue("Форма крыши");
        headerCell0.setCellStyle(headerStyleGrey);
        headerCell.setCellStyle(headerStyleGrey);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Утепляющие слои чердачных перекрытий");
        headerCell0.setCellStyle(headerStyleGrey);
        headerCell.setCellStyle(headerStyleGrey);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Вид несущей части");
        headerCell0.setCellStyle(headerStyleGrey);
        headerCell.setCellStyle(headerStyleGrey);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Тип кровли");
        headerCell0.setCellStyle(headerStyleGrey);
        headerCell.setCellStyle(headerStyleGrey);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Физический износ кровли");
        headerCell0.setCellStyle(headerStyleGrey);
        headerCell.setCellStyle(headerStyleGrey);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Год проведения последнего капитального ремонта кровли");
        headerCell0.setCellStyle(headerStyleGrey);
        headerCell.setCellStyle(headerStyleGrey);

        //******************************Окна******************************
        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell0.setCellValue("Окна");
        headerCell.setCellValue("Физический износ");
        headerCell0.setCellStyle(headerStyleGreen);
        headerCell.setCellStyle(headerStyleGreen);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Материал окон");
        headerCell0.setCellStyle(headerStyleGreen);
        headerCell.setCellStyle(headerStyleGreen);

        //******************************Двери******************************
        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell0.setCellValue("Двери");
        headerCell.setCellValue("Физический износ");
        headerCell0.setCellStyle(headerStyleGrey);
        headerCell.setCellStyle(headerStyleGrey);

        //******************************Отделочные покрытия помещений общего пользования******************************
        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell0.setCellValue("Отделочные покрытия помещений общего пользования");
        headerCell.setCellValue("Физический износ");
        headerCell0.setCellStyle(headerStyleGreen);
        headerCell.setCellStyle(headerStyleGreen);

        //******************************Система отопления******************************
        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell0.setCellValue("Система отопления");
        headerCell.setCellValue("Физический износ");
        headerCell0.setCellStyle(headerStyleGrey);
        headerCell.setCellStyle(headerStyleGrey);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Материал сети");
        headerCell0.setCellStyle(headerStyleGrey);
        headerCell.setCellStyle(headerStyleGrey);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Материал теплоизоляции сети");
        headerCell0.setCellStyle(headerStyleGrey);
        headerCell.setCellStyle(headerStyleGrey);

        //******************************Стояки системы отопления******************************
        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell0.setCellValue("Стояки системы отопления");
        headerCell.setCellValue("Физический износ");
        headerCell0.setCellStyle(headerStyleGreen);
        headerCell.setCellStyle(headerStyleGreen);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Тип поквартирной разводки внутридомовой системы отопления");
        headerCell0.setCellStyle(headerStyleGreen);
        headerCell.setCellStyle(headerStyleGreen);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Материал");
        headerCell0.setCellStyle(headerStyleGreen);
        headerCell.setCellStyle(headerStyleGreen);

        //******************************Запорная арматура системы отопления******************************
        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell0.setCellValue("Запорная арматура системы отопления");
        headerCell.setCellValue("Физический износ");
        headerCell0.setCellStyle(headerStyleGrey);
        headerCell.setCellStyle(headerStyleGrey);

        //******************************Отопительные приборы******************************
        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell0.setCellValue("Отопительные приборы");
        headerCell.setCellValue("Физический износ");
        headerCell0.setCellStyle(headerStyleGreen);
        headerCell.setCellStyle(headerStyleGreen);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Тип отопительных приборов");
        headerCell0.setCellStyle(headerStyleGreen);
        headerCell.setCellStyle(headerStyleGreen);

        //******************************Система холодного водоснабжения******************************
        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell0.setCellValue("Система холодного водоснабжения");
        headerCell.setCellValue("Физический износ");
        headerCell0.setCellStyle(headerStyleGrey);
        headerCell.setCellStyle(headerStyleGrey);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Материал сети");
        headerCell0.setCellStyle(headerStyleGrey);
        headerCell.setCellStyle(headerStyleGrey);

        //******************************Стояки системы холодного водоснабжения******************************
        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell0.setCellValue("Система холодного водоснабжения");
        headerCell.setCellValue("Стояки системы холодного водоснабжения");
        headerCell0.setCellStyle(headerStyleGreen);
        headerCell.setCellStyle(headerStyleGreen);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Материал сети");
        headerCell0.setCellStyle(headerStyleGreen);
        headerCell.setCellStyle(headerStyleGreen);

        //******************************Запорная арматура системы холодного водоснабжения******************************
        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell0.setCellValue("Стояки системы холодного водоснабжения");
        headerCell.setCellValue("Физический износ");
        headerCell0.setCellStyle(headerStyleGrey);
        headerCell.setCellStyle(headerStyleGrey);

        //******************************Стояки системы горячего водоснабжения******************************
        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell0.setCellValue("Стояки системы горячего водоснабжения");
        headerCell.setCellValue("Физический износ");
        headerCell0.setCellStyle(headerStyleGreen);
        headerCell.setCellStyle(headerStyleGreen);

        //******************************Запорная арматура системы горячего водоснабжения******************************
        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell0.setCellValue("Запорная арматура системы горячего водоснабжения");
        headerCell.setCellValue("Физический износ");
        headerCell0.setCellStyle(headerStyleGrey);
        headerCell.setCellStyle(headerStyleGrey);
    }

    private void createInfoBlock(XSSFWorkbook workbook, Row row, HouseInfo houseInfo) {
        CellStyle style = workbook.createCellStyle();

        XSSFFont font = workbook.createFont();
        font.setFontName("Arial Narrow");
        font.setFontHeightInPoints((short) 10);
        style.setFont(font);
        int cellRowIndex = 0;

        Cell cell = row.createCell(cellRowIndex++);
        cell.setCellValue(row.getRowNum() - 1);
        cell.setCellStyle(style);

        if (insertUrl) {
            cell = row.createCell(cellRowIndex++);
            cell.setCellValue(houseInfo.getUrl()); //URL
            cell.setCellStyle(style);
        }

        cell = row.createCell(cellRowIndex++);
        cell.setCellValue(houseInfo.getAddress()); //Адрес
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        setIntegerAndSet(houseInfo.getYear(), cell); //Год постройки
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        cell.setCellValue(houseInfo.getFloor()); //Количество этажей
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        cell.setCellValue(houseInfo.getHouseType().getName()); //Тип дома
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        setIntegerAndSet(houseInfo.getApartmentsCount(), cell); //Жилых помещений
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        cell.setCellValue(houseInfo.getMajorRenovation()); //Капитальный ремонт
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        setCheckAndJoin(houseInfo.getMaterialTypes(), cell); //Серия, тип постройки
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        enumCheckAndSet(houseInfo.getFloorType(), cell); //Тип перекрытий
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        setCheckAndJoin(houseInfo.getWallMaterials(), cell); //Материал несущих стен
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        setBooleanAndSet(houseInfo.getGarbageChute(), cell); //Тип мусоропровода
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        cell.setCellValue(houseInfo.getIsEmergency() ? "Да" : "Нет"); //Дом признан аварийным
        cell.setCellStyle(style);

        if (exportUnused) {
            cell = row.createCell(cellRowIndex++);
            cell.setCellValue(""); //Выписка из ЕГРН
            cell.setCellStyle(style);
        }

        cell = row.createCell(cellRowIndex++);
        cell.setCellValue(houseInfo.getCadastralNumber()); //Кадастровый номер
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        cell.setCellValue(houseInfo.getOktmoCode()); //Код ОКТМО
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        cell.setCellValue(houseInfo.getManagementCompany()); //Управляющая компания
        cell.setCellStyle(style);

//        ****************Основные сведения
        GeneralInfo generalInfo = houseInfo.getGeneralInfo();
        cell = row.createCell(cellRowIndex++);
        setIntegerAndSet(generalInfo.getYear(), cell); //Год ввода в эксплуатацию
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        cell.setCellValue(generalInfo.getIsEmergency() ? "Да" : "Нет"); //Дом признан аварийным
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        cell.setCellValue(generalInfo.getHouseCondition().getName()); //Состояние дома
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        setIntegerAndSet(generalInfo.getApartmentsCount(), cell); //Количество квартир
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        setIntegerAndSet(generalInfo.getNonResidentialPremises(), cell); //Количество нежилых помещений
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        setIntegerAndSet(generalInfo.getLoggiasNumber(), cell); //Количество лоджий
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        setIntegerAndSet(generalInfo.getBalconyNumber(), cell); //Количество балконов
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        enumCheckAndSet(generalInfo.getEnergyEfficiencyClass(), cell); //Класс энергетической эффективности
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        setIntegerAndSet(generalInfo.getNumberOfEntrances(), cell); //Количество подъездов
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        setIntegerAndSet(generalInfo.getElevatorsNumber(), cell); //Количество лифтов
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        setIntegerAndSet(generalInfo.getMaxFloor(), cell); //Наибольшее количество этажей
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        setIntegerAndSet(generalInfo.getMinFloor(), cell); //Наименьшее количество этажей
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        setIntegerAndSet(generalInfo.getUndergroundFloors(), cell); //Подземных этажей
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        enumCheckAndSet(generalInfo.getRepairFormation(), cell); //Формирование фонда кап. ремонта
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        setDoubleAndSet(generalInfo.getParkingArea(), cell); //Площадь парковки м2
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        setBooleanAndSet(generalInfo.getDisablePeopleDevices(), cell); //Наличие в подъездах приспособлений для нужд маломобильных групп населения
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        cell.setCellValue(generalInfo.getHouseType().getName()); //Тип дома
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        setLocalDateAndSet(generalInfo.getEmergencyDate(), cell); //Дата документа о признании дома аварийным
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        cell.setCellValue(generalInfo.getEmergencyDocumentNumber()); //Номер документа о признании дома аварийным"
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        setDoubleAndSet(generalInfo.getWearOfBuilding(), cell); //Износ здания, %
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        setLocalDateAndSet(generalInfo.getWearCalculationDate(), cell); //Дата, на которую установлен износ здания
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        cell.setCellValue(generalInfo.getUnsafeRecognizingReason()); //Основание признания дома аварийным
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        cell.setCellValue(generalInfo.getBuildingSquare()); //Площадь многоквартирного дома, кв.м
        style.setAlignment(HorizontalAlignment.LEFT);
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        setDoubleAndSet(generalInfo.getBuildingResidentialSquare(), cell); //Площадь жилых помещений м2
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        setDoubleAndSet(generalInfo.getBuildingNonResidentialSquare(), cell); //Площадь нежилых помещений м2
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        setDoubleAndSet(generalInfo.getBuildingCommonPropertySquare(), cell); //Площадь помещений общего имущества м2
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        setDoubleAndSet(generalInfo.getLandCommonPropertySquare(), cell); //Площадь зем. участка общего имущества м2
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        setCheckAndJoin(generalInfo.getMaterialTypes(), cell); //Серия, тип постройки здания
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        cell.setCellValue(generalInfo.getIsCulturalHeritage() ? "Да" : "Нет"); //Статус объекта культурного наследия
        cell.setCellStyle(style);

        //Инженерные системы
        EngineeringSystems engineeringSystems = houseInfo.getEngineeringSystems();
        cell = row.createCell(cellRowIndex++);
        enumCheckAndSet(engineeringSystems.getVentilation(), cell); //Вентиляция
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        enumCheckAndSet(engineeringSystems.getWaterDisposal(), cell); //Водоотведение
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        enumCheckAndSet(engineeringSystems.getGuttersSystem(), cell); //Система водостоков
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        enumCheckAndSet(engineeringSystems.getGasSupply(), cell); //Газоснабжение
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        setCheckAndJoin(engineeringSystems.getHotWaterSystemTypes(), cell); //Горячее водоснабжение
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        setBooleanAndSet(engineeringSystems.getFireExtinguishingSystem(), cell); //Система пожаротушения
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        enumCheckAndSet(engineeringSystems.getHeatSupply(), cell); //Теплоснабжение
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        enumCheckAndSet(engineeringSystems.getColdWaterSupply(), cell); //Холодное водоснабжение
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        enumCheckAndSet(engineeringSystems.getElectricitySupply(), cell); //Электроснабжение
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        setIntegerAndSet(engineeringSystems.getNumberOfEntriesIntoHouse(), cell); //Количество вводов в дом, ед.
        cell.setCellStyle(style);

        //********************************Конструктивные элементы
        ConstructionElements constructionElements = houseInfo.getConstructionElements();
        cell = row.createCell(cellRowIndex++);
        setBooleanAndSet(constructionElements.getGarbageChute(), cell); //Мусоропровод
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        setIntegerAndSet(constructionElements.getGarbageChuteNumber(), cell); //Количество мусоропроводов, ед.
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        setCheckAndJoin(constructionElements.getLoadBearingWalls(), cell); //Несущие стены
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        setDoubleAndSet(constructionElements.getBasementArea(), cell); //Площадь подвала, кв.м
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        enumCheckAndSet(constructionElements.getFoundation(), cell); //Фундамент
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        enumCheckAndSet(constructionElements.getFloorType(), cell); //Перекрытия
        cell.setCellStyle(style);

        if (exportUnused) {
            cell = row.createCell(cellRowIndex++);
            cell.setCellValue(""); //Вид услуги (работы)
            cell.setCellStyle(style);

            cell = row.createCell(cellRowIndex++);
            cell.setCellValue(""); //Подъезд
            cell.setCellStyle(style);
        }

        //******************************Cистема горячего водоснабжения******************************
        HotWaterSupplySystem hotWaterSupplySystem = houseInfo.getHotWaterSupplySystem();
        cell = row.createCell(cellRowIndex++);
        setCheckAndJoin(hotWaterSupplySystem.getHotWaterSystemTypes(), cell); //Тип системы горячего водоснабжения
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        setDoubleAndSet(hotWaterSupplySystem.getPhysicalDeterioration(), cell); //Физический износ
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        setCheckAndJoin(hotWaterSupplySystem.getNetworkMaterials(), cell); //Материал сети
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        enumCheckAndSet(hotWaterSupplySystem.getNetworkThermalInsulationMaterial(), cell); //Материал теплоизоляции сети
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        setCheckAndJoin(hotWaterSupplySystem.getRisersMaterials(), cell); //Материал стояков
        cell.setCellStyle(style);

        //******************************Система водоотведения******************************
        DrainageSystem drainageSystem = houseInfo.getDrainageSystem();
        cell = row.createCell(cellRowIndex++);
        setIntegerAndSet(drainageSystem.getPhysicalDeterioration(), cell); //Физический износ
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        setIntegerAndSet(drainageSystem.getLastOverhaulYear(), cell); //Год проведения последнего капитального ремонта
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        enumCheckAndSet(drainageSystem.getDrainageSystemType(), cell); //Тип системы водоотведения
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        setCheckAndJoin(drainageSystem.getNetworkMaterials(), cell); //Материал сети
        cell.setCellStyle(style);

        //******************************Система газоснабжения******************************
        GasSupplySystem gasSupplySystem = houseInfo.getGasSupplySystem();
        cell = row.createCell(cellRowIndex++);
        setIntegerAndSet(gasSupplySystem.getLastOverhaulYear(), cell); //Год проведения последнего капитального ремонта
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        enumCheckAndSet(gasSupplySystem.getGasSupplySystemType(), cell); //Тип системы газоснабжения
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        setIntegerAndSet(gasSupplySystem.getGasSupplySystemInletsNumber(), cell); //Количество вводов системы газоснабжения
        cell.setCellStyle(style);

        //******************************Система электроснабжения******************************
        ElectricitySupplySystem electricitySupplySystem = houseInfo.getElectricitySupplySystem();
        cell = row.createCell(cellRowIndex++);
        setIntegerAndSet(electricitySupplySystem.getPhysicalDeterioration(), cell); //Физический износ
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        setIntegerAndSet(electricitySupplySystem.getLastOverhaulYear(), cell); //Год проведения последнего капитального ремонта
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        setIntegerAndSet(electricitySupplySystem.getPowerSupplyInputNumbers(), cell); //Количество вводов системы электроснабжения
        cell.setCellStyle(style);

        //******************************Фундамент******************************
        Foundation foundation = houseInfo.getFoundation();
        cell = row.createCell(cellRowIndex++);
        enumCheckAndSet(foundation.getFoundationType(), cell); //Тип фундамента
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        enumCheckAndSet(foundation.getFoundationMaterial(), cell); //Материал фундамента
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        setDoubleAndSet(foundation.getBlindArea(), cell); //Площадь отмостки
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        setDoubleAndSet(foundation.getPhysicalDeterioration(), cell); //Физический износ
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        setIntegerAndSet(foundation.getLastOverhaulYear(), cell); //Год проведения последнего капитального ремонта
        cell.setCellStyle(style);

        //******************************Внутренние стены******************************
        InnerWalls innerWalls = houseInfo.getInnerWalls();
        cell = row.createCell(cellRowIndex++);
        setCheckAndJoin(innerWalls.getWallMaterials(), cell); //Тип внутренних стен
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        setDoubleAndSet(innerWalls.getPhysicalDeterioration(), cell); //Физический износ
        cell.setCellStyle(style);

        //******************************Фасад******************************
        Facade facade = houseInfo.getFacade();
        cell = row.createCell(cellRowIndex++);
        setCheckAndJoin(facade.getOuterWallsMaterials(), cell); //Тип наружных стен
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        enumCheckAndSet(facade.getExternalInsulationType(), cell); //Тип наружного утепления фасада
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        setCheckAndJoin(facade.getFacadeFinishingMaterials(), cell); //Материал отделки фасада
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        setDoubleAndSet(facade.getPhysicalDeterioration(), cell); //Физический износ
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        setIntegerAndSet(facade.getLastOverhaulYear(), cell); //Год проведения последнего капитального ремонта
        cell.setCellStyle(style);

        //******************************Перекрытия******************************
        Floors floors = houseInfo.getFloors();
        cell = row.createCell(cellRowIndex++);
        enumCheckAndSet(floors.getFloorType(), cell); //Тип перекрытия
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        setDoubleAndSet(floors.getPhysicalDeterioration(), cell); //Физический износ
        cell.setCellStyle(style);

        //******************************Крыша******************************
        Roof roof = houseInfo.getRoof();
        cell = row.createCell(cellRowIndex++);
        enumCheckAndSet(roof.getRoofShape(), cell); //Форма крыши
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        setCheckAndJoin(roof.getInsulatingLayers(), cell); //Утепляющие слои чердачных перекрытий
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        setCheckAndJoin(roof.getBearingTypes(), cell); //Вид несущей части
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        setCheckAndJoin(roof.getRoofTypes(), cell); //Тип кровли
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        setIntegerAndSet(roof.getPhysicalDeterioration(), cell); //Физический износ кровли
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        setIntegerAndSet(roof.getLastOverhaulYear(), cell); //Год проведения последнего капитального ремонта кровли
        cell.setCellStyle(style);

        //******************************Окна******************************
        Windows windows = houseInfo.getWindows();
        cell = row.createCell(cellRowIndex++);
        setDoubleAndSet(windows.getPhysicalDeterioration(), cell); //Физический износ
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        setCheckAndJoin(windows.getWindowsTypes(), cell); //Материал окон
        cell.setCellStyle(style);

        //******************************Двери******************************
        Doors doors = houseInfo.getDoors();
        cell = row.createCell(cellRowIndex++);
        setDoubleAndSet(doors.getPhysicalDeterioration(), cell); //Физический износ
        cell.setCellStyle(style);

        //******************************Отделочные покрытия помещений общего пользования******************************
        CommonAreasFinishingCoatings commonAreasFinishingCoatings = houseInfo.getCommonAreasFinishingCoatings();
        cell = row.createCell(cellRowIndex++);
        setDoubleAndSet(commonAreasFinishingCoatings.getPhysicalDeterioration(), cell); //Физический износ
        cell.setCellStyle(style);

        //******************************Система отопления******************************
        HeatingSystem heatingSystem = houseInfo.getHeatingSystem();
        cell = row.createCell(cellRowIndex++);
        setDoubleAndSet(heatingSystem.getPhysicalDeterioration(), cell); //Физический износ
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        setCheckAndJoin(heatingSystem.getNetworkMaterials(), cell); //Материал сети
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        setCheckAndJoin(heatingSystem.getThermalInsulationMaterials(), cell); //Материал теплоизоляции сети
        cell.setCellStyle(style);

        //******************************Стояки системы отопления******************************
        HeatingSystemRisers heatingSystemRisers = houseInfo.getHeatingSystemRisers();
        cell = row.createCell(cellRowIndex++);
        setIntegerAndSet(heatingSystemRisers.getPhysicalDeterioration(), cell); //Физический износ
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        enumCheckAndSet(heatingSystemRisers.getApartmentWiringType(), cell); //Тип поквартирной разводки внутридомовой системы отопления
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        setCheckAndJoin(heatingSystemRisers.getMaterialTypes(), cell); //Материал
        cell.setCellStyle(style);

        //******************************Запорная арматура системы отопления******************************
        ShutoffValvesHeatingSystem shutoffValvesHeatingSystem = houseInfo.getShutoffValvesHeatingSystem();
        cell = row.createCell(cellRowIndex++);
        setIntegerAndSet(shutoffValvesHeatingSystem.getPhysicalDeterioration(), cell); //Физический износ
        cell.setCellStyle(style);

        //******************************Отопительные приборы******************************
        HeatingDevices heatingDevices = houseInfo.getHeatingDevices();
        cell = row.createCell(cellRowIndex++);
        setIntegerAndSet(heatingDevices.getPhysicalDeterioration(), cell); //Физический износ
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        setCheckAndJoin(heatingDevices.getHeatingDevicesTypes(), cell); //Тип отопительных приборов
        cell.setCellStyle(style);

        //******************************Система холодного водоснабжения******************************
        ColdWaterSystem coldWaterSystem = houseInfo.getColdWaterSystem();
        cell = row.createCell(cellRowIndex++);
        setDoubleAndSet(coldWaterSystem.getPhysicalDeterioration(), cell); //Физический износ
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        setCheckAndJoin(coldWaterSystem.getNetworkMaterials(), cell); //Материал сети
        cell.setCellStyle(style);

        //******************************Стояки системы холодного водоснабжения******************************
        ColdWaterSupplySystemRisers coldWaterSupplySystemRisers = houseInfo.getColdWaterSupplySystemRisers();
        cell = row.createCell(cellRowIndex++);
        setIntegerAndSet(coldWaterSupplySystemRisers.getPhysicalDeterioration(), cell); //Физический износ
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        setCheckAndJoin(coldWaterSupplySystemRisers.getNetworkMaterials(), cell); //Материал сети
        cell.setCellStyle(style);

        //******************************Запорная арматура системы холодного водоснабжения******************************
        ShutoffValvesColdWaterSupplySystem shutoffValvesColdWaterSupplySystem = houseInfo.getShutoffValvesColdWaterSupplySystem();
        cell = row.createCell(cellRowIndex++);
        setIntegerAndSet(shutoffValvesColdWaterSupplySystem.getPhysicalDeterioration(), cell); //Физический износ
        cell.setCellStyle(style);

        //******************************Стояки системы горячего водоснабжения******************************
        HotWaterSupplySystemRisers hotWaterSupplySystemRisers = houseInfo.getHotWaterSupplySystemRisers();
        cell = row.createCell(cellRowIndex++);
        setIntegerAndSet(hotWaterSupplySystemRisers.getPhysicalDeterioration(), cell); //Физический износ
        cell.setCellStyle(style);

        //******************************Запорная арматура системы горячего водоснабжения******************************
        ShutoffValvesHotWaterSupplySystem shutoffValvesHotWaterSupplySystem = houseInfo.getShutoffValvesHotWaterSupplySystem();
        cell = row.createCell(cellRowIndex++);
        setIntegerAndSet(shutoffValvesHotWaterSupplySystem.getPhysicalDeterioration(), cell); //Физический износ
        cell.setCellStyle(style);
    }

    private void saveFile(XSSFWorkbook workbook) throws IOException {
        File currDir = new File(".");
        String path = currDir.getAbsolutePath();
        String fileLocation = path.substring(0, path.length() - 1) + "houses.xlsx";

        FileOutputStream outputStream = new FileOutputStream(fileLocation);
        workbook.write(outputStream);
    }
}
