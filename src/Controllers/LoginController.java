package Controllers;

import Boundary.ForgotPasswordUI;
import Services.PasswordService;

// Import credentials Service




public class LoginController {


    public static void login(String hospitalID, String password) {

        int login_attempts = 0;

        if (PasswordService.checkPassword(hospitalID, password)) {

            // Dependency Injection? -> instead of instantiating a new User, use a constructor and getters and setters

            // AccountManager creates user

            switch(user.getDomain()) {
                // case PATIENT-> PatientMainPage.patientMainPage
                break;

                // case DOCTOR-> DoctorMainPage.doctorMainPage
                break;

                // case PHARMACIST-> PharmacistMainPage.pharmacistMainPage
                break;

                // case ADMINISTRATOR-> AdministratorMainPage.administratorMainPage
                break;

                default:
                    System.out.println("ERROR GETTING DOMAIN");
                    break;
            }
        }

        else {
            ForgotPasswordUI.askToRetry();
        }






        if (!successfulLogin) {
            System.out.println("Wrong Password!");
            login_attempts++;
            if (login_attempts >= 3) {
                System.out.println("Too many attempts. Please change your password: ");
                // Ask security question, if fail kick out to main page
                securitycheck = Credentials.askSecurityQuestion(inputID);
                if (!securitycheck) {
                    System.out.println("Wrong answer to security question. You have been logged out. ");
                    return null;
                }
                System.out.println("Please enter your new password: ");
                String new_password = InputScanner.sc.nextLine().trim();
                changePassword(inputID, new_password);
                return null;
            }
        }
        break;



    }
}
