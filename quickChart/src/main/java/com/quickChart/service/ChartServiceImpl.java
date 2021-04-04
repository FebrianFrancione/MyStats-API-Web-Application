package com.quickChart.service;
import com.quickChart.persistence.StatsDAO;
import org.springframework.stereotype.Service;
import io.quickchart.QuickChart;

import java.util.ArrayList;

@Service
public class ChartServiceImpl implements ChartService{

    StatsDAO statsDao = new StatsDAO();

    @Override
    public String getChart(){

        //For now return a static url
        return "https://quickchart.io/chart/render/zf-47776625-e5bd-44c0-a2af-9a9ab91efdc9";
    }

    @Override
    public String createChart(String title, int width, int height, String type){

        //Adding fake data to DB for now
        //We will have to get these values from html page through REST controller

        ArrayList<String> labels = new ArrayList<>();
        labels.add("label1");
        labels.add("label2");
        labels.add("label3");
        labels.add("label4");

        ArrayList<Integer> data = new ArrayList<>();
        data.add(9);
        data.add(88);
        data.add(31);
        data.add(400);

        String label = "Dataset label";
        String border_color = "#4E79A7";
        String background_color = "#4E79A733";

        //For now I create a random chart
        QuickChart chart = new QuickChart();
        chart.setWidth(width);
        chart.setHeight(height);
        chart.setConfig("{"
                + "    type: '"+ type +"',"
                + "    data: {"
                + "        labels: ['label1', 'label2', 'Label3', 'label4'],"
                + "        datasets: [{"
                + "            label: 'Labels',"
                + "            data: [9, 88, 31, 475]"
                + "        }]"
                + "    },"
                + "    options: {"
                + "        title: {"
                + "            display: true,"
                + "            text: '"+ title +"'"
                + "        }"
                + "    }"
                + "}"
        );

        String chartUrl = chart.getShortUrl();
        int chartId = statsDao.addChart(title, chartUrl, type, width, height);

        if(chartId >= 1) {
            System.out.println("Chart has been created with ID " + chartId);
            statsDao.addLabels(labels, chartId);
            int dataset_id = statsDao.addDataset(chartId, label, type, border_color, background_color);
            if(dataset_id >= 1){
                System.out.println("Dataset has been created with ID " + dataset_id);
                statsDao.addData(data, dataset_id);
            }

        }

        return chartUrl;
    }
}
