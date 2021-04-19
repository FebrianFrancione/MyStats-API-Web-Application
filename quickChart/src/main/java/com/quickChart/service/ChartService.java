package com.quickChart.service;

import com.quickChart.entity.Chart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public interface ChartService {
    public Chart getChart(int chartId);
    public List<Chart> getCharts(int userId);
    public String createChart(Chart chart, int userId);
    public boolean updateChart(Chart chart);
    public String getDataSetTemplate(String chartType);
    public ArrayList<String> generateColors(int length);
    public String sendEmail(String email, String url);
    public void downloadImg(String url);
    public Chart uploadCSV(Chart chart, MultipartFile file);
}
