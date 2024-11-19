package Boundary;

import Application.ApplicationContext;
import Controllers.ChangePasswordController;
import Controllers.LoginController;
import Services.InputService;

/**
 * {@code ChangePasswordUI} handles the user interface for changing passwords.
 */
public class ChangePasswordUI {
    private ChangePasswordController changePasswordController;

    /**
     * Constructor for {@code ChangePasswordUI}.
     *
     * @param changePasswordController The Controller associated with the changing password action
     */
    public ChangePasswordUI(ChangePasswordController changePasswordController) {
        this.changePasswordController = changePasswordController;
    }

    /**
     * Handles the password change functionality.
     *
     * @param
     */
    public void changePasswordUI() {
        System.out.print("Please enter your unique Hospital ID : ");
        String inputID = InputService.inputString();

        if (!changePasswordController.validUserID(inputID)) {
            System.out.println("User does not exist! \nYou have been kicked to the main page!");
            return;
        }
        if(changePasswordController.isAccountLocked(inputID)){
            System.out.print("Account is locked by the administrator.");
            return;
        }

        boolean validPassword = false;

        System.out.print("Please enter your password : ");
        String password = InputService.inputString();
        validPassword = changePasswordController.verifyValidPassword(inputID, password);

        if(changePasswordController.isAccountLocked(inputID) || !validPassword) {
            System.out.print("You have been kicked to the main page!");
            return;
        }

        String newPassword = null;
        String confirmPassword = null;

        do{
            System.out.print("Please enter your new password: ");
            newPassword = InputService.inputString();

            System.out.print("Please confirm your new password: ");
            confirmPassword = InputService.inputString();

            if(!newPassword.equals(confirmPassword)){
                System.out.println("Passwords do not match!");
            }
        }
        while(!newPassword.equals(confirmPassword));

        changePasswordController.changePassword(inputID, newPassword);
        //System.out.println("Password changed successfully!");

    }
}
