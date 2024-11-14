package Boundary;

import Controllers.LoginController;
import Entity.Enums.Domain;
import Entity.User.IUser;
import Services.InputService;
import Services.CredentialsService;
import Services.UserAccount.AccountManager;

import java.security.Provider;

public class LoginUI {


    private LoginController loginController;
    private AccountManager accountManager;
    private CredentialsService credentialsService;

    public LoginUI(LoginController loginController, AccountManager accountManager, CredentialsService credentialsService) {
        this.loginController = loginController;
        this.accountManager = accountManager;
        this.credentialsService = credentialsService;
    }

    public void loginUI() {
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
        } while (!credentialsService.isAccountLocked(inputID));

        IUser user = accountManager.readUser(inputID);
        //user.homePage();
    }
}

