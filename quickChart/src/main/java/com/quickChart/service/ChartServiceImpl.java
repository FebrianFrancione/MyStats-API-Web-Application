package com.quickChart.service;
import com.quickChart.entity.Chart;
import com.quickChart.persistence.StatsDAO;
import org.json.JSONArray;
import org.json.JSONObject;
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
    public String createChart(Chart chart){

        String labels = setLabels(chart.getLabels());
        String label = "Label";
        String border_color = "#F28E2B";
        String background_color = "#F28E2B33";
        String dataSets = setDataset(chart.getDataSet(), label, border_color, background_color);

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
        int chartId = statsDao.addChart(chart.getTitle(), chartUrl, chart.getType(), chart.getWidth(), chart.getHeight());

        if(chartId >= 1) {
            System.out.println("Chart has been created with ID " + chartId);
            statsDao.addLabels(chart.getLabels(), chartId);
            int dataset_id = statsDao.addDataset(chartId, label, chart.getType(), border_color, background_color);
            if(dataset_id >= 1){
                System.out.println("Dataset has been created with ID " + dataset_id);
                statsDao.addData(chart.getDataSet(), dataset_id);
            }

        }

        return chartUrl;
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

    public String setDataset(ArrayList<Integer> data, String lbl, String border_color, String background_color){
        StringBuilder dataSet = new StringBuilder();
        dataSet.append("{\n");
        String label = "\tlabel: '"+ lbl +"',\n";
        String bgrColor = "\tbackgroundColor: '"+ background_color + "',\n";
        String borderColor = "\tborderColor: '"+ border_color + "',\n";

        dataSet.append(label).append(bgrColor).append(borderColor).append("\tdata: [");
        int last = data.get(data.size() - 1);

        for (int value : data) {
            String element;
            if(last == value)
                element = value +"]";
            else
                element = value +", ";

            dataSet.append(element);
        }

        dataSet.append("\n},\n");
        return dataSet.toString();
    }
}
