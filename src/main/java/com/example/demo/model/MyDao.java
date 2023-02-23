package com.example.demo.model;

import org.springframework.stereotype.Component;
import java.sql.*;
@Component
public class MyDao {
    private Connection connection;

    public MyDao() throws SQLException {
        this.connection = DriverManager.getConnection("jdbc:postgresql://localhost/mydb", "server", "server");
    }
    public ResultSet selectData(String query) throws SQLException {
    Statement statement = connection.createStatement();
    return statement.executeQuery("select "+query);
    }
    public void insertData(String query) throws SQLException {
    Statement statement = connection.createStatement();
    statement.executeQuery("insert "+query);
    }
}
