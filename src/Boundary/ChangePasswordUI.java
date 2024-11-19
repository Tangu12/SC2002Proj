package Boundary;

import Application.ApplicationContext;
import Controllers.LoginController;
import Services.InputService;

/**
 * {@code ChangePasswordUI} handles the user interface for changing passwords.
 */
public class ChangePasswordUI {
    private LoginController loginController;

    /**
     * Constructor for {@code ChangePasswordUI}.
     *
     * @param loginController The controller to handle password changes.
     */
    public ChangePasswordUI(LoginController loginController) {
        this.loginController = loginController;
    }

    /**
     * Handles the password change functionality.
     *
     * @param applicationContext The application context.
     */
    public void changePasswordUI(ApplicationContext applicationContext) {
        System.out.print("Please enter your unique Hospital ID: ");
        String userID = InputService.inputString();

        if (!loginController.validUserID(userID)) {
            System.out.println("User ID does not exist. Please try again.");
            return;
        }

        System.out.print("Please enter your current password: ");
        String currentPassword = InputService.inputString();

        System.out.print("Please enter your new password: ");
        String newPassword = InputService.inputString();

        System.out.print("Please confirm your new password: ");
        String confirmPassword = InputService.inputString();

        if (!newPassword.equals(confirmPassword)) {
            System.out.println("Passwords do not match. Please try again.");
            return;
        }

        boolean success = loginController.changePassword(userID, currentPassword, newPassword);
        if (success) {
            System.out.println("Password changed successfully!");
        } else {
            System.out.println("Failed to change password. Please try again.");
        }
    }
}
