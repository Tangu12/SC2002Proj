package Controllers;

import Entity.Enums.Domain;
import Entity.Repository.CredentialsRepository;
import Entity.User.IUser;
import Entity.User.Patient;
import Services.UserAccount.AccountManager;
import Services.UserAccount.PatientAccountService;

import java.time.LocalDate;
import java.time.Period;

/**
 * {@code PatientRegistrationController} handles all the logic of adding a new {@code Patient}
 */
public class PatientRegistrationController {
    CredentialsRepository credentialsRepository;
    AccountManager accountManager;
    PatientAccountService patientAccountService;

    /**
     * Constructor of {@code PatientRegistrationController}
     * @param credentialsRepository the {@code CredentialsRepository} instance to handle storage and retrieval of credentials
     * @param patientAccountService the {@code PatientAccountService} instance to manage patient account-related operations
     */
    public PatientRegistrationController(CredentialsRepository credentialsRepository, PatientAccountService patientAccountService) {
        this.credentialsRepository = credentialsRepository;
        this.patientAccountService = patientAccountService;
    }

    /**
     * Calls createUserAccount function from the {@code PatientAccountService} class
     * @param patient the {@code Patient} instance representing the new patient
     * @param plainTextPassword the plain text password chosen by the patient
     * @param securityQuestion the security question chosen by the patient
     * @param plainTextSecurityAnswer the plain text answer to the chosen security question
     */
    public void registerUser(Patient patient, String plainTextPassword, String securityQuestion, String plainTextSecurityAnswer) {
        patientAccountService.createUserAccount(patient, plainTextPassword, securityQuestion, plainTextSecurityAnswer);
    }

    /**
     * Looks through the file to find the number of users and creates a username based on the latest user.(eg if last user in file is P002, function returns P003)
     * @return The generated username string, or {@code null} if the maximum number of patients is reached
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

    /**
     * Helper method to generate the format for user ID based on the maximum number of Users
     *
     * @return A formatted string specifying the pattern for the user ID (e.g., "%s%04d" for four-digit IDs)
     */
    private String getIdFormat() {
        int digits = String.valueOf(IUser.MAX_USERS).length();
        return "%s%0" + digits + "d";  // This creates format like "%s%04d" for 9999
    }

    /**
     * Calculates the age of a User, taking the difference between their date of birth(input date) and the current date
     * @param dob the {@code LocalDate} instance representing the user's date of birth
     * @return The age of the user in years
     */
    public int calculateAge(LocalDate dob) {
        LocalDate currentDate = LocalDate.now();
        return Period.between(dob, currentDate).getYears();
    }
}
