package Boundary;

import Services.InputService;

public class WelcomeUI {
    private PatientRegistrationUI patientRegistrationUI;
    private LogoutUI logoutUI;
    private LoginUI loginUI;
    boolean quit = false;

    public WelcomeUI(PatientRegistrationUI patientRegistrationUI, LogoutUI logoutUI,LoginUI loginUI) {
        this.patientRegistrationUI = patientRegistrationUI;
        this.loginUI = loginUI;
        this.logoutUI = logoutUI;
    }

    public void welcomeUI() {
        try {
             do{
                 System.out.print(
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

                int choice = InputService.inputInteger();
                switch (choice) {
                    case 1 -> patientRegistrationUI.registrationUI();
                    case 2 -> loginUI.loginUI();
                    case 3-> quit = logoutUI.logoutUI();
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            } while (!quit);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            welcomeUI();
        }
    }
}