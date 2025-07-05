package com.musicapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static Connection connection;

    public static void connect() throws SQLException {
        String url = "jdbc:your_database_url";
        String user = "your_database_user";
        String password = "your_database_password";
        connection = DriverManager.getConnection(url, user, password);
    }

    public static void close() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    public static Connection getConnection() {
        return connection;
    }
}
