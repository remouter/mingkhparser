package com.example.mingkhparser.service.export;

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
import java.io.IOException;
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
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Houses");
            setColumnsWidth(sheet);

            Row header = sheet.createRow(0);
            createHeader(workbook, header);

            Row row = sheet.createRow(1); //todo counter
            createInfoBlock(workbook, row, houseInfo);

            saveFile(workbook);
            log.info("Finish export method");
        } catch (Exception ignored) {
            ignored.printStackTrace();
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

    private void createHeader(final XSSFWorkbook workbook, final Row header) {
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        XSSFFont font = workbook.createFont();
        font.setFontName("Arial Narrow");
        font.setFontHeightInPoints((short) 16);
        font.setBold(true);
        headerStyle.setFont(font);

        int headerRowIndex = 0;
        Cell headerCell = header.createCell(headerRowIndex++);
        headerCell.setCellValue("№");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(headerRowIndex++);
        headerCell.setCellValue("Адрес");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(headerRowIndex++);
        headerCell.setCellValue("Год постройки");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(headerRowIndex++);
        headerCell.setCellValue("Количество этажей");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(headerRowIndex++);
        headerCell.setCellValue("Тип дома");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(headerRowIndex++);
        headerCell.setCellValue("Жилых помещений");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(headerRowIndex++);
        headerCell.setCellValue("Капитальный ремонт");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(headerRowIndex++);
        headerCell.setCellValue("Серия, тип постройки");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(headerRowIndex++);
        headerCell.setCellValue("Тип перекрытий");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(headerRowIndex++);
        headerCell.setCellValue("Материал несущих стен");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(headerRowIndex++);
        headerCell.setCellValue("Тип мусоропровода");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(headerRowIndex++);
        headerCell.setCellValue("Дом признан аварийным");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(headerRowIndex++);
        headerCell.setCellValue("Выписка из ЕГРН");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(headerRowIndex++);
        headerCell.setCellValue("Кадастровый номер");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(headerRowIndex++);
        headerCell.setCellValue("Код ОКТМО");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(headerRowIndex++);
        headerCell.setCellValue("Управляющая компания");
        headerCell.setCellStyle(headerStyle);

        CellStyle style = workbook.createCellStyle();
        style.setWrapText(true);
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
    }

    private void saveFile(XSSFWorkbook workbook) throws IOException {
        File currDir = new File(".");
        String path = currDir.getAbsolutePath();
        String fileLocation = path.substring(0, path.length() - 1) + "houses.xlsx";

        FileOutputStream outputStream = new FileOutputStream(fileLocation);
        workbook.write(outputStream);
    }
}
