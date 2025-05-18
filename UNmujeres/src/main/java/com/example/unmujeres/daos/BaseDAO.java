package com.example.unmujeres.daos;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;

public class BaseDAO {

    public Connection getConnection() throws SQLException {

        try {
            Class.forName("com.mysql.jdbc.Driver");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String URL = "jdbc:mysql://localhost:3306/iweb_proy";
        String USER = "root";
        String PASSWORD = "root";
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
