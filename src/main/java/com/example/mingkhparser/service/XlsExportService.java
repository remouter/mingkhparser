package com.example.mingkhparser.service;

import com.example.mingkhparser.models.HouseInfo;
import com.example.mingkhparser.models.WallMaterial;
import com.example.mingkhparser.models.generalinfo.MaterialType;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class XlsExportService implements ExportService {

    @Override
    public void export(List<HouseInfo> source) {
        log.info("Start export method");
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Persons");
            sheet.setColumnWidth(0, 6000);
            sheet.setColumnWidth(1, 4000);

            Row header = sheet.createRow(0);

            CellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            XSSFFont font = ((XSSFWorkbook) workbook).createFont();
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
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Houses");
            sheet.setColumnWidth(0, 4000);
            sheet.setColumnWidth(1, 4000);
            sheet.setColumnWidth(2, 4000);
            sheet.setColumnWidth(3, 4000);
            sheet.setColumnWidth(4, 4000);
            sheet.setColumnWidth(5, 4000);
            sheet.setColumnWidth(6, 4000);
            sheet.setColumnWidth(7, 4000);
            sheet.setColumnWidth(8, 4000);
            sheet.setColumnWidth(9, 4000);
            sheet.setColumnWidth(10, 4000);
            sheet.setColumnWidth(11, 4000);
            sheet.setColumnWidth(12, 4000);
            sheet.setColumnWidth(13, 4000);
            sheet.setColumnWidth(14, 4000);
            sheet.setColumnWidth(15, 4000);

            Row header = sheet.createRow(0);

            //todo uncomment me
//            CellStyle headerStyle = workbook.createCellStyle();
//            headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
//            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

//            XSSFFont font = ((XSSFWorkbook) workbook).createFont();
//            font.setFontName("Arial");
//            font.setFontHeightInPoints((short) 16);
//            font.setBold(true);
//            headerStyle.setFont(font);

            Cell headerCell = header.createCell(0);
            headerCell.setCellValue("№");
//            headerCell.setCellStyle(headerStyle);

            headerCell = header.createCell(1);
            headerCell.setCellValue("Адрес");
//            headerCell.setCellStyle(headerStyle);

            headerCell = header.createCell(2);
            headerCell.setCellValue("Год постройки");
//            headerCell.setCellStyle(headerStyle);

            headerCell = header.createCell(3);
            headerCell.setCellValue("Количество этажей");
//            headerCell.setCellStyle(headerStyle);

            headerCell = header.createCell(4);
            headerCell.setCellValue("Тип дома");
//            headerCell.setCellStyle(headerStyle);

            headerCell = header.createCell(5);
            headerCell.setCellValue("Жилых помещений");
//            headerCell.setCellStyle(headerStyle);

            headerCell = header.createCell(6);
            headerCell.setCellValue("Капитальный ремонт");
//            headerCell.setCellStyle(headerStyle);

            headerCell = header.createCell(7);
            headerCell.setCellValue("Серия, тип постройки");
//            headerCell.setCellStyle(headerStyle);

            headerCell = header.createCell(8);
            headerCell.setCellValue("Тип перекрытий");
//            headerCell.setCellStyle(headerStyle);

            headerCell = header.createCell(9);
            headerCell.setCellValue("Материал несущих стен");
//            headerCell.setCellStyle(headerStyle);

            headerCell = header.createCell(10);
            headerCell.setCellValue("Тип мусоропровода");
//            headerCell.setCellStyle(headerStyle);

            headerCell = header.createCell(11);
            headerCell.setCellValue("Дом признан аварийным");
//            headerCell.setCellStyle(headerStyle);

            headerCell = header.createCell(12);
            headerCell.setCellValue("Выписка из ЕГРН");
//            headerCell.setCellStyle(headerStyle);

            headerCell = header.createCell(13);
            headerCell.setCellValue("Кадастровый номер");
//            headerCell.setCellStyle(headerStyle);

            headerCell = header.createCell(14);
            headerCell.setCellValue("Код ОКТМО");
//            headerCell.setCellStyle(headerStyle);

            headerCell = header.createCell(15);
            headerCell.setCellValue("Управляющая компания");
//            headerCell.setCellStyle(headerStyle);

            //todo uncomment me
//            CellStyle style = workbook.createCellStyle();
//            style.setWrapText(true);

            Row row = sheet.createRow(1);

            Cell cell = row.createCell(0);
            cell.setCellValue(1); //todo counter
//            cell.setCellStyle(style);

            cell = row.createCell(1);
            cell.setCellValue(houseInfo.getAddress()); //Адрес
//            cell.setCellStyle(style);

            cell = row.createCell(2);
            Integer year = houseInfo.getYear();
            if (year != null) {
                cell.setCellValue(year); //Год постройки
            }
//            cell.setCellStyle(style);

            cell = row.createCell(3);
            cell.setCellValue(houseInfo.getFloor()); //Количество этажей
//            cell.setCellStyle(style);

            cell = row.createCell(4);
            cell.setCellValue(houseInfo.getHouseType().getName()); //Тип дома
//            cell.setCellStyle(style);

            cell = row.createCell(5);
            cell.setCellValue(houseInfo.getApartmentsCount()); //Жилых помещений
//            cell.setCellStyle(style);

            cell = row.createCell(6);
            cell.setCellValue(houseInfo.getMajorRenovation()); //Капитальный ремонт
//            cell.setCellStyle(style);

            cell = row.createCell(7);
            String value = houseInfo
                    .getMaterialTypes()
                    .stream()
                    .map(MaterialType::getName)
                    .collect(Collectors.joining(", "));
            cell.setCellValue(value); //Серия, тип постройки
//            cell.setCellStyle(style);

            cell = row.createCell(8);
            cell.setCellValue(houseInfo.getFloorType().getName()); //Тип перекрытий
//            cell.setCellStyle(style);

            cell = row.createCell(9);
            value = houseInfo
                    .getWallMaterials()
                    .stream()
                    .map(WallMaterial::getName)
                    .collect(Collectors.joining(", "));
            cell.setCellValue(value); //Материал несущих стен
//            cell.setCellValue(houseInfo.getWallMaterials()); //Материал несущих стен
//            cell.setCellStyle(style);

            cell = row.createCell(10);
            Boolean garbageChute = houseInfo.getGarbageChute();
            if(garbageChute != null) {
                cell.setCellValue(garbageChute); //Тип мусоропровода
            }
//            cell.setCellStyle(style);

            cell = row.createCell(11);
            cell.setCellValue(houseInfo.getIsEmergency() ? "Да" : "Нет"); //Дом признан аварийным
//            cell.setCellStyle(style);

            cell = row.createCell(12);
            cell.setCellValue(""); //Выписка из ЕГРН //todo remove me
//            cell.setCellStyle(style);

            cell = row.createCell(13);
            cell.setCellValue(houseInfo.getCadastralNumber()); //Кадастровый номер
//            cell.setCellStyle(style);

            cell = row.createCell(14);
            cell.setCellValue(houseInfo.getOktmoCode()); //Код ОКТМО
//            cell.setCellStyle(style);

            cell = row.createCell(15);
            cell.setCellValue(houseInfo.getManagementCompany()); //Управляющая компания
//            cell.setCellStyle(style);


            File currDir = new File(".");
            String path = currDir.getAbsolutePath();
            String fileLocation = path.substring(0, path.length() - 1) + "houses.xlsx";

            FileOutputStream outputStream = new FileOutputStream(fileLocation);
            workbook.write(outputStream);
            log.info("Finish export method");
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }
    }
}
