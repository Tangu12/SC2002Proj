package Controllers;

import Boundary.ForgotPasswordUI;
import Entity.Credentials;
import Services.CredentialsService;
import Services.ForgotPasswordService;
import Services.InputService;
import Services.UserAccount.AccountManager;

public class LoginController {
    private CredentialsService credentialsService;
    private AccountManager accountManager;
    private ForgotPasswordService forgotPasswordService;

    public LoginController(CredentialsService credentialsService, AccountManager accountManager,ForgotPasswordService forgotPasswordService) {
        this.credentialsService = credentialsService;
        this.accountManager = accountManager;
        this.forgotPasswordService = forgotPasswordService;
    }

    // Login Function, checks if account is locked, then if password matches
    public boolean login(String userID, String plainTextPassword) {
        if (credentialsService.isAccountLocked(userID)) {
            System.out.println("Account is locked due to too many failed attempts.");
            return false;
        }
        if (credentialsService.checkPassword(userID, plainTextPassword)) {
            credentialsService.unlockAccount(userID);
            return true;
        } else {
            credentialsService.incrementLoginAttempts(userID);
            int attemptsLeft = credentialsService.getNumberOfTriesLeft(userID);
            System.out.println("Incorrect password. Attempts left: " + attemptsLeft);

            if (attemptsLeft <= 0) {
                System.out.println("Account is now locked due to too many failed attempts.");
                // Ask security Question
                boolean sucess = forgotPasswordService.verifySecurityQuestion(userID);

                if(sucess) {
                    System.out.println("Successfully verified.");
                    forgotPasswordService.resetPassword(userID);
                    return true;
                }
                credentialsService.lockAccount(userID);
            }
            System.out.println("Your Account is now locked due to giving the wrong answer");
            return false;
        }
    }

    // Check if UserID is Valid
    public boolean validUserID(String userID){
        Credentials userCredentials = credentialsService.getRecord(userID);
        if (userCredentials == null) {
            return false;
        }
        return true;
    }


}

