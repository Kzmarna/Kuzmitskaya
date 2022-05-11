package com.coursework.dao.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Class for connecting to the database
 * @author Kuzmitskaya Maryia
 * @version 1.0
 */
public class DBManager {
    /**
     * URL of our database
     */
    public static String URL = "jdbc:postgresql://localhost:5432/lectures";
    /**
     * Login of our database
     */
    public static String USER = "postgres";
    /**
     * Password of our database
     */
    public static String PASSWORD = "pass";

    /**
     * Method for getting a connection
     * @return connection
     * @throws SQLException
     */
    public static Connection getConnection () throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
