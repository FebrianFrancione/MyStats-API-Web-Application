package com.quickChart.persistence;

import com.quickChart.entity.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {
    private PreparedStatement statement = null;
    private JDBConfig jdbc = new JDBConfig();

    public void createUser(User user) {
        String sql = "insert into stats_db (first_name, last_name, password) values (?,?,?)";
        JDBConfig jdbc = new JDBConfig();
        statement = jdbc.prepareStatement(sql);
        try {
            statement.setString(1,user.getFirst_name());
            statement.setString(2, user.getLast_name());
            statement.setString(3, user.getPassword());
            statement.executeQuery();
        }catch (SQLException e){
            e.printStackTrace();}
        finally {
            jdbc.close();
        }

    }

    public User getUserByFirstName(String first_name) {
        User user = null;
        int user_id;
        String last_name;
        String password;
        String token;
        JDBConfig jdbc = new JDBConfig();
        String sql = "select * from stats_db.users where first_name=?";
        statement = jdbc.prepareStatement(sql);
        ResultSet resultSet = null;

        try {
            statement.setString(1, first_name);
            resultSet = statement.executeQuery();
            while(resultSet.next()) {
                user_id = resultSet.getInt("user_id");
                first_name = resultSet.getString("first_name");
                last_name = resultSet.getString("last_name");
                password = resultSet.getString("password");
                token = resultSet.getString("token");

                user = new User(user_id,first_name,last_name,password, token);
            }

        }catch(SQLException e){
            e.printStackTrace();
        }finally {
            jdbc.close();
        }
        return user;
    }
}