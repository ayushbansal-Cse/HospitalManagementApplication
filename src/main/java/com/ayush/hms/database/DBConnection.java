package com.ayush.hms.database;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private static final String HOST = System.getenv("MYSQLHOST");
    private static final String PORT = System.getenv("MYSQLPORT");
    private static final String DATABASE = System.getenv("MYSQLDATABASE");
    private static final String USER = System.getenv("MYSQLUSER");
    private static final String PASSWORD = System.getenv("MYSQLPASSWORD");

    private static final String URL =
            "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE;

    public static Connection getConnection() {

        System.out.println("===== DATABASE DEBUG =====");
        System.out.println("HOST = " + HOST);
        System.out.println("PORT = " + PORT);
        System.out.println("DATABASE = " + DATABASE);
        System.out.println("USER = " + USER);
        System.out.println("PASSWORD SET = "
                + (PASSWORD != null && !PASSWORD.isEmpty()));
        System.out.println("URL = " + URL);
        System.out.println("==========================");

        try {

            Connection connection =
                    DriverManager.getConnection(URL, USER, PASSWORD);

            System.out.println("Database connection SUCCESS");

            return connection;

        } catch (Exception e) {

            System.out.println("Connection Failed");
            e.printStackTrace();

        }

        return null;
    }
}