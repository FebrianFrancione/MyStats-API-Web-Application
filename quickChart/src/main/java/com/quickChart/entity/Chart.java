package com.quickChart.entity;

import java.util.ArrayList;
import java.util.Collections;

public class Chart {
    private String title;
    private int width;
    private int height;
    private String type;
    private ArrayList<String> labels = new ArrayList<>();
    private DataSet dataSet;

    public Chart() {
    }

    public Chart(String title, int width, int height, String type, String label, String border_color, String background_color) {
        super();
        this.title = title;
        this.width = width;
        this.height = height;
        this.type = type;
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
