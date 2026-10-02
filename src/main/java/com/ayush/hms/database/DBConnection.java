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
            "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE
                    + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

    public static Connection getConnection() {

        try {

            System.out.println("===== DATABASE DEBUG =====");
            System.out.println("MYSQLHOST: " + HOST);
            System.out.println("MYSQLPORT: " + PORT);
            System.out.println("MYSQLDATABASE: " + DATABASE);
            System.out.println("MYSQLUSER: " + USER);
            System.out.println("MYSQLPASSWORD: " + (PASSWORD != null ? "SET" : "NULL"));
            System.out.println("MYSQL URL: jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE);
            System.out.println("==========================");

            if (HOST == null || PORT == null || DATABASE == null
                    || USER == null || PASSWORD == null) {

                System.out.println("ERROR: MySQL environment variable missing!");
                return null;
            }

            Connection connection =
                    DriverManager.getConnection(URL, USER, PASSWORD);

            System.out.println("DATABASE CONNECTED SUCCESSFULLY!");

            return connection;

        } catch (Exception e) {

            System.out.println("DATABASE CONNECTION FAILED!");
            e.printStackTrace();

        }

        return null;
    }
}