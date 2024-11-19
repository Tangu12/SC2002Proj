package Controllers;

import Entity.Credentials;
import Services.CredentialsService;

public class ChangePasswordController {
    private CredentialsService credentialsService;

    public ChangePasswordController(CredentialsService credentialsService) {
        this.credentialsService = credentialsService;
    }

    public boolean verifyValidPassword(String userID, String plainTextPassword) {
        if(credentialsService.checkPassword(userID, plainTextPassword)) {
            credentialsService.unlockAccount(userID);
            return true;
        }
        else {
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

    public boolean isAccountLocked(String userID) {
        return credentialsService.isAccountLocked(userID);
    }


    /**
     * Check if entered UserID is a valid {@code HospitalID}
     * @param userID HospitalID
     * @return True if the UserID is valid, False otherwise
     */
    public boolean validUserID(String userID){
        Credentials userCredentials = credentialsService.getRecord(userID);
        if (userCredentials == null) {
            return false;
        }
        return true;
    }

    public void changePassword(String userID, String newPlainTextPassword) {
        credentialsService.changePassword(userID, newPlainTextPassword);
    }


}
