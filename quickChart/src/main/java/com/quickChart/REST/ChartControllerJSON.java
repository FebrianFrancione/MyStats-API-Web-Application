package com.quickChart.REST;

import com.quickChart.service.ChartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/chart/json", produces = {MediaType.APPLICATION_JSON_VALUE}, method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class ChartControllerJSON {
    private ChartService chartService;

    @Autowired
    public ChartControllerJSON(ChartService chartService) {
        this.chartService = chartService;
    }


}
