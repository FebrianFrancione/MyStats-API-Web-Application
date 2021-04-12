package com.quickChart.entity;

import java.util.ArrayList;
import java.util.Collections;

public class Chart {
    private int chartId;
    private String title = "test chart";
    private int width = 600;
    private int height = 400;
    private String type;
    private ArrayList<String> labels = new ArrayList<>();
    private DataSet dataSet;
    private String chartUrl;

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

    public Chart(int chartId, String title, String chartUrl, int width, int height, String type, ArrayList<String> labels) {
        super();
        this.chartId = chartId;
        this.title = title;
        this.width = width;
        this.height = height;
        this.type = type;
        this.chartUrl = chartUrl;
        this.labels = labels;
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

}
