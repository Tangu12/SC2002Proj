package Tests;

import Boundary.LoginUI;
import Boundary.LogoutUI;
import Boundary.PatientRegistrationUI;
import Boundary.WelcomeUI;
import Controllers.LoginController;
import Controllers.PatientRegistrationController;
import Entity.Repository.CredentialsRepository;
import Entity.Repository.PatientDataRepository;
import Services.CredentialsService;
import Services.UserAccount.PatientAccountService;

import java.time.LocalDate;

public class TestLoginRegister {
    public static void main(String[] args) {
        String workingDir = System.getProperty("user.dir");
        String credentialsPath = workingDir + "/src/Tests/TestingFiles/credentials.txt";
        String patientsDataPath = workingDir + "/src/Tests/TestingFiles/patientsData.txt";

        // Setup Repositories
        CredentialsRepository credentialsRepository = new CredentialsRepository(credentialsPath);
        CredentialsService credentialsService = new CredentialsService(credentialsRepository);
        PatientDataRepository patientDataRepository = new PatientDataRepository(patientsDataPath);

        // Create Services
        PatientAccountService patientAccountService = new PatientAccountService(credentialsService,patientDataRepository);

        // Setup Controllers
        PatientRegistrationController patientRegistrationController= new PatientRegistrationController(credentialsRepository,patientAccountService);
        LoginController loginController = new LoginController();

        // Create Boundaries
        PatientRegistrationUI patientRegistrationUI = new PatientRegistrationUI(patientRegistrationController);
        LogoutUI logoutUI = new LogoutUI();
        LoginUI loginUI = new LoginUI(loginController);
        WelcomeUI welcomeUI = new WelcomeUI(patientRegistrationUI,logoutUI,loginUI);

        // Initialise
        //LocalDate dob = LocalDate.of(2002, 5, 14);
        //Patient Ryan = new Patient("P001", "Ryan Wong", 21, Gender.MALE, Domain.PATIENT,
        //        null, "ryanguy@gmail.com", dob, BloodType.A_NEGATIVE);
        //patientAccountService.createUserAccount(Ryan,"Ryan123","What is your Favourite Food","MCD");
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // Testing Functions
        //System.out.println(patientRegistrationController.getUserName());
        welcomeUI.welcomeUI();

        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // Clean Up
        //patientAccountService.deleteUserAccount("P006");

    }
}
