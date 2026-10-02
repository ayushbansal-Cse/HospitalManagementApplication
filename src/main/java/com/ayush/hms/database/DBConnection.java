package com.ayush.hms.database;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private static final String URL =
            "jdbc:mysql://localhost:3306/hospital_db";

    private static final String USER = "";

    private static final String PASSWORD = "";

    public static Connection getConnection() {

        try {

            Connection connection =
                    DriverManager.getConnection(URL, USER, PASSWORD);


            return connection;

        }

        catch (Exception e) {

            System.out.println("Connection Failed");

            e.printStackTrace();

        }

        return null;

    }

}