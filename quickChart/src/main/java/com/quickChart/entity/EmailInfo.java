package com.quickChart.entity;

public class EmailInfo {
    private String emailTo;
    private String chartUrl;
    private String msg;

    public EmailInfo(){}

    public EmailInfo(String emailTo, String chartUrl, String msg){
        this.chartUrl = chartUrl;
        this.emailTo = emailTo;
        this.msg = msg;
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

    public String getMsg(){ return msg;}

    public void setMsg(String msg) {this.msg = msg;}
}
