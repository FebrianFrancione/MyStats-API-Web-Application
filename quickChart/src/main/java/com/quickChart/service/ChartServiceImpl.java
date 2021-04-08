package com.quickChart.service;
import com.quickChart.entity.Chart;
import com.quickChart.entity.DataSet;
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
        DataSet dataSet = chart.getDataSet();
        String dataSets = setDataset(dataSet);

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
        int chartId = statsDao.addChart(chart, chartUrl);

        if(chartId >= 1) {
            System.out.println("Chart has been created with ID " + chartId);
            statsDao.addLabels(chart.getLabels(), chartId);
            int dataset_id = statsDao.addDataset(chartId, dataSet, chart.getType());
            if(dataset_id >= 1){
                System.out.println("Dataset has been created with ID " + dataset_id);
                statsDao.addData(dataSet.getData(), dataset_id);
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

    public String setDataset(DataSet dataSet){
        StringBuilder dataSetJson = new StringBuilder();
        dataSetJson.append("{\n");
        String label = "\tlabel: '"+ dataSet.getLabel() +"',\n";
        String bgrColor = "\tbackgroundColor: '"+ dataSet.getBackground_color() + "',\n";
        String borderColor = "\tborderColor: '"+ dataSet.getBorder_color() + "',\n";
        String borderWidth = "\tborderWidth: '"+ dataSet.getBorderWidth() + "',\n";
        ArrayList<Integer> data = dataSet.getData();

        dataSetJson.append(label).append(bgrColor).append(borderColor).append(borderWidth).append("\tdata: [");
        int last = data.get(data.size() - 1);

        for (int value : data) {
            String element;
            if(last == value)
                element = value +"]";
            else
                element = value +", ";

            dataSetJson.append(element);
        }

        dataSetJson.append("\n},\n");
        return dataSetJson.toString();
    }

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
                template = "PieDataset";
                break;
        }
        return template;
    }
}
