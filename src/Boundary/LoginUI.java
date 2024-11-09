package Boundary;

import Controllers.LoginController;
import Services.InputService;
import Services.PasswordService;

public class LoginUI {
    public static void loginUI() {
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

