package Boundary;

import Services.InputService;
import Services.CredentialsService;

public class ForgotPasswordUI {
    public static void askToRetry() {
        System.out.println("Wrong Password!");
    }
    /*
    public static void forgotPassword(String inputID) {
        System.out.println("To change your password, please answer this security question: ");
        int sqAttenpts = 0;
        if (CredentialsService.askSecurityQuestion(inputID)) {
            System.out.println("Enter your new password: ");
            String newPassword = InputService.inputString();
            CredentialsService.changePassword(inputID, newPassword);
        }
        else {
            System.out.println("Exceeded number of tries. You have been logged out. ");
            // How to log out the person?
        }
    */
//        System.out.println("Please enter your new password: ");
//        String new_password = InputScanner.sc.nextLine().trim();
//        changePassword(inputID, new_password);

}
