package com.quickChart.entity;

import java.util.ArrayList;

public class Chart {
    private String title;
    private int width;
    private int height;
    private String type;

    //Setting lables here for now, but it needs to be a collection
    //We don't know how many labels user wants
    private String label1;
    private String label2;
    private String label3;
    private String label4;
    private ArrayList<String> labels = new ArrayList<>();

    //Will have to migrate to a different class called DataSet
    private String label;
    private String border_color;
    private String background_color;
    private int value1;
    private int value2;
    private int value3;
    private int value4;
    private ArrayList<Integer> dataSet = new ArrayList<>();

    public Chart() {
    }

    public Chart(String title, int width, int height, String type, String label1, String label2, String label3, String label4, String label, String border_color, String background_color, int value1, int value2, int value3, int value4) {
        this.title = title;
        this.width = width;
        this.height = height;
        this.type = type;
        this.label1 = label1;
        this.label2 = label2;
        this.label3 = label3;
        this.label4 = label4;
        this.label = label;
        this.border_color = border_color;
        this.background_color = background_color;
        this.value1 = value1;
        this.value2 = value2;
        this.value3 = value3;
        this.value4 = value4;
        setLabels();
        setDataSet();
    }

    public ArrayList<String> getLabels() {
        return labels;
    }

    public void setLabels() {
        labels.add(label1);
        labels.add(label2);
        labels.add(label3);
        labels.add(label4);
    }

    public ArrayList<Integer> getDataSet() {
        return dataSet;
    }

    public void setDataSet() {
        dataSet.add(value1);
        dataSet.add(value2);
        dataSet.add(value3);
        dataSet.add(value4);
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

    public String getLabel1() {
        return label1;
    }

    public void setLabel1(String label1) {
        this.label1 = label1;
    }

    public String getLabel2() {
        return label2;
    }

    public void setLabel2(String label2) {
        this.label2 = label2;
    }

    public String getLabel3() {
        return label3;
    }

    public void setLabel3(String label3) {
        this.label3 = label3;
    }

    public String getLabel4() {
        return label4;
    }

    public void setLabel4(String label4) {
        this.label4 = label4;
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

    public int getValue1() {
        return value1;
    }

    public void setValue1(int value1) {
        this.value1 = value1;
    }

    public int getValue2() {
        return value2;
    }

    public void setValue2(int value2) {
        this.value2 = value2;
    }

    public int getValue3() {
        return value3;
    }

    public void setValue3(int value3) {
        this.value3 = value3;
    }

    public int getValue4() {
        return value4;
    }

    public void setValue4(int value4) {
        this.value4 = value4;
    }
}
