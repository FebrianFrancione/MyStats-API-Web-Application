package com.quickChart.service;

import com.quickChart.entity.Chart;
import com.quickChart.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public interface ChartService {
    public Chart getChart(int chartId);
    public List<Chart> getCharts(int userId);
    public String createChart(Chart chart, int userId);
    public boolean updateChart(Chart chart);
    public String getDataSetTemplate(String chartType);
    public ArrayList<String> generateColors(int length);
    public String sendEmail(String email, String url, String msg);
    public boolean downloadImg(String url, String title);
    public Chart uploadCSV(Chart chart, MultipartFile file);
    public boolean deleteChart(int chartId);
    void createUser(User user);
}
