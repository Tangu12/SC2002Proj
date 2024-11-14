package Services;

public class ForgotPasswordService {
    private CredentialsService credentialsService;

    public ForgotPasswordService(CredentialsService credentialsService) {
        this.credentialsService = credentialsService;
    }

    public boolean verifySecurityQuestion(String userID){
        System.out.println("Please answer the security question : ");
        System.out.println(getSecurityQuestion(userID));
        String plainTextSecurityAnswer = InputService.inputString();
        return credentialsService.verifySecurityQuestion(userID, plainTextSecurityAnswer);
    }

    public void resetPassword(String userID){
        System.out.println("Please answer the reset password : ");
        String plainTextPassword = InputService.inputString();
        credentialsService.changePassword(userID, plainTextPassword);
    }

    public String getSecurityQuestion(String userID) {
        return credentialsService.getSecurityQuestion(userID);
    }
}
