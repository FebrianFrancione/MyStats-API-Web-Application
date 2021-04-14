package com.quickChart.REST;

import com.quickChart.entity.Chart;
import com.quickChart.entity.DataSet;
import org.springframework.beans.factory.annotation.Autowired;
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

@Controller
@RequestMapping(value = "/chart", produces = {MediaType.APPLICATION_JSON_VALUE}, method = {RequestMethod.GET, RequestMethod.POST,RequestMethod.PUT, RequestMethod.DELETE})
public class ChartController implements WebMvcConfigurer {

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

    @GetMapping("/viewChart")
    public String viewChart(Model model, @RequestParam int chartId) {
        Chart chart = chartService.getChart(chartId);
        String template = getChartTemplate(chart.getType());
        model.addAttribute(template, true);
        model.addAttribute("chart", chart);
        return "ViewChart";
    }

    @GetMapping("/Post")
    public String showPost(Model model){
        model.addAttribute("chart", new Chart());
        return "CreateChart";
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

}