package com.quickChart.REST;

import com.quickChart.entity.Chart;
import com.quickChart.entity.DataSet;
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

//    @Autowired
//    private SendGrid sendGrid;

//    @Value("${templateId}")
//    private String EMAIL_TEMPLATE_ID;

    @PostMapping("/sendGrid")
    public void sendEmail(Model model, @ModelAttribute("email") EmailInfo emailInfo) throws IOException{
//        Email from = new Email("kiho2735@gmail.com");
//        String subject = "Sending a chart url using SendGrid API";
//        Email to = new Email(emailInfo.getEmailTo());
//        Content content = new Content("text/html", emailInfo.getChartUrl() );
//        Mail mail = new Mail(from, subject, to, content);
//
//        SendGrid sg = new SendGrid(System.getenv("SENDGRID_API_KEY"));
//        Request request = new Request();
//        try{
//            request.setMethod(Method.POST);
//            request.setEndpoint("mail/send");
//            request.setBody(mail.build());
//            Response response = sg.api(request);
//            System.out.println(response.getStatusCode());
//            System.out.println(response.getBody());
//            System.out.println(response.getHeaders());
//        }catch(IOException e){
//            throw e;
//        }
        chartService.sendEmail(emailInfo.getEmailTo());
    }

}