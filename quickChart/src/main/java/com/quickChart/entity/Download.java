package com.quickChart.entity;

public class Download {
    private String name;
    private String chartUrl;

    public Download(){}

    public Download(String name, String chartUrl){
        this.name = name;
        this.chartUrl = chartUrl;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getChartUrl() {
        return chartUrl;
    }

    public void setChartUrl(String chartUrl) {
        this.chartUrl = chartUrl;
    }
}
