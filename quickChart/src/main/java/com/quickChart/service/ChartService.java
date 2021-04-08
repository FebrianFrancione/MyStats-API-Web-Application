package com.quickChart.service;

import com.quickChart.entity.Chart;

import java.util.ArrayList;

public interface ChartService {
    public String getChart();
    public String createChart(Chart chart);
    public String getDataSetTemplate(String chartType);
}
