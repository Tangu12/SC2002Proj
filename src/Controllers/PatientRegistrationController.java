package Controllers;

import Entity.Enums.Domain;
import Entity.Repository.CredentialsRepository;
import Entity.User.IUser;
import Entity.User.Patient;
import Services.UserAccount.AccountManager;
import Services.UserAccount.PatientAccountService;

import java.time.LocalDate;
import java.time.Period;

public class PatientRegistrationController {
    CredentialsRepository credentialsRepository;
    AccountManager accountManager;
    PatientAccountService patientAccountService;

    public PatientRegistrationController(CredentialsRepository credentialsRepository, PatientAccountService patientAccountService) {
        this.credentialsRepository = credentialsRepository;
        //this.accountManager = accountManager;
        this.patientAccountService = patientAccountService;
    }

    /*
    Calls patientAccountService.createUserAccount to create a new account for the patient
    */
    public void registerUser(Patient patient, String plainTextPassword, String securityQuestion, String plainTextSecurityAnswer) {
        patientAccountService.createUserAccount(patient, plainTextPassword, securityQuestion, plainTextSecurityAnswer);
    }

    /*
    Looks through the file to find the number of users and creates a username based on the latest user.(eg if last user in file is P002, function returns P003)
    */
    public String getUserName(){
        int patientCount = credentialsRepository.countUsersByType(Domain.PATIENT);
        if(patientCount < IUser.MAX_USERS){
            return String.format(getIdFormat(), IUser.PATIENT_PREFIX, patientCount + 1);
        }
        else{
            System.out.println("Maximum patient ID (P" + IUser.MAX_USERS + ") reached");
            return null;
        }
    }

    // Helper method
    private String getIdFormat() {
        int digits = String.valueOf(IUser.MAX_USERS).length();
        return "%s%0" + digits + "d";  // This creates format like "%s%04d" for 9999
    }

    public int calculateAge(LocalDate dob) {
        LocalDate currentDate = LocalDate.now();
        return Period.between(dob, currentDate).getYears();
    }


}
