package Boundary;

import Boundary.UserUI.*;
import Controllers.AdministratorController;
import Controllers.DoctorController;
import Controllers.LoginController;
import Controllers.PatientController;
import Controllers.PharmacistController;
import Entity.Enums.Domain;
import Entity.User.Administrator;
import Entity.User.Doctor;
import Entity.User.IUser;
import Entity.User.Patient;
import Entity.User.Pharmacist;
import Services.InputService;
import Services.CredentialsService;
import Services.UserAccount.AccountManager;

import java.security.Provider;

import Application.ApplicationContext;

public class LoginUI {


    private LoginController loginController;
    private AccountManager accountManager;
    private CredentialsService credentialsService;

    /**
     * LoginUI class which displays the login
     * @param loginController
     * @param accountManager
     * @param credentialsService
     */
    public LoginUI(LoginController loginController, AccountManager accountManager, CredentialsService credentialsService) {
        this.loginController = loginController;
        this.accountManager = accountManager;
        this.credentialsService = credentialsService;
    }

    public void loginUI(ApplicationContext applicationContext) throws Exception {
        String inputID;
        boolean validUserID = false;
        boolean validPassword = false;
        do {
            System.out.print("Please enter your unique Hospital ID : ");
            inputID = InputService.inputString();
            // function to check valid ID

            validUserID = loginController.validUserID(inputID);
            if (!validUserID) {
                System.out.println("User does not exist!");
            }
        } while (!validUserID);

        do {
            System.out.print("Please enter your password : ");
            String password = InputService.inputString();
            validPassword = loginController.login(inputID, password);
        } while (!credentialsService.isAccountLocked(inputID) && !validPassword);

        if(validPassword) {
            IUser user = accountManager.readUser(inputID);
            UserMainPage userMainPage = UserMainPageFactory.getHomePage(user, applicationContext);
            userMainPage.homePage();
        }

        //user.homePage();

        /*
        switch(user.getDomain()) {
	        case PATIENT:
	        		PatientController patientController = new PatientController((Patient) user, applicationContext.getAppointmentService());
	        		PatientMainPage patientMainPage = new PatientMainPage(patientController);
	        		patientMainPage.mainpage();
	        		break;
	        case PHARMACIST:
	        		PharmacistController pharmacistController = new PharmacistController(applicationContext.getMedicalInventoryService(), applicationContext.getAppointmentService(), (Pharmacist) user);
	        		PharmacistMainPage pharmacistMainPage = new PharmacistMainPage(pharmacistController);
	        		pharmacistMainPage.homePage();
	        		break;
	        case DOCTOR:
	        		DoctorController doctorController = new DoctorController((Doctor) user, applicationContext.getAppointmentService());
	        		DoctorMainPage doctorMainPage = new DoctorMainPage(doctorController);
	        		doctorMainPage.homePage();
	        		break;
	        case ADMINISTRATOR:
	        		AdministratorController administratorController = new AdministratorController((Administrator) user, applicationContext.getStaffManagementService(), applicationContext.getMedicalInventoryService(), applicationContext.getAccountManager());
	        		AdministratorMainPage administratorMainPage = new AdministratorMainPage(administratorController,applicationContext.getHospitalStaffRegistrationService());
	        		administratorMainPage.homePage();
	        		break;
	        	default:
	        		System.out.println("Not Available Domain!");
	        		break;
        }
         */

    }
}

