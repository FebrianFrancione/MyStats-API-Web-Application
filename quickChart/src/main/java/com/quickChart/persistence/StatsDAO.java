package com.quickChart.persistence;

import com.quickChart.entity.Chart;
import com.quickChart.entity.DataSet;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatsDAO {
    private PreparedStatement statement = null;
    private JDBConfig jdbc = new JDBConfig();

    public int addChart(Chart chart, String url, int userId) {
        int newID = 0;
        String sql = "insert into charts (chart_title, chart_url, type, width, height, user_id) values (?,?,?,?,?,?)";
        statement = jdbc.prepareStatementWithKeys(sql);
        try {
            statement.setString(1, chart.getTitle());
            statement.setString(2, url);
            statement.setString(3, chart.getType());
            statement.setInt(4, chart.getWidth());
            statement.setInt(5, chart.getHeight());
            statement.setInt(6, userId);
            int insertedRow = statement.executeUpdate();

            if (insertedRow == 0) {
                throw new SQLException("Insert failed");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    newID = generatedKeys.getInt(1);
                }
                else {
                    throw new SQLException("Creating a chart failed, no ID obtained.");
                }
            }

        }catch(SQLException e){
            e.printStackTrace();
        }finally {
            jdbc.close();
        }
        return newID;
    }

    public boolean updateChart(Chart chart, String url){
        boolean isUpdated = false;
        String sql = "update charts set chart_title=?, chart_url=?, width=?, height=? where chart_id=?";
        statement = jdbc.prepareStatement(sql);
        try {
            statement.setString(1, chart.getTitle());
            statement.setString(2, url);
            statement.setInt(3, chart.getWidth());
            statement.setInt(4, chart.getHeight());
            statement.setInt(5, chart.getChartId());
            statement.executeUpdate();
            int updatedRow = statement.executeUpdate();
            if(updatedRow > 0)
                isUpdated = true;
        }catch(SQLException e){
            e.printStackTrace();
        }finally {
            jdbc.close();
        }

        return isUpdated;
    }

    public void updateLabels(Map<Integer, String> labelsMap){

        String sql = "update labels set title=? where label_id=?";
        statement = jdbc.prepareStatement(sql);
        try {
            for (Map.Entry<Integer, String> label : labelsMap.entrySet()) {
                statement.setString(1, label.getValue());
                statement.setInt(2, label.getKey());
                statement.executeUpdate();
            }

        }catch(SQLException e){
            e.printStackTrace();
        }finally {
            jdbc.close();
        }

    }
    public List<Chart> getAllCharts(int userId){
        List<Chart> charts = new ArrayList<>();
        int chartId, width, height;
        String title, type, chartUrl;
        String sql = "select * from charts where user_id=?";
        statement = jdbc.prepareStatement(sql);
        ResultSet rs = null;

        try {
            statement.setInt(1, userId);
            rs = statement.executeQuery();
            while(rs.next()){
                chartId = rs.getInt("chart_id");
                title = rs.getString("chart_title");
                chartUrl = rs.getString("chart_url");
                type = rs.getString("type");
                width = rs.getInt("width");
                height = rs.getInt("height");
                charts.add(new Chart(chartId, title, chartUrl, width, height, type));
            }

        }catch(SQLException e){
            e.printStackTrace();
        }finally {
            jdbc.close();
        }
        return charts;
    }

    public Chart getChart(int chartId){
        Chart chart = null;
        String sql = "SELECT charts.*, datasets.* FROM charts JOIN datasets ON charts.chart_id=datasets.chart_id WHERE charts.chart_id=?";
        statement = jdbc.prepareStatement(sql);
        ResultSet rs = null;

        try {
            statement.setInt(1, chartId);

            rs = statement.executeQuery();
            while(rs.next()){
                String title = rs.getString("chart_title");
                String chartUrl = rs.getString("chart_url");
                String type = rs.getString("type");
                int width = rs.getInt("width");
                int height = rs.getInt("height");
                DataSet dataSet = getDataset(rs, type);
                //dataSet.setData(getData(dataSet.getDatasetId()));
                chart = new Chart(chartId, title, chartUrl, width, height, type, getLabels(chartId), dataSet);
            }

        }catch(SQLException e){
            e.printStackTrace();
        }finally {
            jdbc.close();
        }
        return chart;
    }

    public boolean addLabels(ArrayList<String> labels, int chartId) {
        boolean success = false;
        String sql = "insert into labels (title, chart_id) values (?,?)";
        statement = jdbc.prepareStatement(sql);
        try {
            for (String label : labels) {
                System.out.println("adding label " + label);
                statement.setString(1, label);
                statement.setInt(2, chartId);
                statement.executeUpdate();
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally {
            jdbc.close();
            success = true;
        }
        return success;
    }

    public Map<Integer, String> getLabels(int chartId){
        Map<Integer, String> labels = new HashMap<Integer, String>();
        String sql = "select * from labels where chart_id=?";
        statement = jdbc.prepareStatement(sql);
        ResultSet rs = null;

        try {
            statement.setInt(1, chartId);
            rs = statement.executeQuery();
            while(rs.next()){
                int label_id = rs.getInt("label_id");
                String label = rs.getString("title");
                labels.put(label_id, label);
            }

        }catch(SQLException e){
            e.printStackTrace();
        }finally {
            jdbc.close();
        }
        return labels;
    }

    public int addBarDataset(int chartId, DataSet dataSet, String chart_type) {
        int newID = 0;
        String sql = "insert into datasets (chart_id, label, chart_type, border_color, background_color, border_width) values (?,?,?,?,?,?)";
        statement = jdbc.prepareStatementWithKeys(sql);
        try {
            statement.setInt(1, chartId);
            statement.setString(2, dataSet.getLabel());
            statement.setString(3, chart_type);
            statement.setString(4, dataSet.getBorder_color());
            statement.setString(5, dataSet.getBackground_color());
            statement.setInt(6, dataSet.getBorderWidth());
            newID = insertDataset();
        }catch(SQLException e){
            e.printStackTrace();
        }finally {
            jdbc.close();
        }
        return newID;
    }

    public int addLineDataset(int chartId, DataSet dataSet, String chart_type) {
        int newID = 0;
        String sql = "insert into datasets (chart_id, label, chart_type, border_color, background_color, border_width, fill, pointRadius, showLine) values (?,?,?,?,?,?,?,?,?)";
        statement = jdbc.prepareStatementWithKeys(sql);
        try {
            statement.setInt(1, chartId);
            statement.setString(2, dataSet.getLabel());
            statement.setString(3, chart_type);
            statement.setString(4, dataSet.getBorder_color());
            statement.setString(5, dataSet.getBackground_color());
            statement.setInt(6, dataSet.getBorderWidth());
            statement.setString(7, String.valueOf(dataSet.isFill()));
            statement.setInt(8, dataSet.getPointRadius());
            statement.setString(9, String.valueOf(dataSet.isShowLine()));
            newID = insertDataset();
        }catch(SQLException e){
            e.printStackTrace();
        }finally {
            jdbc.close();
        }
        return newID;
    }

    public int addPieDataset(int chartId, DataSet dataSet, String chart_type) {
        int newID = 0;
        String sql = "insert into datasets (chart_id, label, chart_type, border_width) values (?,?,?,?)";
        statement = jdbc.prepareStatementWithKeys(sql);
        try {
            statement.setInt(1, chartId);
            statement.setString(2, dataSet.getLabel());
            statement.setString(3, chart_type);
            statement.setInt(4, dataSet.getBorderWidth());
            newID = insertDataset();
        }catch(SQLException e){
            e.printStackTrace();
        }finally {
            jdbc.close();
        }
        return newID;
    }

    public boolean addPieChartColors(int datasetId, DataSet dataSet) {
        boolean success = false;
        String sql = "insert into background_colors (color, dataset_id) values (?,?)";
        statement = jdbc.prepareStatement(sql);
        ArrayList<String> backgroundColors = dataSet.getBackgroundColors();
        try {
            for (String color : backgroundColors) {
                statement.setString(1, color);
                statement.setInt(2, datasetId);
                statement.executeUpdate();
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally {
            jdbc.close();
            success = true;
        }
        return success;
    }

    public DataSet getDataset(ResultSet rs, String chart_type) {
        DataSet dataSet = null;
        String label,border_color,background_color,fill, showLine;
        int border_width, pointRadius, datasetId;

        try {
            datasetId = rs.getInt("dataset_id");
            label = rs.getString("label");
            border_width = (rs.getInt("border_width")) == 0 ? 2 : rs.getInt("border_width");

            switch (chart_type) {
                case "bar":
                    border_color = rs.getString("border_color");
                    background_color = rs.getString("background_color");
                    Map<Integer, Integer> dataMap = getData(datasetId);
                    dataSet = new DataSet(datasetId, label, border_color, background_color, border_width, dataMap);
                    break;
                case "line":
                    border_color = rs.getString("border_color");
                    background_color = rs.getString("background_color");
                    fill = (rs.getString("fill")) == null ? "true" : rs.getString("fill");
                    pointRadius = (rs.getInt("pointRadius")) == 0 ? 3 : rs.getInt("pointRadius");
                    showLine = (rs.getString("showLine")) == null ? "true" : rs.getString("showLine");
                    dataSet = new DataSet(datasetId, label, border_color, background_color, border_width, Boolean.parseBoolean(fill), pointRadius, Boolean.parseBoolean(showLine));
                    break;
                case "pie":
                case "doughnut":
                    //and array of colors
                    ArrayList<String> backgroundColors = new ArrayList<>();
                    dataSet = new DataSet(datasetId, label, border_width, backgroundColors);
                    break;
            }

        }catch(SQLException e){
            e.printStackTrace();
        }

        return dataSet;
    }
    public boolean addData(ArrayList<Integer> data, int datasetId) {
        boolean success = false;
        String sql = "insert into dataset_data (value, dataset_id) values (?,?)";
        statement = jdbc.prepareStatement(sql);
        try {
            for (int value : data) {
                System.out.println("Adding value " + value);
                statement.setInt(1, value);
                statement.setInt(2, datasetId);
                statement.executeUpdate();
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally {
            jdbc.close();
            success = true;
        }
        return success;
    }

    public Map<Integer, Integer> getData(int datasetId) {
        Map<Integer, Integer> data = new HashMap<>();
        String sql = "select * from dataset_data where dataset_id=?";
        statement = jdbc.prepareStatement(sql);
        ResultSet rs = null;

        try {
            statement.setInt(1, datasetId);
            rs = statement.executeQuery();
            while(rs.next()){
                int value_id = rs.getInt("value_id");
                int value = rs.getInt("value");
                data.put(value_id, value);
            }

        }catch(SQLException e){
            e.printStackTrace();
        }finally {
            jdbc.close();
        }

        return data;
    }

    public boolean updateData(Map<Integer, Integer> dataMap) {
        boolean success = false;
        String sql = "update dataset_data set value=? where value_id=?";
        statement = jdbc.prepareStatement(sql);
        try {
            for (Map.Entry<Integer, Integer> data : dataMap.entrySet()) {
                statement.setInt(1, data.getValue());
                statement.setInt(2, data.getKey());
                statement.executeUpdate();
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally {
            jdbc.close();
            success = true;
        }
        return success;
    }

    public boolean updateBarDataset(DataSet dataSet) {
        boolean isUpdated = false;
        String sql = "update datasets set label=?, border_color=?, background_color=?, border_width=? where dataset_id=?";
        statement = jdbc.prepareStatement(sql);
        try {
            statement.setString(1, dataSet.getLabel());
            statement.setString(2, dataSet.getBorder_color());
            statement.setString(3, dataSet.getBackground_color());
            statement.setInt(4, dataSet.getBorderWidth());
            statement.setInt(5, dataSet.getDatasetId());
            int updatedRow = statement.executeUpdate();
            if(updatedRow > 0)
                isUpdated = true;
        }catch(SQLException e){
            e.printStackTrace();
        }finally {
            jdbc.close();
        }
        return isUpdated;
    }

    /*
    ** Helper to execute insert statement for all datasets
     */
    public int insertDataset() throws SQLException {
        int newID = 0;
        int insertedRow = statement.executeUpdate();

        if (insertedRow == 0) {
            throw new SQLException("Insert failed");
        }

        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                newID = generatedKeys.getInt(1);
            }
            else {
                throw new SQLException("Creating a dataset failed, no ID obtained.");
            }
        }
        return newID;
    }
}
