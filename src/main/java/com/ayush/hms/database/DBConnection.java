package com.ayush.hms.database;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private static final String HOST = System.getenv("MYSQLHOST");
    private static final String PORT = System.getenv("MYSQLPORT");

    // Railway MySQL database
    private static final String DATABASE = "railway";

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
            System.out.println("DATABASE: " + DATABASE);
            System.out.println("MYSQLUSER: " + USER);

            if (PASSWORD != null && !PASSWORD.isEmpty()) {
                System.out.println("MYSQLPASSWORD: SET");
            } else {
                System.out.println("MYSQLPASSWORD: MISSING");
            }

            System.out.println("MYSQL URL: " + URL);
            System.out.println("==========================");

            if (HOST == null || HOST.isEmpty()) {
                System.out.println("ERROR: MYSQLHOST missing!");
                return null;
            }

            if (PORT == null || PORT.isEmpty()) {
                System.out.println("ERROR: MYSQLPORT missing!");
                return null;
            }

            if (USER == null || USER.isEmpty()) {
                System.out.println("ERROR: MYSQLUSER missing!");
                return null;
            }

            if (PASSWORD == null || PASSWORD.isEmpty()) {
                System.out.println("ERROR: MYSQLPASSWORD missing!");
                return null;
            }

            Connection connection = DriverManager.getConnection(
                    URL,
                    USER,
                    PASSWORD
            );

            System.out.println("DATABASE CONNECTED SUCCESSFULLY!");

            return connection;

        } catch (Exception e) {

            System.out.println("Connection Failed");
            e.printStackTrace();

            return null;
        }
    }
}