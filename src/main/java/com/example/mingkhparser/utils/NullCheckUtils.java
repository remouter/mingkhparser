package com.example.mingkhparser.utils;

import com.example.mingkhparser.models.IEnum;
import org.apache.poi.ss.usermodel.Cell;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.stream.Collectors;

public class NullCheckUtils {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public static <T extends IEnum> void setCheckAndJoin(final Set<T> values, final Cell cell) {
        if (values != null) {
            String join = values.stream()
                    .map(T::getName)
                    .collect(Collectors.joining(", "));
            cell.setCellValue(join);
        }
    }

    public static <T extends IEnum> void setIntegerAndSet(final Integer value, final Cell cell) {
        if (value != null) {
            cell.setCellValue(value);
        }
    }

    public static <T extends IEnum> void setDoubleAndSet(final Double value, final Cell cell) {
        if (value != null) {
            cell.setCellValue(value);
        }
    }

    public static <T extends IEnum> void setBooleanAndSet(final Boolean value, final Cell cell) {
        if (value != null) {
            cell.setCellValue(value ? "Да" : "Нет");
        }
    }

    public static <T extends IEnum> void enumCheckAndSet(final T value, final Cell cell) {
        if (value != null) {
            cell.setCellValue(value.getName());
        }
    }

    public static <T extends IEnum> void setLocalDateAndSet(final LocalDate value, final Cell cell) {
        if (value != null) {
            cell.setCellValue(formatter.format(value));
        }
    }
}
