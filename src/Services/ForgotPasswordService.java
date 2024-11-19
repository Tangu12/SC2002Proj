package Services;

/**
 * {@code ForgotPasswordService} class which handles all the logic dealing with a User's password
 */
public class ForgotPasswordService {
    private CredentialsService credentialsService;

    /**
     * Constructor for {@code ForgotPasswordService}
     * @param credentialsService
     */
    public ForgotPasswordService(CredentialsService credentialsService) {
        this.credentialsService = credentialsService;
    }

    /**
     * Asks the User their security question based on their {@code HospitalID}
     * @param userID
     * @return
     */
    public boolean verifySecurityQuestion(String userID){
        System.out.println("Please answer the security question : ");
        System.out.println(getSecurityQuestion(userID));
        String plainTextSecurityAnswer = InputService.inputString();

        boolean success = credentialsService.verifySecurityQuestion(userID, plainTextSecurityAnswer.toLowerCase());
        if(success){
            credentialsService.unlockAccount(userID);
        }
        return success;
    }

    /**
     * Resets a User's password based on the User's {@code HospitalID}
     * @param userID
     */
    public void resetPassword(String userID){
        System.out.println("Please answer the reset password : ");
        String plainTextPassword = InputService.inputString();
        credentialsService.changePassword(userID, plainTextPassword);
    }

    /**
     * The getter method of a User's security question
     * @param userID
     * @return The User's security question
     */
    public String getSecurityQuestion(String userID) {
        return credentialsService.getSecurityQuestion(userID);
    }
}
