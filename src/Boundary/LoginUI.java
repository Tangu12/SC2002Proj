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
            System.out.println(inputID);
            // function to check valid ID

            validUserID = loginController.validUserID(inputID);
            System.out.println(inputID);
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











        /*
        int choice = 0;
        String inputID;
        String inputPassword;
        boolean validUserID;
        boolean securitycheck = false;
        int login_attempts = 0;

        do {
            System.out.print("Please enter your unique Hospital ID : ");
            inputID = InputService.inputString();
            validUserID = true; // function to check valid ID
            if (!validUserID) {
                System.out.println("User does not exist!");
            }
        } while (!validUserID);

        do {
            System.out.print("(1) Please enter your password \n");
            System.out.print("(2) Forgot password \n");
            try {
                choice = InputService.inputInteger();
                InputService.inputString();
                switch (choice) {
                    case 1:
                        System.out.print("Password : \n");
                        inputPassword = InputService.inputString();
                        LoginController.login(inputID, inputPassword);
                        login_attempts++;
                    case 2:

                        ForgotPasswordUI.forgotPassword(inputID);
                        login_attempts++;
                }
            } catch (Exception e) {
                System.out.println("Invalid Entry. Enter 1 or 2");
            }
        } while(login_attempts < 3);
*/

//                        PasswordService.checkPassword(); // Should return a Bool and then continue?
//                        if (!successfulLogin) {
//                            System.out.println("Wrong Password!");
//                            login_attempts++;
//                            if (login_attempts >= 3) {
//                                System.out.println("Too many attempts. Please change your password: ");
//                                // Ask security question, if fail kick out to main page
//                                securitycheck = Credentials.askSecurityQuestion(inputID);
//                                if (!securitycheck) {
//                                    System.out.println("Wrong answer to security question. You have been logged out. ");
//                                    return null;
//                                }
//                                System.out.println("Please enter your new password: ");
//                                String new_password = InputScanner.sc.nextLine().trim();
//                                changePassword(inputID, new_password);
//                                return null;
//                            }
//                        }
//                        break;
//
//                    case 2:
//                        System.out.println("To change your password, please answer this security question: ");
//                        // Ask security question, if fail kick out to main page
//                        securitycheck = Credentials.askSecurityQuestion(inputID);
//                        if (!securitycheck) {
//                            System.out.println("Wrong answer to security question. You have been logged out. ");
//                            return null;
//                        }
//                        System.out.println("Please enter your new password: ");
//                        String new_password = InputScanner.sc.nextLine().trim();
//                        changePassword(inputID, new_password);
//                        break;
//                    default:
//                        System.out.println("Invalid choice. Please enter 1 or 2 : ");
//                        break;
//                }
//
//            } catch (Exception e) {
//                System.out.println("Invalid input. Please enter 1 or 2: \n");
//                continue;
//            }
//        } while (choice != 1 || choice != 2);


        // Call LoginController.login function


    }
}

