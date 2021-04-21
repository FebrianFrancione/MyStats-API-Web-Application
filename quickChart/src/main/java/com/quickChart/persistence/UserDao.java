package com.quickChart.persistence;

import com.quickChart.entity.User;
import org.springframework.security.core.GrantedAuthority;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class UserDao {
    private PreparedStatement statement = null;
    private JDBConfig jdbc = new JDBConfig();

    public void createUser(User user) {
        String sql = "insert into users (first_name, last_name, password) values (?,?,?)";
        JDBConfig jdbc = new JDBConfig();
        statement = jdbc.prepareStatement(sql);
        try {
            statement.setString(1,user.getFirst_name());
            statement.setString(2, user.getLast_name());
            statement.setString(3, user.getPassword());
            statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();}
        finally {
            jdbc.close();
        }

    }

//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return Collections.emptyList();
//    }

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

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        User user = null;
        int user_id;
        String first_name;
        String last_name;
        String password;
        String token;
        JDBConfig jdbc = new JDBConfig();
        String sql = "select * from users";
        statement = jdbc.prepareStatement(sql);
        ResultSet resultSet = null;

        try {
            resultSet = statement.executeQuery();
            while(resultSet.next()) {
                user_id = resultSet.getInt("user_id");
                first_name = resultSet.getString("first_name");
                last_name = resultSet.getString("last_name");
                password = resultSet.getString("password");
                token = resultSet.getString("token");

                user = new User(user_id,first_name,last_name,password, token);
                users.add(user);
            }

        }catch(SQLException e){
            e.printStackTrace();
        }finally {
            jdbc.close();
        }
        return users;
    }

}