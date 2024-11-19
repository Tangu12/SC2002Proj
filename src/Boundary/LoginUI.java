package Boundary;

import Boundary.UserUI.*;
import Controllers.AdministratorController;
import Controllers.DoctorController;
import Controllers.LoginController;
import Controllers.PatientController;
import Controllers.PharmacistController;
import Entity.Enums.Domain;
import Entity.User.Administrator;
import Entity.User.Doctor;
import Entity.User.IUser;
import Entity.User.Patient;
import Entity.User.Pharmacist;
import Services.InputService;
import Services.CredentialsService;
import Services.UserAccount.AccountManager;

import java.security.Provider;

import Application.ApplicationContext;

/**
 * LoginUI class which displays the login page when the User wants to log in
 */
public class LoginUI {

    private LoginController loginController;
    private AccountManager accountManager;
    private CredentialsService credentialsService;

    /**
     * Constructor for {@code LoginUI}
     *
     * @param loginController The {@code LoginController} responsible for managing login operations.
     * @param accountManager The {@code AccountManager} that handles user account management.
     * @param credentialsService The {@code CredentialsService} that manages credentials validation and other related operations.
     */
    public LoginUI(LoginController loginController, AccountManager accountManager, CredentialsService credentialsService) {
        this.loginController = loginController;
        this.accountManager = accountManager;
        this.credentialsService = credentialsService;
    }

    /**
     * Login function which takes in the Users {@code HospitalID} and password and compares it with the corresponding {@code HospitalID} and password in {@code Credentials}.
     * If the login is successful, the home page of the User's domain would be displayed
     *
     * @param applicationContext The {@code ApplicationContext} that contains the context of the application, including repositories and services.
     * @throws Exception If an error occurs during login or while displaying the user’s home page.
     */
    public void loginUI(ApplicationContext applicationContext) throws Exception {
        String inputID;
        boolean validUserID = false;
        boolean validPassword = false;

        do {
            System.out.print("Please enter your unique Hospital ID : ");
            inputID = InputService.inputString();
            // function to check valid ID

            validUserID = loginController.validUserID(inputID);
            if (!validUserID) {
                System.out.println("User does not exist!");
            }
        } while (!validUserID);

        do {
            System.out.print("Please enter your password : ");
            String password = InputService.inputString();
            validPassword = loginController.login(inputID, password);
        } while (!credentialsService.isAccountLocked(inputID) && !validPassword);

        if(validPassword) {
            IUser user = accountManager.readUser(inputID);
            UserMainPage userMainPage = UserMainPageFactory.getHomePage(user, applicationContext);
            userMainPage.homePage();
        }
    }
}
