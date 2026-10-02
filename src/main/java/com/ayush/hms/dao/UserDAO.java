package com.ayush.hms.dao;

import com.ayush.hms.database.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {

    public boolean login(String username, String password) {

        String sql = "SELECT * FROM users WHERE username=? AND password=?";

        try {

            Connection con = DBConnection.getConnection();

            if (con == null) {
                System.out.println("Database connection is NULL");
                return false;
            }

            System.out.println("Connected to database");

            System.out.println("Username entered: [" + username + "]");
            System.out.println("Password entered: [" + password + "]");

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, username.trim());
            ps.setString(2, password.trim());

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                System.out.println("User found in database");
                return true;
            } else {
                System.out.println("User NOT found in database");
                return false;
            }

        } catch (Exception e) {

            System.out.println("Login Error:");
            e.printStackTrace();

        }

        return false;
    }
}