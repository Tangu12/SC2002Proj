package Services.UserAccount;

import Entity.Repository.PatientDataRepository;
import Entity.User.Patient;
import Services.CredentialsService;

/**
 * {@code PatientAccountService} which inherits the {@code IUserAccountService} interface
 */
public class PatientAccountService implements IUserAccountService<Patient> {
    private CredentialsService credentialsService;
    private PatientDataRepository patientDataRepository;

    /**
     * Constructor for PatientAccountService with Dependency Injection of the {@code CredentialsService} and the {@code PatientDataRepository}
     * @param credentialsService
     * @param patientDataRepository
     */
    public PatientAccountService(CredentialsService credentialsService, PatientDataRepository patientDataRepository) {
        this.credentialsService = credentialsService;
        this.patientDataRepository = patientDataRepository;
    }

    /**
     * Creates a new {@code Patient} in the {@code Patient} and {@code Credentials} file
     * @param patient
     * @param plainTextPassword
     * @param securityQuestion
     * @param plainTextSecurityAnswer
     */
    public void createUserAccount(Patient patient,String plainTextPassword,String securityQuestion,String plainTextSecurityAnswer){
        String userID = patient.getUserID();
        credentialsService.createNewRecord(userID,plainTextPassword,securityQuestion,plainTextSecurityAnswer);
        patientDataRepository.createRecord(patient);
    }

    /**
     * Reads and return {@code Patient} with matching {@code HospitalID}
     * @param userID
     * @return The {@code Patient} with matching {@code HospitalID}
     */
    public Patient getAccount(String userID) {
        return patientDataRepository.readRecord(userID);
    }

    /**
     * Deletes a {@code Patient} Account from the {@code Patient} and {@code Credentials} file
     * @param userID
     */
    public void deleteUserAccount(String userID){
        credentialsService.deleteRecord(userID);
        patientDataRepository.deleteRecord(userID);
    }

    /**
     * Updates a {@code Patient}'s parameters
     * @param patient
     */
    public void updateUserData(Patient patient){
        patientDataRepository.updateRecord(patient);
    }

    /**
     * Verifies a User has entered their correct password
     * @param UserID
     * @param plainTextPassword
     * @return True if the answer given is correct, False otherwise
     */
    @Override
    public boolean verifyPassword(String UserID, String plainTextPassword) {
        return credentialsService.checkPassword(UserID,plainTextPassword);
    }

    /**
     * Verifies if the User has correctly answered their security question
     * @param userID
     * @param plainTextSecurityAnswer
     * @return True if the answer given is correct, False otherwise
     */
    public boolean verifySecurityQuestion(String userID, String plainTextSecurityAnswer){
        return credentialsService.verifySecurityQuestion(userID,plainTextSecurityAnswer);
    }

    /**
     * Returns the remaining logins that the User has before the User is locked out
     * @param userID
     * @return Returns the remaining logins attempts of a User
     */
    public int getNumberOfTriesLeft(String userID){
        return credentialsService.getNumberOfTriesLeft(userID);
    }

    /**
     * Changes the password of {@code Patient} in the {@code Credentials} file
     * @param userID
     * @param newPassword
     */
    public void changePassword(String userID, String newPassword){
        credentialsService.changePassword(userID,newPassword);
    }

    /**
     * Increment the number of login attempts of a User based on their {@code HospitalID}
     * @param userID
     */
    public void incrementNumberOfTries(String userID){
        if(credentialsService.isAccountLocked(userID)){
            return; // Return if the account is already Locked
        }
        credentialsService.incrementLoginAttempts(userID);
    }

    /**
     * Unlocks the account of a User, only to be done by the {@code Administrator}
     * @param userID
     */
    public void unlockAccount(String userID){
        credentialsService.unlockAccount(userID);
    }

}
