package com.quickChart.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Chart {
    private int chartId;
    private String title = "test chart";
    private int width = 600;
    private int height = 400;
    private String type;
    private ArrayList<String> labels = new ArrayList<>();
    private DataSet dataSet;
    private String chartUrl;
    private Map<Integer, String> labelsMap = new HashMap<>();
    private int userId;

    public Chart() {
    }

    public Chart(int chartId, String title, String chartUrl, int width, int height, String type) {
        super();
        this.chartId = chartId;
        this.title = title;
        this.width = width;
        this.height = height;
        this.type = type;
        this.chartUrl = chartUrl;
    }

    public Chart(int chartId, String title, String chartUrl, int width, int height, String type, Map<Integer, String> labelsMap, DataSet dataset, int userId) {
        super();
        this.chartId = chartId;
        this.title = title;
        this.width = width;
        this.height = height;
        this.type = type;
        this.chartUrl = chartUrl;
        this.labelsMap = labelsMap;
        this.dataSet = dataset;
        this.userId = userId;

        for (Map.Entry<Integer, String> label : labelsMap.entrySet()) {
            labels.add(label.getValue());
        }
    }

    public int getChartId() {
        return chartId;
    }

    public void setChartId(int chartId) {
        this.chartId = chartId;
    }

    public String getChartUrl() {
        return chartUrl;
    }

    public void setChartUrl(String chartUrl) {
        this.chartUrl = chartUrl;
    }

    public ArrayList<String> getLabels() {
        return labels;
    }

    public void setLabels(ArrayList<String> labels) {
        this.labels = labels;
    }

    public DataSet getDataSet() {
        return dataSet;
    }

    public void setDataSet(DataSet dataSet) {
        this.dataSet = dataSet;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<Integer, String> getLabelsMap() {
        return labelsMap;
    }

    public void setLabelsMap(Map<Integer, String> labelsMap) {
        this.labelsMap = labelsMap;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
