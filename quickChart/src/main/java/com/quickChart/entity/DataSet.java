package com.quickChart.entity;

import java.util.ArrayList;

public class DataSet {
    private int datasetId;
    private ArrayList<Integer> data = new ArrayList<>();
    private String label;
    private String border_color = "#F28E2B";
    private String background_color = "#FFEAD6";
    private boolean fill = true;
    private int borderWidth = 2;
    private int pointRadius = 3;
    private boolean showLine = true;
    private ArrayList<String> backgroundColors = new ArrayList<>();

    public DataSet() {
    }

    /*
    ** Bar Dataset Constructor
     */
    public DataSet(int datasetId, String label, String border_color, String background_color, int borderWidth) {
        super();
        this.label = label;
        this.border_color = border_color;
        this.background_color = background_color;
        this.borderWidth = borderWidth;
        this.datasetId = datasetId;
    }

    /*
     ** Pie/Doughnut Dataset Constructor
     */
    public DataSet(int datasetId, String label, int borderWidth, ArrayList<String> backgroundColors) {
        super();
        this.label = label;
        this.borderWidth = borderWidth;
        this.backgroundColors = backgroundColors;
        this.datasetId = datasetId;
    }

    /*
     ** Line Dataset Constructor
     */
    public DataSet(int datasetId, String label, String border_color, String background_color, int borderWidth, boolean fill, int pointRadius, boolean showLine) {
        super();
        this.label = label;
        this.border_color = border_color;
        this.background_color = background_color;
        this.borderWidth = borderWidth;
        this.fill = fill;
        this.pointRadius = pointRadius;
        this.showLine = showLine;
        this.datasetId = datasetId;
    }

    public int getDatasetId() {
        return datasetId;
    }

    public void setDatasetId(int datasetId) {
        this.datasetId = datasetId;
    }

    public ArrayList<String> getBackgroundColors() {
        return backgroundColors;
    }

    public void setBackgroundColors(ArrayList<String> backgroundColors) {
        this.backgroundColors = backgroundColors;
    }

    public boolean isShowLine() {
        return showLine;
    }

    public void setShowLine(boolean showLine) {
        this.showLine = showLine;
    }

    public int getPointRadius() {
        return pointRadius;
    }

    public void setPointRadius(int pointRadius) {
        this.pointRadius = pointRadius;
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
