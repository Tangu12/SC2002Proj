package Controllers;

import Boundary.ForgotPasswordUI;
import Services.CredentialsService;

public class LoginController {
    private CredentialsService credentialsService;

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
                credentialsService.lockAccount(userID);
            }
            return false;
        }
    }

}

