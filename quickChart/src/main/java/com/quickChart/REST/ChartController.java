package com.quickChart.REST;

import com.quickChart.entity.Chart;
import com.quickChart.entity.DataSet;
import com.quickChart.entity.Download;
import com.quickChart.entity.EmailInfo;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.quickChart.service.ChartService;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.sendgrid.*;

@Controller
@RequestMapping(value = "/chart", produces = {MediaType.APPLICATION_JSON_VALUE}, method = {RequestMethod.GET, RequestMethod.POST,RequestMethod.PUT, RequestMethod.DELETE})
public class ChartController implements WebMvcConfigurer {

    @Autowired
    private ChartService chartService;

    @Autowired
    public ChartController(ChartService chartService) {
        this.chartService = chartService;
    }

    @GetMapping( "/")
    public String showHomePage(Model model) {
        List<Chart> charts = chartService.getCharts(1);
        model.addAttribute(charts);
        return "Home";
    }

    @GetMapping( "/Signup")
    public String showSignupPage() {
        return "Signup";
    }

    /*
    @GetMapping("/Get")
    public String getChart(Model model) {
        String chartUrl = chartService.getChart(2);
        model.addAttribute("get", true);
        model.addAttribute("chart", chartUrl);
        return "Chart";
    }*/

    @GetMapping("/Post")
    public String showPost(Model model){
        model.addAttribute("chart", new Chart());
        return "CreateChart";
    }

    @GetMapping("/Send")
    public String sendLink(Model model){
        model.addAttribute("email", new EmailInfo());
        return "SendChart";
    }

    @GetMapping("/Download")
    public String download(Model model){
        model.addAttribute("download", new Download());
        return "DownloadChart";
    }

    @PostMapping( "/createDataSet")
    public String createDataSet(Model model, @ModelAttribute("chart") Chart chart) throws IOException {
        String template = chartService.getDataSetTemplate(chart.getType());

        if(chart.getType().compareTo("pie") == 0 || chart.getType().compareTo("doughnut")== 0){
            DataSet dataSet = new DataSet();
            dataSet.setBackgroundColors(chartService.generateColors(chart.getLabels().size()));
            chart.setDataSet(dataSet);
        }

        model.addAttribute("chart", chart);
        return template;
    }

    @PostMapping( "/createChart")
    public String createChart(Model model, @ModelAttribute("chart") Chart chart) throws IOException {
        String chartUrl = chartService.createChart(chart);
        model.addAttribute("posted", true);
        model.addAttribute("chart", chart);
        return "Chart";
    }


    @PostMapping("/sendGrid")
    public String sendEmail(Model model, @ModelAttribute("email") EmailInfo emailInfo) throws IOException{
        System.out.println(chartService.sendEmail(emailInfo.getEmailTo(), emailInfo.getChartUrl()));
        model.addAttribute("posted", true);
        return "SendChart";
    }

    @PostMapping("downloadChart")
    public String downloadChart(Model model, @ModelAttribute("download") Download download){
        chartService.downloadImg(download.getName(), download.getChartUrl());
        model.addAttribute("posted", true);
        return "DownloadChart";
    }

}