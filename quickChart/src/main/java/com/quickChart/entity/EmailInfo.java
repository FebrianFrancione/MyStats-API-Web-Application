package com.quickChart.entity;

public class EmailInfo {
    private String emailTo;
    private String chartUrl;

    public EmailInfo(){}

    public EmailInfo(String emailTo, String chartUrl){
        this.chartUrl = chartUrl;
        this.emailTo = emailTo;
    }

    public void setEmailTo(String emailTo){
        this.emailTo = emailTo;
    }

    public String getEmailTo(){
        return this.emailTo;
    }

    public String getChartUrl() {
        return chartUrl;
    }

    public void setChartUrl(String chartUrl) {
        this.chartUrl = chartUrl;
    }
}
