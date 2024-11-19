package Boundary;

import Application.ApplicationContext;
import Services.InputService;

/**
 * {@code WelcomeUI} displays the login UI for the User at the start of the program
 */
public class WelcomeUI {
    private PatientRegistrationUI patientRegistrationUI;
    private LogoutUI logoutUI;
    private LoginUI loginUI;
    private ChangePasswordUI changePasswordUI;
    boolean quit = false;

    /**
     * Constructor for {code WelcomeUI}
     * @param patientRegistrationUI
     * @param logoutUI
     * @param loginUI
     */
    public WelcomeUI(PatientRegistrationUI patientRegistrationUI, LogoutUI logoutUI,LoginUI loginUI, ChangePasswordUI changePasswordUI) {
        this.patientRegistrationUI = patientRegistrationUI;
        this.loginUI = loginUI;
        this.logoutUI = logoutUI;
        this.changePasswordUI = changePasswordUI;
    }

    /**
     * Displays the welcome UI for every User that visits the Hospital
     * @param applicationContext
     */
    public void welcomeUI(ApplicationContext applicationContext) {
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
                 System.out.println("(3) Change your password");
                 System.out.println("(4) Exit System");

                int choice = InputService.inputInteger();
                switch (choice) {
                    case 1 -> patientRegistrationUI.registrationUI();
                    case 2 -> loginUI.loginUI(applicationContext);
                    case 3 -> changePasswordUI.changePasswordUI();
                    case 4-> quit = logoutUI.logoutUI();
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            } while (!quit);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            welcomeUI(applicationContext);
        }
    }
}