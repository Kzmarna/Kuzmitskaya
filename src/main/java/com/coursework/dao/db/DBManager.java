package com.coursework.dao.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {
    public static String URL = "jdbc:postgresql://localhost:5432/lectures";
    public static String USER = "postgres";
    public static String PASSWORD = "pass";

    public static Connection getConnection () throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
