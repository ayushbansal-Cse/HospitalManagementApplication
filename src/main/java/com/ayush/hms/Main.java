package com.ayush.hms;

import com.ayush.hms.ui.HospitalMenu;
import com.ayush.hms.ui.Login;

public class Main {

    public static void main(String[] args) {

        Login login = new Login();

        if (login.login()) {

            HospitalMenu menu = new HospitalMenu();
            menu.start();

        } else {

            System.out.println("Exiting Program...");

        }
    }
}