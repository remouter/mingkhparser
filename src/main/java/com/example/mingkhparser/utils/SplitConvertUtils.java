package com.example.mingkhparser.utils;

public class SplitConvertUtils {
    public static Integer stringToInteger(String value) {
        return Integer.valueOf(value.split(" ")[0]);
    }

    public static Double stringToDouble(String value) {
        return Double.valueOf(value.split(" ")[0]);
    }
}
