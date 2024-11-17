package Controllers;

import Entity.Credentials;
import Services.CredentialsService;
import Services.ForgotPasswordService;
import Services.UserAccount.AccountManager;

/**
 * {@code LoginController} handles all the logic a User trying to log in
 */
public class LoginController {
    private CredentialsService credentialsService;
    private AccountManager accountManager;
    private ForgotPasswordService forgotPasswordService;

    /**
     * Constructor for {@code LoginController}
     * @param credentialsService
     * @param accountManager
     * @param forgotPasswordService
     */
    public LoginController(CredentialsService credentialsService, AccountManager accountManager,ForgotPasswordService forgotPasswordService) {
        this.credentialsService = credentialsService;
        this.accountManager = accountManager;
        this.forgotPasswordService = forgotPasswordService;
    }

    /**
     * Login Function, checks if account is locked, then if password matches
     * @param userID
     * @param plainTextPassword
     * @return
     */
    public boolean login(String userID, String plainTextPassword) {
        if (credentialsService.isAccountLocked(userID)) {
            System.out.println("Account is locked by the administrator.");
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
                boolean success = forgotPasswordService.verifySecurityQuestion(userID);

                if(success) {
                    System.out.println("Successfully verified.");
                    forgotPasswordService.resetPassword(userID);
                    return true;
                }
                System.out.println("Wrong answer to the security question!");
                credentialsService.lockAccount(userID);
            }
            return false;
        }
    }

    /**
     * Check if entered UserID is a valid {@code HospitalID}
     * @param userID
     * @return True if the UserID is valid, False otherwise
     */
    public boolean validUserID(String userID){
        Credentials userCredentials = credentialsService.getRecord(userID);
        if (userCredentials == null) {
            return false;
        }
        return true;
    }


}

