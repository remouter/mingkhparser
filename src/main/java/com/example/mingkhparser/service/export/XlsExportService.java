package com.example.mingkhparser.service.export;

import com.example.mingkhparser.models.*;
import com.example.mingkhparser.models.coldwatersupplysystemrisers.ColdWaterSupplySystemRisers;
import com.example.mingkhparser.models.coldwatersystem.ColdWaterSystem;
import com.example.mingkhparser.models.constructionelements.ConstructionElements;
import com.example.mingkhparser.models.doors.Doors;
import com.example.mingkhparser.models.drainagesystem.DrainageSystem;
import com.example.mingkhparser.models.electricitysupplysystem.ElectricitySupplySystem;
import com.example.mingkhparser.models.engineeringsystems.ElectricitySupply;
import com.example.mingkhparser.models.engineeringsystems.EngineeringSystems;
import com.example.mingkhparser.models.engineeringsystems.GuttersSystem;
import com.example.mingkhparser.models.engineeringsystems.Ventilation;
import com.example.mingkhparser.models.facade.Facade;
import com.example.mingkhparser.models.facade.FacadeFinishingMaterial;
import com.example.mingkhparser.models.floors.Floors;
import com.example.mingkhparser.models.foundation.Foundation;
import com.example.mingkhparser.models.gassupplysystem.GasSupplySystem;
import com.example.mingkhparser.models.generalinfo.EnergyEfficiencyClass;
import com.example.mingkhparser.models.generalinfo.GeneralInfo;
import com.example.mingkhparser.models.generalinfo.MaterialType;
import com.example.mingkhparser.models.generalinfo.RepairFormation;
import com.example.mingkhparser.models.heatingdevices.HeatingDevices;
import com.example.mingkhparser.models.heatingdevices.HeatingDevicesType;
import com.example.mingkhparser.models.heatingsystem.HeatingSystem;
import com.example.mingkhparser.models.heatingsystem.ThermalInsulationMaterial;
import com.example.mingkhparser.models.heatingsystemrisers.HeatingSystemRisers;
import com.example.mingkhparser.models.hotwatersupplysystem.HotWaterSupplySystem;
import com.example.mingkhparser.models.hotwatersupplysystem.HotWaterSystemType;
import com.example.mingkhparser.models.hotwatersupplysystem.NetworkMaterial;
import com.example.mingkhparser.models.hotwatersupplysystem.RisersMaterial;
import com.example.mingkhparser.models.hotwatersupplysystemrisers.HotWaterSupplySystemRisers;
import com.example.mingkhparser.models.roof.BearingType;
import com.example.mingkhparser.models.roof.InsulatingLayers;
import com.example.mingkhparser.models.roof.Roof;
import com.example.mingkhparser.models.roof.RoofType;
import com.example.mingkhparser.models.shutoffvalves.coldwater.ShutoffValvesColdWaterSupplySystem;
import com.example.mingkhparser.models.shutoffvalves.heating.ShutoffValvesHeatingSystem;
import com.example.mingkhparser.models.shutoffvalves.hotwater.ShutoffValvesHotWaterSupplySystem;
import com.example.mingkhparser.models.windows.Windows;
import com.example.mingkhparser.models.windows.WindowsType;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class XlsExportService implements ExportService {

    @Override
    public void export(List<HouseInfo> source) {
        log.info("Start export method");
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Persons");
            sheet.setColumnWidth(0, 6000);
            sheet.setColumnWidth(1, 4000);

            Row header = sheet.createRow(0);

            CellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            XSSFFont font = workbook.createFont();
            font.setFontName("Arial");
            font.setFontHeightInPoints((short) 16);
            font.setBold(true);
            headerStyle.setFont(font);

            Cell headerCell = header.createCell(0);
            headerCell.setCellValue("Name");
            headerCell.setCellStyle(headerStyle);

            headerCell = header.createCell(1);
            headerCell.setCellValue("Age");
            headerCell.setCellStyle(headerStyle);

            CellStyle style = workbook.createCellStyle();
            style.setWrapText(true);

            Row row = sheet.createRow(2);
            Cell cell = row.createCell(0);
            cell.setCellValue("John Smith");
            cell.setCellStyle(style);

            cell = row.createCell(1);
            cell.setCellValue(20);
            cell.setCellStyle(style);

            File currDir = new File(".");
            String path = currDir.getAbsolutePath();
            String fileLocation = path.substring(0, path.length() - 1) + "temp.xlsx";

            FileOutputStream outputStream = new FileOutputStream(fileLocation);
            workbook.write(outputStream);
            log.info("Finish export method");
        } catch (Exception ignored) {
        }
    }

    public void test(List<HouseInfo> source) {
        HouseInfo houseInfo = source.get(0);

        log.info("Start export method");
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Houses");
            setColumnsWidth(sheet);

            Row header0 = sheet.createRow(0);
            Row header1 = sheet.createRow(1);
            createHeader(workbook, header0, header1);

            Row row = sheet.createRow(2); //todo counter
            createInfoBlock(workbook, row, houseInfo);

            saveFile(workbook);
            log.info("Finish export method");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void setColumnsWidth(final Sheet sheet) {
        int columnWidthCount = 0;
        sheet.setColumnWidth(columnWidthCount++, 4000);
        sheet.setColumnWidth(columnWidthCount++, 4000);
        sheet.setColumnWidth(columnWidthCount++, 4000);
        sheet.setColumnWidth(columnWidthCount++, 4000);
        sheet.setColumnWidth(columnWidthCount++, 4000);
        sheet.setColumnWidth(columnWidthCount++, 4000);
        sheet.setColumnWidth(columnWidthCount++, 4000);
        sheet.setColumnWidth(columnWidthCount++, 4000);
        sheet.setColumnWidth(columnWidthCount++, 4000);
        sheet.setColumnWidth(columnWidthCount++, 4000);
        sheet.setColumnWidth(columnWidthCount++, 4000);
        sheet.setColumnWidth(columnWidthCount++, 4000);
        sheet.setColumnWidth(columnWidthCount++, 4000);
        sheet.setColumnWidth(columnWidthCount++, 4000);
        sheet.setColumnWidth(columnWidthCount++, 4000);
        sheet.setColumnWidth(columnWidthCount++, 4000);
    }

    private void createHeader(final XSSFWorkbook workbook, final Row header0, final Row header1) {
        XSSFFont font = workbook.createFont();
        font.setFontName("Arial Narrow");
        font.setFontHeightInPoints((short) 16);
        font.setBold(true);

        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setFont(font);

        int headerRowIndex = 0;
        Cell headerCell0 = header0.createCell(headerRowIndex);
        Cell headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("№");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Адрес");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Год постройки");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Количество этажей");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Тип дома");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Жилых помещений");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Капитальный ремонт");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Серия, тип постройки");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Тип перекрытий");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Материал несущих стен");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Тип мусоропровода");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Дом признан аварийным");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Выписка из ЕГРН");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Кадастровый номер");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Код ОКТМО");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Управляющая компания");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        //********************************Основные сведения**************************************
        headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.BRIGHT_GREEN.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setFont(font);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell0.setCellValue("Основные сведения");
        headerCell.setCellValue("Год ввода в эксплуатацию");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Дом признан аварийным");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Состояние дома");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Количество квартир");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Количество нежилых помещений");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Количество лоджий");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Количество балконов");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Класс энергетической эффективности");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Количество подъездов");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Количество лифтов");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Наибольшее количество этажей");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Наименьшее количество этажей");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Подземных этажей");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Формирование фонда кап. ремонта");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Площадь парковки м2");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Наличие в подъездах приспособлений для нужд маломобильных групп населения");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Тип дома");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Дата документа о признании дома аварийным");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Номер документа о признании дома аварийным");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Износ здания, %");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Дата, на которую установлен износ здания");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Основание признания дома аварийным");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Площадь многоквартирного дома, кв.м");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Площадь жилых помещений м2");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Площадь нежилых помещений м2");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Площадь помещений общего имущества м2");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Площадь зем. участка общего имущества м2");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Серия, тип постройки здания");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Статус объекта культурного наследия");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        //**********************Инженерные системы**********************
        headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setFont(font);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell0.setCellValue("Инженерные системы");
        headerCell.setCellValue("Вентиляция");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Водоотведение");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Система водостоков");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Газоснабжение");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Горячее водоснабжение");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Система пожаротушения");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Теплоснабжение");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Холодное водоснабжение");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Электроснабжение");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Количество вводов в дом, ед.");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        //******************************Конструктивные элементы***********************
        headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.BRIGHT_GREEN.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setFont(font);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell0.setCellValue("Конструктивные элементы");
        headerCell.setCellValue("Мусоропровод");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Количество мусоропроводов, ед.");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Несущие стены");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Площадь подвала, кв.м");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Фундамент");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Перекрытия");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Вид услуги (работы)");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Подъезд");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        //******************************Cистема горячего водоснабжения******************************
        headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setFont(font);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell0.setCellValue("Cистема горячего водоснабжения");
        headerCell.setCellValue("Тип системы горячего водоснабжения");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Физический износ");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Материал сети");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Материал теплоизоляции сети");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Материал стояков");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        //******************************Система водоотведения******************************
        headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.BRIGHT_GREEN.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setFont(font);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell0.setCellValue("Система водоотведения");
        headerCell.setCellValue("Физический износ");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Год проведения последнего капитального ремонта");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Тип системы водоотведения");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Материал сети");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        //******************************Система газоснабжения******************************
        headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setFont(font);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell0.setCellValue("Система газоснабжения");
        headerCell.setCellValue("Год проведения последнего капитального ремонта");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Тип системы газоснабжения");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Количество вводов системы газоснабжения");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        //******************************Система электроснабжения******************************
        headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.BRIGHT_GREEN.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setFont(font);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell0.setCellValue("Система электроснабжения");
        headerCell.setCellValue("Физический износ");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Год проведения последнего капитального ремонта");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Количество вводов системы электроснабжения");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        //******************************Фундамент******************************
        headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setFont(font);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell0.setCellValue("Фундамент");
        headerCell.setCellValue("Тип фундамента");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Материал фундамента");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Площадь отмостки");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Физический износ");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Год проведения последнего капитального ремонта");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        //******************************Внутренние стены******************************
        headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.BRIGHT_GREEN.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setFont(font);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell0.setCellValue("Внутренние стены");
        headerCell.setCellValue("Тип внутренних стен");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Физический износ");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        //******************************Фасад******************************
        headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setFont(font);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell0.setCellValue("Фасад");
        headerCell.setCellValue("Тип наружных стен");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Тип наружного утепления фасада");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Материал отделки фасада");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Физический износ");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Год проведения последнего капитального ремонта");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        //******************************Перекрытия******************************
        headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.BRIGHT_GREEN.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setFont(font);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell0.setCellValue("Перекрытия");
        headerCell.setCellValue("Тип перекрытия");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Физический износ");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        //******************************Крыша******************************
        headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setFont(font);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell0.setCellValue("Крыша");
        headerCell.setCellValue("Форма крыши");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Утепляющие слои чердачных перекрытий");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Вид несущей части");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Тип кровли");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Физический износ кровли");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Год проведения последнего капитального ремонта кровли");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        //******************************Окна******************************
        headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.BRIGHT_GREEN.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setFont(font);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell0.setCellValue("Окна");
        headerCell.setCellValue("Физический износ");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Материал окон");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        //******************************Двери******************************
        headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setFont(font);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell0.setCellValue("Двери");
        headerCell.setCellValue("Физический износ");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        //******************************Отделочные покрытия помещений общего пользования******************************
        headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.BRIGHT_GREEN.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setFont(font);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell0.setCellValue("Отделочные покрытия помещений общего пользования");
        headerCell.setCellValue("Физический износ");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        //******************************Система отопления******************************
        headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setFont(font);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell0.setCellValue("Система отопления");
        headerCell.setCellValue("Физический износ");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Материал сети");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Материал теплоизоляции сети");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        //******************************Стояки системы отопления******************************
        headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.BRIGHT_GREEN.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setFont(font);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell0.setCellValue("Стояки системы отопления");
        headerCell.setCellValue("Физический износ");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Тип поквартирной разводки внутридомовой системы отопления");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Материал");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        //******************************Запорная арматура системы отопления******************************
        headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setFont(font);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell0.setCellValue("Запорная арматура системы отопления");
        headerCell.setCellValue("Физический износ");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        //******************************Отопительные приборы******************************
        headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.BRIGHT_GREEN.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setFont(font);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell0.setCellValue("Отопительные приборы");
        headerCell.setCellValue("Физический износ");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Тип отопительных приборов");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        //******************************Система холодного водоснабжения******************************
        headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setFont(font);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell0.setCellValue("Система холодного водоснабжения");
        headerCell.setCellValue("Физический износ");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Материал сети");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        //******************************Стояки системы холодного водоснабжения******************************
        headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.BRIGHT_GREEN.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setFont(font);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell0.setCellValue("Система холодного водоснабжения");
        headerCell.setCellValue("Стояки системы холодного водоснабжения");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell.setCellValue("Материал сети");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        //******************************Запорная арматура системы холодного водоснабжения******************************
        headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setFont(font);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell0.setCellValue("Стояки системы холодного водоснабжения");
        headerCell.setCellValue("Физический износ");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        //******************************Стояки системы горячего водоснабжения******************************
        headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.BRIGHT_GREEN.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setFont(font);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell0.setCellValue("Стояки системы горячего водоснабжения");
        headerCell.setCellValue("Физический износ");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);

        //******************************Запорная арматура системы горячего водоснабжения******************************
        headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setFont(font);

        headerCell0 = header0.createCell(headerRowIndex);
        headerCell = header1.createCell(headerRowIndex++);
        headerCell0.setCellValue("Запорная арматура системы горячего водоснабжения");
        headerCell.setCellValue("Физический износ");
        headerCell0.setCellStyle(headerStyle);
        headerCell.setCellStyle(headerStyle);
    }

    private void createInfoBlock(XSSFWorkbook workbook, Row row, HouseInfo houseInfo) {
        CellStyle style = workbook.createCellStyle();
        int cellRowIndex = 0;

        Cell cell = row.createCell(cellRowIndex++);
        cell.setCellValue(1); //todo counter
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        cell.setCellValue(houseInfo.getAddress()); //Адрес
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        Integer year = houseInfo.getYear();
        if (year != null) {
            cell.setCellValue(year); //Год постройки
        }
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        cell.setCellValue(houseInfo.getFloor()); //Количество этажей
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        cell.setCellValue(houseInfo.getHouseType().getName()); //Тип дома
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        cell.setCellValue(houseInfo.getApartmentsCount()); //Жилых помещений
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        cell.setCellValue(houseInfo.getMajorRenovation()); //Капитальный ремонт
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        String value = houseInfo
                .getMaterialTypes()
                .stream()
                .map(MaterialType::getName)
                .collect(Collectors.joining(", "));
        cell.setCellValue(value); //Серия, тип постройки
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        cell.setCellValue(houseInfo.getFloorType().getName()); //Тип перекрытий
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        value = houseInfo
                .getWallMaterials()
                .stream()
                .map(WallMaterial::getName)
                .collect(Collectors.joining(", "));
        cell.setCellValue(value); //Материал несущих стен
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        Boolean garbageChute = houseInfo.getGarbageChute();
        if (garbageChute != null) {
            cell.setCellValue(garbageChute); //Тип мусоропровода
        }
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        cell.setCellValue(houseInfo.getIsEmergency() ? "Да" : "Нет"); //Дом признан аварийным
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        cell.setCellValue(""); //Выписка из ЕГРН //todo remove me
        cell.setCellStyle(style);

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
        Integer year1 = generalInfo.getYear();
        if (year1 != null) {
            cell.setCellValue(year1); //Год ввода в эксплуатацию
        }
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        cell.setCellValue(generalInfo.getIsEmergency() ? "Да" : "Нет"); //Дом признан аварийным
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        cell.setCellValue(generalInfo.getHouseCondition().getName()); //Состояние дома
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        cell.setCellValue(generalInfo.getApartmentsCount()); //Количество квартир
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        Integer nonResidentialPremises = generalInfo.getNonResidentialPremises();
        if (nonResidentialPremises != null) {
            cell.setCellValue(nonResidentialPremises); //Количество нежилых помещений
        }
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        Integer loggiasNumber = generalInfo.getLoggiasNumber();
        if (loggiasNumber != null) {
            cell.setCellValue(loggiasNumber); //Количество лоджий
        }
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        Integer balconyNumber = generalInfo.getBalconyNumber();
        if (balconyNumber != null) {
            cell.setCellValue(balconyNumber); //Количество балконов
        }
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        EnergyEfficiencyClass energyEfficiencyClass = generalInfo.getEnergyEfficiencyClass();
        if (energyEfficiencyClass != null) {
            cell.setCellValue(energyEfficiencyClass.getName()); //Класс энергетической эффективности
        }
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        cell.setCellValue(generalInfo.getNumberOfEntrances()); //Количество подъездов
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        Integer elevatorsNumber = generalInfo.getElevatorsNumber();
        if (elevatorsNumber != null) {
            cell.setCellValue(elevatorsNumber); //Количество лифтов
        }
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        Integer maxFloor = generalInfo.getMaxFloor();
        if (maxFloor != null) {
            cell.setCellValue(maxFloor); //Наибольшее количество этажей
        }
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        Integer minFloor = generalInfo.getMinFloor();
        if (minFloor != null) {
            cell.setCellValue(minFloor); //Наименьшее количество этажей
        }
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        Integer undergroundFloors = generalInfo.getUndergroundFloors();
        if (undergroundFloors != null) {
            cell.setCellValue(undergroundFloors); //Подземных этажей
        }
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        RepairFormation repairFormation = generalInfo.getRepairFormation();
        if (repairFormation != null) {
            cell.setCellValue(repairFormation.getName()); //Формирование фонда кап. ремонта
        }
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        Double parkingArea = generalInfo.getParkingArea();
        if (parkingArea != null) {
            cell.setCellValue(parkingArea); //Площадь парковки м2
        }
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        cell.setCellValue(generalInfo.getDisablePeopleDevices() ? "Нет" : "Да"); //Наличие в подъездах приспособлений для нужд маломобильных групп населения
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        cell.setCellValue(generalInfo.getHouseType().getName()); //Тип дома
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        cell.setCellValue(generalInfo.getEmergencyDate()); //Дата документа о признании дома аварийным
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        cell.setCellValue(generalInfo.getEmergencyDocumentNumber()); //Номер документа о признании дома аварийным"
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        Double wearOfBuilding = generalInfo.getWearOfBuilding();
        if (wearOfBuilding != null) {
            cell.setCellValue(wearOfBuilding); //Износ здания, %
        }
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        cell.setCellValue(generalInfo.getWearCalculationDate()); //Дата, на которую установлен износ здания
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        cell.setCellValue(generalInfo.getUnsafeRecognizingReason()); //Основание признания дома аварийным
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        cell.setCellValue(generalInfo.getBuildingSquare()); //Площадь многоквартирного дома, кв.м
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        cell.setCellValue(generalInfo.getBuildingResidentialSquare()); //Площадь жилых помещений м2
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        cell.setCellValue(generalInfo.getBuildingNonResidentialSquare()); //Площадь нежилых помещений м2
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        Double buildingCommonPropertySquare = generalInfo.getBuildingCommonPropertySquare();
        if (buildingCommonPropertySquare != null) {
            cell.setCellValue(buildingCommonPropertySquare); //Площадь помещений общего имущества м2
        }
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        Double landCommonPropertySquare = generalInfo.getLandCommonPropertySquare();
        if (landCommonPropertySquare != null) {
            cell.setCellValue(landCommonPropertySquare); //Площадь зем. участка общего имущества м2
        }
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        String materialTYpes = generalInfo
                .getMaterialTypes()
                .stream()
                .map(MaterialType::getName)
                .collect(Collectors.joining(", "));
        cell.setCellValue(materialTYpes); //Серия, тип постройки здания
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        cell.setCellValue(generalInfo.getIsCulturalHeritage() ? "Да" : "Нет"); //Статус объекта культурного наследия
        cell.setCellStyle(style);

        //Инженерные системы
        EngineeringSystems engineeringSystems = houseInfo.getEngineeringSystems();
        cell = row.createCell(cellRowIndex++);
        Ventilation ventilation = engineeringSystems.getVentilation();
        if (ventilation != null) {
            cell.setCellValue(ventilation.getName()); //Вентиляция
        }
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        cell.setCellValue(engineeringSystems.getWaterDisposal().getName()); //Водоотведение
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        GuttersSystem guttersSystem = engineeringSystems.getGuttersSystem();
        if (guttersSystem != null) {
            cell.setCellValue(guttersSystem.getName()); //Система водостоков
        }
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        cell.setCellValue(engineeringSystems.getGasSupply().getName()); //Газоснабжение
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        String hotWaterSystemTypes = engineeringSystems
                .getHotWaterSystemTypes()
                .stream()
                .map(HotWaterSystemType::getName)
                .collect(Collectors.joining(", "));
        cell.setCellValue(hotWaterSystemTypes); //Горячее водоснабжение
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        Boolean fireExtinguishingSystem = engineeringSystems.getFireExtinguishingSystem();
        if (fireExtinguishingSystem != null) {
            cell.setCellValue(fireExtinguishingSystem ? "Да" : "Нет"); //Система пожаротушения
        }
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        cell.setCellValue(engineeringSystems.getHeatSupply().getName()); //Теплоснабжение
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        cell.setCellValue(engineeringSystems.getColdWaterSupply().getName()); //Холодное водоснабжение
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        ElectricitySupply electricitySupply = engineeringSystems.getElectricitySupply();
        if (electricitySupply != null) {
            cell.setCellValue(electricitySupply.getName()); //Электроснабжение
        }
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        cell.setCellValue(engineeringSystems.getNumberOfEntriesIntoHouse()); //Количество вводов в дом, ед.
        cell.setCellStyle(style);

        //********************************Конструктивные элементы
        ConstructionElements constructionElements = houseInfo.getConstructionElements();
        cell = row.createCell(cellRowIndex++);
        Boolean garbageChute1 = constructionElements.getGarbageChute();
        if (garbageChute1 != null) {
            cell.setCellValue(garbageChute1 ? "Да" : "Нет"); //Мусоропровод
        }
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        Integer garbageChuteNumber = constructionElements.getGarbageChuteNumber();
        if (garbageChuteNumber != null) {
            cell.setCellValue(garbageChuteNumber); //Количество мусоропроводов, ед.
        }
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        String loadBearingWalls = constructionElements
                .getLoadBearingWalls()
                .stream()
                .map(LoadBearingWalls::getName)
                .collect(Collectors.joining(", "));
        cell.setCellValue(loadBearingWalls); //Несущие стены
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        Double basementArea = constructionElements.getBasementArea();
        if (basementArea != null) {
            cell.setCellValue(basementArea); //Площадь подвала, кв.м
        }
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        cell.setCellValue(constructionElements.getFoundation().getName()); //Фундамент
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        cell.setCellValue(constructionElements.getFloorType().getName()); //Перекрытия
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        cell.setCellValue(""); //Вид услуги (работы) //todo remove me
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        cell.setCellValue(""); //Подъезд //todo remove me
        cell.setCellStyle(style);

        //******************************Cистема горячего водоснабжения******************************
        HotWaterSupplySystem hotWaterSupplySystem = houseInfo.getHotWaterSupplySystem();
        cell = row.createCell(cellRowIndex++);
        String hotWaterSystemTypes1 = hotWaterSupplySystem
                .getHotWaterSystemTypes()
                .stream()
                .map(HotWaterSystemType::getName)
                .collect(Collectors.joining(", "));
        cell.setCellValue(hotWaterSystemTypes1); //Тип системы горячего водоснабжения
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        Double physicalDeterioration = hotWaterSupplySystem.getPhysicalDeterioration();
        if (physicalDeterioration != null) {
            cell.setCellValue(physicalDeterioration); //Физический износ
        }
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        String networkMaterials = hotWaterSupplySystem
                .getNetworkMaterials()
                .stream()
                .map(NetworkMaterial::getName)
                .collect(Collectors.joining(", "));
        cell.setCellValue(networkMaterials); //Материал сети
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        cell.setCellValue(hotWaterSupplySystem.getNetworkThermalInsulationMaterial().getName()); //Материал теплоизоляции сети
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        String risersMaterials = hotWaterSupplySystem.getRisersMaterials()
                .stream().map(RisersMaterial::getName)
                .collect(Collectors.joining(", "));
        cell.setCellValue(risersMaterials); //Материал стояков
        cell.setCellStyle(style);

        //******************************Система водоотведения******************************
        DrainageSystem drainageSystem = houseInfo.getDrainageSystem();
        cell = row.createCell(cellRowIndex++);
        cell.setCellValue(drainageSystem.getPhysicalDeterioration()); //Физический износ
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        Integer lastOverhaulYear = drainageSystem.getLastOverhaulYear();
        if (lastOverhaulYear != null) {
            cell.setCellValue(lastOverhaulYear); //Год проведения последнего капитального ремонта
        }
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        cell.setCellValue(drainageSystem.getDrainageSystemType().getName()); //Тип системы водоотведения
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        String networkMaterials1 = drainageSystem.getNetworkMaterials()
                .stream()
                .map(com.example.mingkhparser.models.drainagesystem.NetworkMaterial::getName)
                .collect(Collectors.joining(", "));
        cell.setCellValue(networkMaterials1); //Материал сети
        cell.setCellStyle(style);

        //******************************Система газоснабжения******************************
        GasSupplySystem gasSupplySystem = houseInfo.getGasSupplySystem();
        cell = row.createCell(cellRowIndex++);
        Integer lastOverhaulYear1 = gasSupplySystem.getLastOverhaulYear();
        if (lastOverhaulYear1 != null) {
            cell.setCellValue(lastOverhaulYear1); //Год проведения последнего капитального ремонта
        }
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        cell.setCellValue(gasSupplySystem.getGasSupplySystemType().getName()); //Тип системы газоснабжения
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        cell.setCellValue(gasSupplySystem.getGasSupplySystemInletsNumber()); //Количество вводов системы газоснабжения
        cell.setCellStyle(style);

        //******************************Система электроснабжения******************************
        ElectricitySupplySystem electricitySupplySystem = houseInfo.getElectricitySupplySystem();
        cell = row.createCell(cellRowIndex++);
        cell.setCellValue(electricitySupplySystem.getPhysicalDeterioration()); //Физический износ
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        Integer lastOverhaulYear2 = electricitySupplySystem.getLastOverhaulYear();
        if (lastOverhaulYear2 != null) {
            cell.setCellValue(lastOverhaulYear2); //Год проведения последнего капитального ремонта
        }
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        cell.setCellValue(electricitySupplySystem.getPowerSupplyInputNumbers()); //Количество вводов системы электроснабжения
        cell.setCellStyle(style);

        //******************************Фундамент******************************
        Foundation foundation = houseInfo.getFoundation();
        cell = row.createCell(cellRowIndex++);
        cell.setCellValue(foundation.getFoundationType().getName()); //Тип фундамента
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        cell.setCellValue(foundation.getFoundationMaterial().getName()); //Материал фундамента
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        cell.setCellValue(foundation.getBlindArea()); //Площадь отмостки
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        cell.setCellValue(foundation.getPhysicalDeterioration()); //Физический износ
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        Integer lastOverhaulYear3 = foundation.getLastOverhaulYear();
        if (lastOverhaulYear3 != null) {
            cell.setCellValue(lastOverhaulYear3); //Год проведения последнего капитального ремонта
        }
        cell.setCellStyle(style);

        //******************************Внутренние стены******************************
        InnerWalls innerWalls = houseInfo.getInnerWalls();
        cell = row.createCell(cellRowIndex++);
        String wallMaterials = innerWalls
                .getWallMaterials()
                .stream()
                .map(WallMaterial::getName)
                .collect(Collectors.joining(", "));
        cell.setCellValue(wallMaterials); //Тип внутренних стен
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        cell.setCellValue(innerWalls.getPhysicalDeterioration()); //Физический износ
        cell.setCellStyle(style);

        //******************************Фасад******************************
        Facade facade = houseInfo.getFacade();
        cell = row.createCell(cellRowIndex++);
        String outerWallsMaterials = facade.getOuterWallsMaterials()
                .stream()
                .map(WallMaterial::getName)
                .collect(Collectors.joining(", "));
        cell.setCellValue(outerWallsMaterials); //Тип наружных стен
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        cell.setCellValue(facade.getExternalInsulationType().getName()); //Тип наружного утепления фасада
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        String facadeFinishingMaterials = facade.getFacadeFinishingMaterials()
                .stream()
                .map(FacadeFinishingMaterial::getName)
                .collect(Collectors.joining(", "));
        cell.setCellValue(facadeFinishingMaterials); //Материал отделки фасада
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        cell.setCellValue(facade.getPhysicalDeterioration()); //Физический износ
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        Integer lastOverhaulYear4 = facade.getLastOverhaulYear();
        if (lastOverhaulYear4 != null) {
            cell.setCellValue(lastOverhaulYear4); //Год проведения последнего капитального ремонта
        }
        cell.setCellStyle(style);

        //******************************Перекрытия******************************
        Floors floors = houseInfo.getFloors();
        cell = row.createCell(cellRowIndex++);
        cell.setCellValue(floors.getFloorType().getName()); //Тип перекрытия
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        cell.setCellValue(floors.getPhysicalDeterioration()); //Физический износ
        cell.setCellStyle(style);

        //******************************Крыша******************************
        Roof roof = houseInfo.getRoof();
        cell = row.createCell(cellRowIndex++);
        cell.setCellValue(roof.getRoofShape().getName()); //Форма крыши
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        String insulatingLayers = roof.getInsulatingLayers()
                .stream()
                .map(InsulatingLayers::getName)
                .collect(Collectors.joining(", "));
        cell.setCellValue(insulatingLayers); //Утепляющие слои чердачных перекрытий
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        String bearingTypes = roof.getBearingTypes()
                .stream()
                .map(BearingType::getName)
                .collect(Collectors.joining(", "));
        cell.setCellValue(bearingTypes); //Вид несущей части
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        String roofTypes = roof.getRoofTypes()
                .stream()
                .map(RoofType::getName)
                .collect(Collectors.joining(", "));
        cell.setCellValue(roofTypes); //Тип кровли
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        cell.setCellValue(roof.getPhysicalDeterioration()); //Физический износ кровли
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        Integer lastOverhaulYear5 = roof.getLastOverhaulYear();
        if (lastOverhaulYear5 != null) {
            cell.setCellValue(lastOverhaulYear5); //Год проведения последнего капитального ремонта кровли
        }
        cell.setCellStyle(style);

        //******************************Окна******************************
        Windows windows = houseInfo.getWindows();
        cell = row.createCell(cellRowIndex++);
        cell.setCellValue(windows.getPhysicalDeterioration()); //Физический износ
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        String windowsTypes = windows.getWindowsTypes()
                .stream()
                .map(WindowsType::getName)
                .collect(Collectors.joining(", "));
        cell.setCellValue(windowsTypes); //Материал окон
        cell.setCellStyle(style);

        //******************************Двери******************************
        Doors doors = houseInfo.getDoors();
        cell = row.createCell(cellRowIndex++);
        cell.setCellValue(doors.getPhysicalDeterioration()); //Физический износ
        cell.setCellStyle(style);

        //******************************Отделочные покрытия помещений общего пользования******************************
        CommonAreasFinishingCoatings commonAreasFinishingCoatings = houseInfo.getCommonAreasFinishingCoatings();
        cell = row.createCell(cellRowIndex++);
        cell.setCellValue(commonAreasFinishingCoatings.getPhysicalDeterioration()); //Физический износ
        cell.setCellStyle(style);

        //******************************Система отопления******************************
        HeatingSystem heatingSystem = houseInfo.getHeatingSystem();
        cell = row.createCell(cellRowIndex++);
        cell.setCellValue(heatingSystem.getPhysicalDeterioration()); //Физический износ
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        String networkMaterials2 = heatingSystem.getNetworkMaterials()
                .stream()
                .map(com.example.mingkhparser.models.heatingsystem.NetworkMaterial::getName)
                .collect(Collectors.joining(", "));
        cell.setCellValue(networkMaterials2); //Материал сети
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        String thermalInsulationMaterials = heatingSystem.getThermalInsulationMaterials()
                .stream()
                .map(ThermalInsulationMaterial::getName)
                .collect(Collectors.joining(", "));
        cell.setCellValue(thermalInsulationMaterials); //Материал теплоизоляции сети
        cell.setCellStyle(style);

        //******************************Стояки системы отопления******************************
        HeatingSystemRisers heatingSystemRisers = houseInfo.getHeatingSystemRisers();
        cell = row.createCell(cellRowIndex++);
        cell.setCellValue(heatingSystemRisers.getPhysicalDeterioration()); //Физический износ
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        cell.setCellValue(heatingSystemRisers.getApartmentWiringType().getName()); //Тип поквартирной разводки внутридомовой системы отопления
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        String networkMaterials3 = heatingSystemRisers.getMaterialTypes()
                .stream()
                .map(com.example.mingkhparser.models.heatingsystemrisers.MaterialType::getName)
                .collect(Collectors.joining(", "));
        cell.setCellValue(networkMaterials3); //Материал
        cell.setCellStyle(style);

        //******************************Запорная арматура системы отопления******************************
        ShutoffValvesHeatingSystem shutoffValvesHeatingSystem = houseInfo.getShutoffValvesHeatingSystem();
        cell = row.createCell(cellRowIndex++);
        cell.setCellValue(shutoffValvesHeatingSystem.getPhysicalDeterioration()); //Физический износ
        cell.setCellStyle(style);

        //******************************Отопительные приборы******************************
        HeatingDevices heatingDevices = houseInfo.getHeatingDevices();
        cell = row.createCell(cellRowIndex++);
        cell.setCellValue(heatingDevices.getPhysicalDeterioration()); //Физический износ
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        String heatingDevicesTypes = heatingDevices.getHeatingDevicesTypes()
                .stream()
                .map(HeatingDevicesType::getName)
                .collect(Collectors.joining(", "));
        cell.setCellValue(heatingDevicesTypes); //Тип отопительных приборов
        cell.setCellStyle(style);

        //******************************Система холодного водоснабжения******************************
        ColdWaterSystem coldWaterSystem = houseInfo.getColdWaterSystem();
        cell = row.createCell(cellRowIndex++);
        cell.setCellValue(coldWaterSystem.getPhysicalDeterioration()); //Физический износ
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        String networkMaterial = coldWaterSystem.getNetworkMaterials()
                .stream().map(com.example.mingkhparser.models.coldwatersystem.NetworkMaterial::getName)
                .collect(Collectors.joining(", "));
        cell.setCellValue(networkMaterial); //Материал сети
        cell.setCellStyle(style);

        //******************************Стояки системы холодного водоснабжения******************************
        ColdWaterSupplySystemRisers coldWaterSupplySystemRisers = houseInfo.getColdWaterSupplySystemRisers();
        cell = row.createCell(cellRowIndex++);
        cell.setCellValue(coldWaterSupplySystemRisers.getPhysicalDeterioration()); //Физический износ
        cell.setCellStyle(style);

        cell = row.createCell(cellRowIndex++);
        String networkMaterials4 = coldWaterSupplySystemRisers.getNetworkMaterials()
                .stream()
                .map(com.example.mingkhparser.models.coldwatersupplysystemrisers.NetworkMaterial::getName)
                .collect(Collectors.joining(", "));
        cell.setCellValue(networkMaterials4); //Материал сети
        cell.setCellStyle(style);

        //******************************Запорная арматура системы холодного водоснабжения******************************
        ShutoffValvesColdWaterSupplySystem shutoffValvesColdWaterSupplySystem = houseInfo.getShutoffValvesColdWaterSupplySystem();
        cell = row.createCell(cellRowIndex++);
        cell.setCellValue(shutoffValvesColdWaterSupplySystem.getPhysicalDeterioration()); //Физический износ
        cell.setCellStyle(style);

        //******************************Стояки системы горячего водоснабжения******************************
        HotWaterSupplySystemRisers hotWaterSupplySystemRisers = houseInfo.getHotWaterSupplySystemRisers();
        cell = row.createCell(cellRowIndex++);
        Integer physicalDeterioration1 = hotWaterSupplySystemRisers.getPhysicalDeterioration();
        if (physicalDeterioration1 != null) {
            cell.setCellValue(physicalDeterioration1); //Физический износ
        }
        cell.setCellStyle(style);

        //******************************Запорная арматура системы горячего водоснабжения******************************
        ShutoffValvesHotWaterSupplySystem shutoffValvesHotWaterSupplySystem = houseInfo.getShutoffValvesHotWaterSupplySystem();
        cell = row.createCell(cellRowIndex++);
        cell.setCellValue(shutoffValvesHotWaterSupplySystem.getPhysicalDeterioration()); //Физический износ
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
