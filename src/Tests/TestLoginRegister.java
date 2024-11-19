package Tests;

import Boundary.*;
import Controllers.LoginController;
import Controllers.PatientRegistrationController;
import Entity.Enums.BloodType;
import Entity.Enums.Department;
import Entity.Enums.Domain;
import Entity.Enums.Gender;
import Entity.Repository.CredentialsRepository;
import Entity.Repository.HospitalStaffRepository;
import Entity.Repository.PatientDataRepository;
import Entity.User.Administrator;
import Entity.User.Doctor;
import Entity.User.Patient;
import Entity.User.Pharmacist;
import Services.CredentialsService;
import Services.ForgotPasswordService;
import Services.UserAccount.*;

import java.time.LocalDate;

public class TestLoginRegister {
    public static void main(String[] args) {
        String workingDir = System.getProperty("user.dir");
        String credentialsPath = workingDir + "/src/Tests/TestingFiles/credentials.txt";
        String patientsDataPath = workingDir + "/src/Tests/TestingFiles/patientsData.txt";
        String hospitalDataPath = workingDir + "/src/Tests/TestingFiles/HospitalStaff.csv";

        // Setup Repositories
        CredentialsRepository credentialsRepository = new CredentialsRepository(credentialsPath);
        CredentialsService credentialsService = new CredentialsService(credentialsRepository);
        PatientDataRepository patientDataRepository = new PatientDataRepository(patientsDataPath);
        HospitalStaffRepository hospitalStaffRepository = new HospitalStaffRepository(hospitalDataPath);

        // Create Services
        IUserAccountService<Patient> patientAccountService = new PatientAccountService(credentialsService, patientDataRepository);
        IUserAccountService<Doctor> doctorAccountService = new DoctorAccountService(credentialsService, hospitalStaffRepository);
        IUserAccountService<Pharmacist> pharmacistAccountService = new PharmacistAccountService(credentialsService, hospitalStaffRepository);
        IUserAccountService<Administrator> administratorAccountService = new AdministratorAccountService(credentialsService, hospitalStaffRepository);
        AccountManager accountManager = new AccountManager(patientAccountService, doctorAccountService, pharmacistAccountService, administratorAccountService,credentialsService);
        ForgotPasswordService forgotPasswordService = new ForgotPasswordService(credentialsService);

        // Setup Controllers
        PatientRegistrationController patientRegistrationController= new PatientRegistrationController(credentialsRepository, (PatientAccountService) patientAccountService);
        LoginController loginController = new LoginController(credentialsService, accountManager,forgotPasswordService);

        // Create Boundaries
        PatientRegistrationUI patientRegistrationUI = new PatientRegistrationUI(patientRegistrationController);
        LogoutUI logoutUI = new LogoutUI();
        LoginUI loginUI = new LoginUI(loginController, accountManager, credentialsService);
        //WelcomeUI welcomeUI = new WelcomeUI(patientRegistrationUI,logoutUI,loginUI,new ChangePasswordUI());

        // Initialise
        /*
        LocalDate dob = LocalDate.of(2002, 5, 14);

        Patient Ryan = new Patient("P001", "Ryan Wong", 40, Gender.MALE, Domain.PATIENT,
                null, "ryanguy@gmail.com", dob, BloodType.A_NEGATIVE);
        patientAccountService.createUserAccount(Ryan,"Ryan123","What is this person's name","ryan");

        Doctor Min = new Doctor("D001","Min",40,Gender.MALE, Department.Cardiology);
        doctorAccountService.createUserAccount(Min,"Min123","What is this person's name","min");

        Administrator Justin = new Administrator("A001","Justin",40,Gender.MALE);
        administratorAccountService.createUserAccount(Justin,"Justin123","What is this person's name","justin");

        Pharmacist Hung = new Pharmacist("R001","Hung",40,Gender.MALE,Domain.PHARMACIST);
        pharmacistAccountService.createUserAccount(Hung,"Hung123","What is this person's name","hung");
        */

        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // Testing Functions
        //System.out.println(patientRegistrationController.getUserName());
        //welcomeUI.welcomeUI();

        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // Clean Up
        //patientAccountService.deleteUserAccount("P006");
    }
}
