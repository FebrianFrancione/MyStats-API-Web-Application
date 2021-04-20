package com.quickChart.REST;

import com.quickChart.entity.Chart;
import com.quickChart.service.ChartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.util.List;

@Controller
@RequestMapping(value = "/chart/json", produces = {MediaType.APPLICATION_JSON_VALUE}, method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class ChartControllerJSON  implements WebMvcConfigurer {
    private ChartService chartService;

    @Autowired
    public ChartControllerJSON(ChartService chartService) {
        this.chartService = chartService;
    }

    @GetMapping(value = "/")
    @ResponseBody
    public ResponseEntity showHomePage() {
        List<Chart> charts = chartService.getCharts(1);
        if(charts != null) {
            return ResponseEntity.ok(charts);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Charts found!");
        }
    }

    @GetMapping(value = "/viewChart/{chartId}")
    @ResponseBody
    public ResponseEntity viewChart(@PathVariable("chartId") int chartId){
        if(chartId <= 0){
            String message = "Chart id must be provided, the id must be greater than zero";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
        Chart chart = chartService.getChart(chartId);
        if(chart != null){
            return ResponseEntity.ok(chart);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Chart with ID " + chartId + " not found!");
        }
    }

    @PostMapping(value = "/createChart")
    @ResponseBody
    public ResponseEntity createChart(@RequestBody Chart chart)  {
        if(chart == null){
            String message = "The chart entity is empty, please provide the JSON chart structure";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }

        else{
            String chartUrl = chartService.createChart(chart,1);
            return ResponseEntity.ok("Successfully created new chart\nChart URL: " + chartUrl);
        }
    }

    @DeleteMapping(value = "/deleteChart/{chartId}")
    @ResponseBody
    public ResponseEntity deleteChart(@PathVariable("chartId") int chartId) {
        if(chartId <= 0){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("chartId cannot be empty");
        }else{
            boolean deleted = chartService.deleteChart(chartId);
            if(deleted)
                return ResponseEntity.ok("Successfully deleted chart with ID " + chartId);

            else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("chart ID has not been found");
            }
        }
    }

}

