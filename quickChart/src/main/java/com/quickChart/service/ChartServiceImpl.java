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
    public String getChart(int chartId){
        String chartUrl = statsDao.getChart(chartId);
        return chartUrl;
    }

    @Override
    public String createChart(Chart chart){

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
        int chartId = statsDao.addChart(chart, chartUrl);

        if(chartId >= 1) {
            System.out.println("Chart has been created with ID " + chartId);
            statsDao.addLabels(chart.getLabels(), chartId);

            int dataset_id = 0;
            if(chart.getType().compareTo("bar") == 0)
                dataset_id = statsDao.addBarDataset(chartId, dataSet, chart.getType());
            else if(chart.getType().compareTo("line") == 0)
                dataset_id = statsDao.addLineDataset(chartId, dataSet, chart.getType());

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

    public String setBarDataset(DataSet dataSet){
        StringBuilder dataSetJson = new StringBuilder();
        dataSetJson.append("{\n");
        String label = "\tlabel: '"+ dataSet.getLabel() +"',\n";
        String bgrColor = "\tbackgroundColor: '"+ dataSet.getBackground_color() + "',\n";
        String borderColor = "\tborderColor: '"+ dataSet.getBorder_color() + "',\n";
        String borderWidth = "\tborderWidth: "+ dataSet.getBorderWidth() + ",\n";
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
                .append(pointRadius).append(showLine).append("\tdata: [");
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

    public String setDataset(String chartType, DataSet dataSet){
        String dataSetJson = "";

        switch (chartType) {
            case "bar":
                dataSetJson = setBarDataset(dataSet);
                break;
            case "line":
                dataSetJson = setLineDataset(dataSet);
                break;
        }
        return dataSetJson;
    }
}
