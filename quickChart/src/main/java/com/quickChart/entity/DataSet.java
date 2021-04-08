package com.quickChart.entity;

import java.util.ArrayList;

public class DataSet {
    private ArrayList<Integer> data = new ArrayList<>();
    private String label;
    private String border_color = "#F28E2B";
    private String background_color = "#F28E2B33";
    private boolean fill;
    private int borderWidth = 2;

    public DataSet() {
    }

    public DataSet(ArrayList<Integer> data, String label, String border_color, String background_color, boolean fill, int borderWidth) {
        super();
        this.data = data;
        this.label = label;
        this.border_color = border_color;
        this.background_color = background_color;
        this.fill = fill;
        this.borderWidth = borderWidth;
    }

    public ArrayList<Integer> getData() {
        return data;
    }

    public void setData(ArrayList<Integer> data) {
        this.data = data;
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

    public boolean isFill() {
        return fill;
    }

    public void setFill(boolean fill) {
        this.fill = fill;
    }

    public int getBorderWidth() {
        return borderWidth;
    }

    public void setBorderWidth(int borderWidth) {
        this.borderWidth = borderWidth;
    }
}
