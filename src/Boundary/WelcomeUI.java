package Boundary;

import Controllers.AccountManager;
import Services.InputService;

public class WelcomeUI {
    public static void welcomeUI() {
        System.out.printf(
                " _  _                 _  _          _   __  __                                                 _     ___            _               \r\n"
                        + "| || | ___  ___ _ __ (_)| |_  __ _ | | |  \\/  | __ _  _ _   __ _  __ _  ___  _ __   ___  _ _  | |_  / __| _  _  ___| |_  ___  _ __  \r\n"
                        + "| __ |/ _ \\(_-<| '_ \\| ||  _|/ _` || | | |\\/| |/ _` || ' \\ / _` |/ _` |/ -_)| '  \\ / -_)| ' \\ |  _| \\__ \\| || |(_-<|  _|/ -_)| '  \\ \r\n"
                        + "|_||_|\\___//__/| .__/|_| \\__|\\__,_||_| |_|  |_|\\__,_||_||_|\\__,_|\\__, |\\___||_|_|_|\\___||_||_| \\__| |___/ \\_, |/__/ \\__|\\___||_|_|_|\r\n"
                        + "               |_|                                               |___/                                    |__/                      \r\n"
                        + "");

        System.out.println("Welcome to Hospital Management System !");
        System.out.println("Please input your choice : ");
        System.out.println("(1) Register as New User ");
        System.out.println("(2) Login as Existing User ");
        System.out.println("(3) Exit System");


        try {
            while (true) {
                int choice = InputService.inputInteger();
                switch (choice) {
                    case 1 -> AccountManager.addUser();
                    case 2 -> LoginUI.loginUI();
                    case 3 -> LogoutUI.logoutUI();
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            }
        } catch (Exception e) {
            welcomeUI();
        }
    }
}