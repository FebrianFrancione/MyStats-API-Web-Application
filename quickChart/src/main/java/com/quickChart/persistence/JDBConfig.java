package com.quickChart.persistence;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

public class JDBConfig {
    private Connection con = null;
    private PreparedStatement ps = null;
    private String dbURL;
    private String username;
    private String password;
    private String filepath;

    public JDBConfig() {
        try {
            filepath = System.getProperty("user.dir") + "\\quickChart\\src\\main\\java\\com\\quickChart\\persistence\\config.json";
            JsonReader jr = Json.createReader(new FileInputStream(filepath));
            JsonObject jo = jr.readObject();
            dbURL = jo.getString("dbURL");
            username = jo.getString("username");
            password = jo.getString("password");
        } catch (FileNotFoundException e) {
            System.out.println("Configuration file not found");
            setDefaultConfig();
        }
    }

    static{
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Connection Driver error");
        }

    }

    public Connection getConnection(){
        try {
            con = DriverManager.getConnection(dbURL, username, password);
        }catch(SQLException e){
            e.printStackTrace();
            System.out.println("Connection error");
        }
        return con;
    }

    public PreparedStatement prepareStatement(String sql){
        Connection con = getConnection();
        try {
            ps = con.prepareStatement(sql);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return ps;
    }

    public PreparedStatement prepareStatementWithKeys(String sql){
        Connection con = getConnection();
        try {
            ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return ps;
    }

    public void close(){
        if(ps!=null){
            try{
                ps.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
        if(con!=null){
            try {
                con.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
    }

    public void close(ResultSet rs){
        if(rs!=null){
            try{
                rs.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
        close();
    }

    private void setDefaultConfig() {
        dbURL = "jdbc:mysql://localhost:3306/stats_db?allowPublicKeyRetrieval=true&useSSL=false";
        username = "root";
        password = "1234";
    }
}
