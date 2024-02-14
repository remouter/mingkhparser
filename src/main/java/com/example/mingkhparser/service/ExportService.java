package com.example.mingkhparser.service;

import com.example.mingkhparser.models.HouseInfo;

import java.util.List;

public interface ExportService {
    void export(List<HouseInfo> source);
    void test(List<HouseInfo> source);
}
