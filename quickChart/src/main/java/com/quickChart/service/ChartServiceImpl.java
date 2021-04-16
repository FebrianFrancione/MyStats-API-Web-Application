package com.quickChart.service;
import com.quickChart.entity.Chart;
import com.quickChart.entity.DataSet;
import com.quickChart.persistence.StatsDAO;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import io.quickchart.QuickChart;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class ChartServiceImpl implements ChartService{

    StatsDAO statsDao = new StatsDAO();
    private boolean update = false;

    @Override
    public Chart getChart(int chartId){
        return statsDao.getChart(chartId);
    }

    @Override
    public List<Chart> getCharts(int userId){
        return statsDao.getAllCharts(userId);
    }

    @Override
    public String createChart(Chart chart, int userId){

        String labels = setLabels(chart.getLabels());
        DataSet dataSet = chart.getDataSet();
        String dataSets = setDataset(chart.getType(), dataSet);

        QuickChart Quickchart = new QuickChart();
        Quickchart.setWidth(chart.getWidth());
        Quickchart.setHeight(chart.getHeight());
        Quickchart.setConfig("{"
                + "    type: '"+ chart.getType() +"',"
                + "    data: {"
                +          labels
                + "        datasets: ["
                +               dataSets
                + "        ]"
                + "    },"
                + "    options: {"
                + "        title: {"
                + "            display: true,"
                + "            text: '"+ chart.getTitle() +"'"
                + "        }"
                + "    }"
                + "}"
        );

        String chartUrl = Quickchart.getShortUrl();
        int chartId = statsDao.addChart(chart, chartUrl, userId);

        if(chartId >= 1) {
            System.out.println("Chart has been created with ID " + chartId);
            statsDao.addLabels(chart.getLabels(), chartId);

            int dataset_id = 0;
            if(chart.getType().compareTo("bar") == 0)
                dataset_id = statsDao.addBarDataset(chartId, dataSet, chart.getType());
            else if(chart.getType().compareTo("line") == 0)
                dataset_id = statsDao.addLineDataset(chartId, dataSet, chart.getType());
            else if(chart.getType().compareTo("pie") == 0 || chart.getType().compareTo("doughnut")== 0){
                dataset_id = statsDao.addPieDataset(chartId, dataSet, chart.getType());
                if(dataset_id >= 1)
                    statsDao.addPieChartColors(dataset_id, dataSet);
            }

            if(dataset_id >= 1){
                System.out.println("Dataset has been created with ID " + dataset_id);
                statsDao.addData(dataSet.getData(), dataset_id);
            }

        }

        chart.setChartUrl(chartUrl);
        return chartUrl;
    }

    @Override
    public boolean updateChart(Chart chart){

        update = true;
        boolean chartUpdated = true;
        String labels = setLabelsMap(chart.getLabelsMap());
        DataSet dataSet = chart.getDataSet();
        String dataSets = setDataset(chart.getType(), dataSet);

        QuickChart Quickchart = new QuickChart();
        Quickchart.setWidth(chart.getWidth());
        Quickchart.setHeight(chart.getHeight());
        Quickchart.setConfig("{"
                + "    type: '"+ chart.getType() +"',"
                + "    data: {"
                +          labels
                + "        datasets: ["
                +               dataSets
                + "        ]"
                + "    },"
                + "    options: {"
                + "        title: {"
                + "            display: true,"
                + "            text: '"+ chart.getTitle() +"'"
                + "        }"
                + "    }"
                + "}"
        );

        String chartUrl = Quickchart.getShortUrl();
        chart.setChartUrl(chartUrl);
        if(!statsDao.updateChart(chart, chartUrl))
            chartUpdated = false;

        statsDao.updateLabels(chart.getLabelsMap());

        if(chart.getType().compareTo("bar") == 0)
            if(!statsDao.updateBarDataset(dataSet))
                chartUpdated = false;

        if(!statsDao.updateData(dataSet.getDataMap()))
            chartUpdated = false;

        chart.setLabels(new ArrayList<>(chart.getLabelsMap().values()));
        update = false;
        return chartUpdated;
    }

    public String setLabels(ArrayList<String> labelsList){
        StringBuilder labels = new StringBuilder();
        labels.append(" labels: [");
        String last = labelsList.get(labelsList.size() - 1);
        for (String label : labelsList) {
            String element;
            if(last.compareTo(label) == 0)
                element = "'"+ label +"'],";
            else
                element = "'"+ label +"', ";

            labels.append(element);
        }
        return labels.toString();
    }

    public String setLabelsMap(Map<Integer, String> labelsMap){
        StringBuilder labels = new StringBuilder();
        labels.append(" labels: [");
        String values = labelsMap.values().stream().collect(Collectors.joining("','", "'", "'"));
        labels.append(values).append("],");
        return labels.toString();
    }

    public String setBarDataset(DataSet dataSet){
        StringBuilder dataSetJson = new StringBuilder();
        dataSetJson.append("{\n");
        String label = "\tlabel: '"+ dataSet.getLabel() +"',\n";
        String bgrColor = "\tbackgroundColor: '"+ dataSet.getBackground_color() + "',\n";
        String borderColor = "\tborderColor: '"+ dataSet.getBorder_color() + "',\n";
        String borderWidth = "\tborderWidth: "+ dataSet.getBorderWidth() + ",\n";
        ArrayList<Integer> data = new ArrayList<>();
        if(update){
            data = new ArrayList<>(dataSet.getDataMap().values());
        }
        else
            data = dataSet.getData();

        dataSetJson.append(label).append(bgrColor).append(borderColor).append(borderWidth).append("\tdata: " + data);
        dataSetJson.append("\n},\n");
        return dataSetJson.toString();
    }

    public String setLineDataset(DataSet dataSet){
        StringBuilder dataSetJson = new StringBuilder();
        dataSetJson.append("{\n");
        String label = "\tlabel: '"+ dataSet.getLabel() +"',\n";
        String bgrColor = "\tbackgroundColor: '"+ dataSet.getBackground_color() + "',\n";
        String borderColor = "\tborderColor: '"+ dataSet.getBorder_color() + "',\n";
        String borderWidth = "\tborderWidth: "+ dataSet.getBorderWidth() + ",\n";
        String fill = "\tfill: "+ dataSet.isFill() + ",\n";
        String pointRadius = "\tpointRadius: "+ dataSet.getPointRadius() + ",\n";
        String showLine = "\tshowLine: "+ dataSet.isShowLine() + ",\n";
        ArrayList<Integer> data = dataSet.getData();
        dataSetJson.append(label).append(bgrColor).append(borderColor).append(borderWidth).append(fill)
                .append(pointRadius).append(showLine).append("\tdata: " + data);

        dataSetJson.append("\n},\n");
        return dataSetJson.toString();
    }

    public String setPieDataset(DataSet dataSet){
        StringBuilder dataSetJson = new StringBuilder();
        dataSetJson.append("{\n");
        String label = "\tlabel: '"+ dataSet.getLabel() +"',\n";
        String borderWidth = "\tborderWidth: "+ dataSet.getBorderWidth() + ",\n";
        String colors = dataSet.getBackgroundColors().stream().collect(Collectors.joining("','", "'", "'"));
        String backgroundColor = "\tbackgroundColor: ["+ colors + "],\n";
        ArrayList<Integer> data = dataSet.getData();
        dataSetJson.append(label).append(borderWidth).append(backgroundColor).append("\tdata: "+ data);
        dataSetJson.append("\n},\n");
        return dataSetJson.toString();
    }

    @Override
    public String getDataSetTemplate(String chartType){
        String template = "";

        switch (chartType) {
            case "bar":
                template = "BarDataset";
                break;
            case "line":
                template = "LineDataset";
                break;
            case "pie":
            case "doughnut":
                template = "PieDataset";
                break;
        }
        return template;
    }

    public String setDataset(String chartType, DataSet dataSet){
        String dataSetJson = "";

        switch (chartType) {
            case "bar":
                dataSetJson = setBarDataset(dataSet);
                break;
            case "line":
                dataSetJson = setLineDataset(dataSet);
                break;
            case "pie":
            case "doughnut":
                dataSetJson = setPieDataset(dataSet);
                break;
        }
        return dataSetJson;
    }

    @Override
    public ArrayList<String> generateColors(int length){
        ArrayList<String> backgroundColors = new ArrayList<>();

        for(int i =0; i<length; i++){
            Random obj = new Random();
            int rand_num = obj.nextInt(0xffffff + 1);
            backgroundColors.add(String.format("#%06x", rand_num));
        }

        return backgroundColors;
    }

    @Override
    public String sendEmail(String email, String url){
        Email from = new Email("ekdms7027@naver.com");
        String subject = "From Chart Web Service using SendGrid API";
        Email to = new Email(email);
        Content content = new Content("text/plain", url);
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid("");
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
//            System.out.println(response.getStatusCode());
//            System.out.println(response.getBody());
//            System.out.println(response.getHeaders());

        } catch (IOException ex) {
            ex.printStackTrace();
            return "Error in send grid.";
        }
        return "mail has been sent check your inbox.";
    }

    @Override
    public void downloadImg(String name, String url) {
        try(InputStream in = new URL(url).openStream()){
            Files.copy(in, Paths.get("C:\\Users\\kiho2\\Desktop\\Concordia\\" + name +".jpg"));
        }catch(IOException e){
            e.printStackTrace();
        }

    }

    @Override
    public String uploadChart(Chart chart, MultipartFile file){
        // List<List<String>> list = new ArrayList<List<String>>();
        BufferedReader br = null;
        try{
            br = new BufferedReader(new InputStreamReader(file.getInputStream()));
            String line = "";

            ArrayList<String> labels = new ArrayList<>();
            ArrayList<Integer> labelOrder = new ArrayList<>(); // [0,2,4]
            ArrayList<Integer> data = new ArrayList<Integer>();
            ArrayList<Integer> dataOrder = new ArrayList<>(); // [1,3,5]

            boolean flag = false;
            while((line=br.readLine()) != null) {
                // System.out.println(line);
                String[] token = line.split(",");
                if(flag){
                    for(int i = 0; i < labelOrder.size() ; i++){
                        labels.add(token[labelOrder.get(i)]);
                    }
                    for(int i = 0 ; i < dataOrder.size() ; i++){
                        int value = Integer.parseInt(token[dataOrder.get(i)]);
                        data.add(value);
                    }
                }else{
                    for(int i = 0; i < token.length; i++){
                        if(token[i].equalsIgnoreCase("label")){
                            labelOrder.add(i);
                        }else if(token[i].equalsIgnoreCase("value")){
                            dataOrder.add(i);
                        }
                    }
                    flag = true;
                }

                //List<String> tempList = new ArrayList<String>(Arrays.asList(token));
                //list.add(tempList);

            }
            chart.setLabels(labels);
            chart.getDataSet().setData(data);

            return createChart(chart,1);

        }catch(IOException e){
            e.printStackTrace();
            return "Error in uploading";
        }finally {
            try {
                if(br != null)
                    br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
