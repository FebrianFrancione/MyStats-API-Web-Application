package com.quickChart.persistence;

import com.quickChart.entity.Chart;
import com.quickChart.entity.DataSet;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StatsDAO {
    private PreparedStatement statement = null;
    private JDBConfig jdbc = new JDBConfig();

    public int addChart(Chart chart, String url) {
        int newID = 0;
        java.util.Date utilDate = new java.util.Date();
        Timestamp time_stamp = new Timestamp(utilDate.getTime());
        String sql = "insert into charts (chart_title, chart_url, type, width, height) values (?,?,?,?,?)";
        statement = jdbc.prepareStatementWithKeys(sql);
        try {
            statement.setString(1, chart.getTitle());
            statement.setString(2, url);
            statement.setString(3, chart.getType());
            statement.setInt(4, chart.getWidth());
            statement.setInt(5, chart.getHeight());
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

    public int addDataset(int chartId, DataSet dataSet, String chart_type) {
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
        }catch(SQLException e){
            e.printStackTrace();
        }finally {
            jdbc.close();
        }
        return newID;
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
}
