package com.ayush.hms.database;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private static final String HOST = System.getenv("MYSQLHOST");
    private static final String PORT = System.getenv("MYSQLPORT");

    // Railway MySQL ka actual variable name
    private static final String DATABASE = System.getenv("MYSQL_DATABASE");

    private static final String USER = System.getenv("MYSQLUSER");
    private static final String PASSWORD = System.getenv("MYSQLPASSWORD");

    private static final String URL =
            "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE;

    public static Connection getConnection() {

        try {

            System.out.println("===== DATABASE DEBUG =====");
            System.out.println("MYSQLHOST: " + HOST);
            System.out.println("MYSQLPORT: " + PORT);
            System.out.println("MYSQL_DATABASE: " + DATABASE);
            System.out.println("MYSQLUSER: " + USER);
            System.out.println("MYSQLPASSWORD: " +
                    (PASSWORD != null && !PASSWORD.isEmpty() ? "SET" : "MISSING"));
            System.out.println("MYSQL URL: " + URL);
            System.out.println("==========================");

            if (HOST == null || PORT == null ||
                    DATABASE == null || USER == null || PASSWORD == null) {

                System.out.println("ERROR: MySQL environment variable missing!");
                return null;
            }

            Connection connection =
                    DriverManager.getConnection(URL, USER, PASSWORD);

            System.out.println("DATABASE CONNECTED SUCCESSFULLY");

            return connection;

        } catch (Exception e) {

            System.out.println("Connection Failed");
            e.printStackTrace();

            return null;
        }
    }
}