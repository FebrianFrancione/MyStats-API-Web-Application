package com.quickChart.service;

import com.quickChart.entity.Chart;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public interface ChartService {
    public String getChart(int chartId);
    public List<Chart> getCharts(int userId);
    public String createChart(Chart chart);
    public String getDataSetTemplate(String chartType);
    public ArrayList<String> generateColors(int length);
    public String sendEmail(String email, String url);
    public void downloadImg(String name, String url);
}
