package com.quickChart.REST;

import com.quickChart.entity.*;
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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.quickChart.service.ChartService;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.sendgrid.*;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping(value = "/chart", produces = {MediaType.APPLICATION_JSON_VALUE}, method = {RequestMethod.GET, RequestMethod.POST,RequestMethod.PUT, RequestMethod.DELETE})
public class ChartController implements WebMvcConfigurer {

    @Autowired
    private ChartService chartService;
    private boolean uploadCSV = false;
    private Chart uploadedChart;
    private boolean formatCSV = true;

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
    public String showSignupPage(Model model) {
        model.addAttribute("user", new User());
        return "Signup";
    }

    @PostMapping( "/createUser")
    public String showSignupPage(Model model, @ModelAttribute("album") User user) {
        chartService.createUser(user);
        return "Home";
    }

    @GetMapping("/viewChart")
    public String viewChart(Model model, @RequestParam int chartId) {
        Chart chart = chartService.getChart(chartId);
        String template = getChartTemplate(chart.getType());
        model.addAttribute(template, true);
        model.addAttribute("chart", chart);
        return "ViewChart";
    }

    @GetMapping("/PostForm")
    public String postForm(Model model){
        model.addAttribute("chart", new Chart());
        return "CreateChart";
    }

    @GetMapping("/Send")
    public String sendLink(Model model, @RequestParam int chartId){
        Chart chart = chartService.getChart(chartId);
        EmailInfo emailInfo = new EmailInfo();
        emailInfo.setChartUrl(chart.getChartUrl());
        model.addAttribute("email", emailInfo);
        return "SendChart";
    }

//    @GetMapping("/Download")
//    public String download(Model model){
//        model.addAttribute("download", new Download());
//        return "DownloadChart";
//    }

    @GetMapping("/UploadFile")
    public String uploadLink(Model model){
        model.addAttribute("chart", new Chart());

        if(!formatCSV){
            model.addAttribute("formatValidate", true);
            formatCSV = true;
        }else{
            model.addAttribute("formatValidate", false);
        }

        return "UploadFile";
    }

    @PostMapping( "/createDataSet")
    public String createDataSet(Model model, @ModelAttribute("chart") Chart chart) throws IOException {
        if(uploadCSV){
            chart = uploadedChart;
        }

        String template = chartService.getDataSetTemplate(chart.getType());

        if(chart.getType().compareTo("pie") == 0 || chart.getType().compareTo("doughnut")== 0){
            if(uploadCSV)
                chart.getDataSet().setBackgroundColors(chartService.generateColors(chart.getLabels().size()));

            else{
                DataSet dataSet = new DataSet();
                dataSet.setBackgroundColors(chartService.generateColors(chart.getLabels().size()));
                chart.setDataSet(dataSet);
            }

        }

        if(uploadCSV){
            uploadCSV = false;
            model.addAttribute("uploadCSV", true);
        }
        model.addAttribute("chart", chart);
        model.addAttribute("createDataset", true);
        return template;
    }

    @PostMapping( "/createChart")
    public String createChart(Model model, @ModelAttribute("chart") Chart chart) throws IOException {
        String chartUrl = chartService.createChart(chart,1);
        model.addAttribute("posted", true);
        model.addAttribute("chart", chart);
        return "Chart";
    }

    @PutMapping( "/updateChart")
    public String updateChart(Model model, @ModelAttribute("chart") Chart chart) throws IOException {
        boolean updated = chartService.updateChart(chart);
        String template = getChartTemplate(chart.getType());
        model.addAttribute(template, true);

        if(updated)
            model.addAttribute("updated", true);

        model.addAttribute("chart", chart);
        return "ViewChart";
    }

    private String getChartTemplate(String type){
        String template = "";

        switch (type) {
            case "bar":
                template = "barTemplate";
                break;
            case "line":
                template = "lineTemplate";
                break;
            case "pie":
            case "doughnut":
                template = "pieTemplate";
                break;
        }
        return template;
    }

    @PostMapping("/upload")
    public RedirectView uploadFile(Model model, @ModelAttribute("chart") Chart chart, @RequestParam("file") MultipartFile file){
        Chart newChart = chartService.uploadCSV(chart, file);
        uploadCSV = true;
        uploadedChart = newChart;

        if(newChart == null){
            formatCSV = false;
            return new RedirectView("/chart/UploadFile");
        }else{
            return new RedirectView("/chart/createDataSet");
        }
    }

    @PostMapping("/sendGrid")
    public String sendEmail(Model model, @ModelAttribute("email") EmailInfo emailInfo) throws IOException{
        System.out.println(chartService.sendEmail(emailInfo.getEmailTo(), emailInfo.getChartUrl(), emailInfo.getMsg()));
        model.addAttribute("posted", true);
        return "SendChart";
    }

    @PostMapping("/downloadChart")
    public String downloadChart(Model model, @RequestParam int chartId){
        Chart chart = chartService.getChart(chartId);
        boolean success = chartService.downloadImg(chart.getChartUrl(), chart.getTitle());
        String template = getChartTemplate(chart.getType());

        if(success){
            model.addAttribute("posted", true);
        }else{
            model.addAttribute("downloadFail", true);
        }

        model.addAttribute(template, true);
        model.addAttribute("chart", chart);
        return "ViewChart";
    }

}