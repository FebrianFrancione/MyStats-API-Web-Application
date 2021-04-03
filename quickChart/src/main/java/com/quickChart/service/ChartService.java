package com.quickChart.service;

public interface ChartService {
    public String getChart();
    public String createChart(String title, int width, int height, String type);
}
