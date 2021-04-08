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
    public String showHomePage() {
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


    @PostMapping( "/createDataSet")
    public String createDataSet(Model model, @ModelAttribute("chart") Chart chart) throws IOException {
        String template = chartService.getDataSetTemplate(chart.getType());
        model.addAttribute("chart", chart);
        return template;
    }

    @PostMapping( "/createChart")
    public String createChart(Model model, @ModelAttribute("chart") Chart chart) throws IOException {
        String chartUrl = chartService.createChart(chart);
        model.addAttribute("posted", true);
        model.addAttribute("chart", chartUrl);
        return "Chart";
    }


}