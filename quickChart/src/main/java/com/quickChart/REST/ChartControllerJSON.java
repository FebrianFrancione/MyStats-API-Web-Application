package com.quickChart.REST;

import com.quickChart.entity.Chart;
import com.quickChart.entity.EmailInfo;
import com.quickChart.entity.User;
import com.quickChart.persistence.UserDao;
import com.quickChart.service.ChartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
        List<Chart> charts = chartService.getCharts(getUserId());
        if(charts != null) {
            return ResponseEntity.ok(charts);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Charts found!");
        }
    }

//    @GetMapping( "/Landing")
//    @ResponseBody
//    public ResponseEntity showLandingPage() {
//        return ResponseEntity.ok("Landing page");
//    }
//
//    @GetMapping( "/Signup")
//    public ResponseEntity showSignupPage() {
//
//        return ResponseEntity.ok("Signup page");
//    }

    @PostMapping( "/createUser")
    @ResponseBody
    public ResponseEntity showSignupPage(@RequestPart("first_name") String first_name,@RequestPart("last_name") String last_name,@RequestPart("password") String password) {
        User user = new User();
        user.setFirst_name(first_name);
        user.setLast_name(last_name);
        user.setPassword(password);
        chartService.createUser(user);
        return ResponseEntity.ok("User created");
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
            boolean created = chartService.createChart(chart,getUserId());
            if(created)
                return ResponseEntity.ok("Successfully created new chart\nChart URL: " + chart.getChartUrl());
            else{
                String message = "Failed to create chart or dataset";
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
            }

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

    @PutMapping( "/updateChart")
    @ResponseBody
    public ResponseEntity updateChart(@RequestBody Chart chart) {
        if(chart == null){
            String message = "The chart entity is empty, please provide the JSON chart structure";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
        else{
            boolean updated = chartService.updateChart(chart);
            if(updated)
                return ResponseEntity.ok("Successfully updated your chart\nChart URL: " + chart.getChartUrl());
            else{
                String message = "Failed to update chart or dataset";
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
            }
        }
    }

    @PostMapping("/sendGrid")
    @ResponseBody
    public ResponseEntity sendEmail(@RequestBody EmailInfo emailInfo){
        if(emailInfo == null){
            String message = "The email info entity is empty, please provide the JSON email data structure";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
        else{
            String sent = chartService.sendEmail(emailInfo.getEmailTo(), emailInfo.getChartUrl(), emailInfo.getMsg());
            return ResponseEntity.ok(sent);
        }
    }

    private int getUserId(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDao userDao = new UserDao();
        User user = userDao.getUserByFirstName(auth.getName());
        return user.getUser_id();
    }

}

