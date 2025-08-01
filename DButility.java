package com.swabhav.transaction.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DButility {
    private static final String URL = "jdbc:mysql://localhost:3306/banksystem";
    private static final String USER = "root";
    private static final String PASSWORD = "Akash@2803";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL JDBC Driver not found", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void testConnection() {
        try (Connection con = getConnection()) {
            System.out.println("Connection established successfully!!");
        } catch (SQLException e) {
            System.out.println("Failed to connect to database: " + e.getMessage());
        }
    }
}