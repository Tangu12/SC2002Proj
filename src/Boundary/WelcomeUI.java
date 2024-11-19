package Boundary;

import Application.ApplicationContext;
import Services.InputService;

/**
 * {@code WelcomeUI} displays the login UI for the User at the start of the program.
 * It provides options for the user to register, login, or exit the system.
 */
public class WelcomeUI {
    private PatientRegistrationUI patientRegistrationUI;
    private LogoutUI logoutUI;
    private LoginUI loginUI;
    boolean quit = false;

    /**
     * Constructor for {@code WelcomeUI}.
     * Initializes the {@code WelcomeUI} with the necessary UI components for user registration, login, and logout.
     *
     * @param patientRegistrationUI The {@code PatientRegistrationUI} that handles the user registration process for new patients.
     * @param logoutUI The {@code LogoutUI} that manages the logout process when the user chooses to exit the system.
     * @param loginUI The {@code LoginUI} that manages the login process for existing users, allowing them to log into the system.
     */
    public WelcomeUI(PatientRegistrationUI patientRegistrationUI, LogoutUI logoutUI, LoginUI loginUI) {
        this.patientRegistrationUI = patientRegistrationUI;
        this.loginUI = loginUI;
        this.logoutUI = logoutUI;
    }

    /**
     * Displays the welcome UI for every User that visits the Hospital, offering options to register, log in, or exit the system.
     * It continuously prompts the user until a valid choice is made, allowing for registration, login, or system exit.
     *
     * @param applicationContext The {@code ApplicationContext} that provides the necessary application data, services, and repositories for managing user interactions.
     */
    public void welcomeUI(ApplicationContext applicationContext) {
        try {
            do {
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
                    case 2 -> loginUI.loginUI(applicationContext);
                    case 3 -> quit = logoutUI.logoutUI();
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
