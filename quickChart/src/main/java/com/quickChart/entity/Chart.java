package com.quickChart.entity;

import java.util.ArrayList;
import java.util.Collections;

public class Chart {
    private String title;
    private int width;
    private int height;
    private String type;
    private ArrayList<String> labels = new ArrayList<>();

    //Will have to migrate to a different class called DataSet
    private ArrayList<Integer> dataSet = new ArrayList<>();
    private String label;
    private String border_color;
    private String background_color;


    public Chart() {
    }

    public Chart(String title, int width, int height, String type, String label, String border_color, String background_color) {
        this.title = title;
        this.width = width;
        this.height = height;
        this.type = type;
        this.label = label;
        this.border_color = border_color;
        this.background_color = background_color;
    }

    public ArrayList<String> getLabels() {
        return labels;
    }

    public void setLabels(ArrayList<String> labels) {
        this.labels = labels;
    }

    public ArrayList<Integer> getDataSet() {
        return dataSet;
    }

    public void setDataSet(ArrayList<Integer> dataSet) {
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

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getBorder_color() {
        return border_color;
    }

    public void setBorder_color(String border_color) {
        this.border_color = border_color;
    }

    public String getBackground_color() {
        return background_color;
    }

    public void setBackground_color(String background_color) {
        this.background_color = background_color;
    }

}
