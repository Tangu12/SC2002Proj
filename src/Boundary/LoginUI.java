package Boundary;

import Boundary.UserUI.*;
import Controllers.LoginController;
import Entity.User.IUser;
import Services.InputService;
import Services.CredentialsService;
import Services.UserAccount.AccountManager;

import Application.ApplicationContext;

/**
 * LoginUI class which displays the login page when the User wants to log in
 */
public class LoginUI {


    private LoginController loginController;
    private AccountManager accountManager;
    private CredentialsService credentialsService;
    private boolean isTesting;

    /**
     * Constructor for {@code LoginUI}
     * @param loginController
     * @param accountManager
     * @param credentialsService
     */
    public LoginUI(LoginController loginController, AccountManager accountManager, CredentialsService credentialsService, Boolean isTesting) {
        this.loginController = loginController;
        this.accountManager = accountManager;
        this.credentialsService = credentialsService;
        this.isTesting = isTesting;
    }

    /**
     * Login function which takes in the Users {@code HospitalID} and password and compares it with the corresponding {@code HospitalID} and password in {@code Credentials}.
     * </p>
     * If the login is successful, the home page of the User's domain would be displayed
     * @param applicationContext
     * @throws Exception
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

        if(validPassword && !isTesting) {
            IUser user = accountManager.readUser(inputID);
            UserMainPage userMainPage = UserMainPageFactory.getHomePage(user, applicationContext);
            userMainPage.homePage();
        }

    }
}

