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
     * @param credentialsService The service responsible for handling user credentials and authentication.
     * @param patientDataRepository The repository responsible for storing and accessing patient data.
     */
    public PatientAccountService(CredentialsService credentialsService, PatientDataRepository patientDataRepository) {
        this.credentialsService = credentialsService;
        this.patientDataRepository = patientDataRepository;
    }

    /**
     * Creates a new {@code Patient} in the {@code Patient} and {@code Credentials} file
     * @param patient The {@code Patient} object containing the patient's information to be added.
     * @param plainTextPassword The plain text password for the {@code Patient}.
     * @param securityQuestion The security question for the patient to be used for account recovery or verification.
     * @param plainTextSecurityAnswer The plain text answer to the security question.
     */
    public void createUserAccount(Patient patient,String plainTextPassword,String securityQuestion,String plainTextSecurityAnswer){
        String userID = patient.getUserID();
        credentialsService.createNewRecord(userID,plainTextPassword,securityQuestion,plainTextSecurityAnswer);
        patientDataRepository.createRecord(patient);
    }

    /**
     * Reads and return {@code Patient} with matching {@code HospitalID}
     * @param userID The unique identifier (hospital ID) for the {@code Patient} to be fetched.
     * @return The {@code Patient} with the matching {@code HospitalID}.
     */
    public Patient getAccount(String userID) {
        return patientDataRepository.readRecord(userID);
    }

    /**
     * Deletes a {@code Patient} Account from the {@code Patient} and {@code Credentials} file
     * @param userID The unique identifier (hospital ID) for the {@code Patient} account to be deleted.
     */
    public void deleteUserAccount(String userID){
        credentialsService.deleteRecord(userID);
        patientDataRepository.deleteRecord(userID);
    }

    /**
     * Updates a {@code Patient}'s parameters
     * @param patient The {@code Patient} object with updated information to be saved.
     */
    public void updateUserData(Patient patient){
        patientDataRepository.updateRecord(patient);
    }

    /**
     * Verifies a User has entered their correct password
     * @param userID The unique identifier (hospital ID) of the user whose password is being verified.
     *      * @param plainTextPassword The plain text password entered by the user.
     *      * @return {@code true} if the entered password matches the one stored for the user, {@code false} otherwise.
     *      */
    @Override
    public boolean verifyPassword(String userID, String plainTextPassword) {
        return credentialsService.checkPassword(userID,plainTextPassword);
    }

    /**
     * Verifies if the User has correctly answered their security question
     * @param userID The unique identifier (hospital ID) of the user whose security question answer is being verified.
     * @param plainTextSecurityAnswer The plain text answer to the security question entered by the user.
     * @return {@code true} if the entered answer is correct, {@code false} otherwise.
     */
    public boolean verifySecurityQuestion(String userID, String plainTextSecurityAnswer){
        return credentialsService.verifySecurityQuestion(userID,plainTextSecurityAnswer);
    }

    /**
     * Returns the remaining logins that the User has before the User is locked out
     * @param userID The unique identifier (hospital ID) of the user whose remaining login attempts are being queried.
     * @return The number of login attempts left for the user before they are locked out.
     */
    public int getNumberOfTriesLeft(String userID){
        return credentialsService.getNumberOfTriesLeft(userID);
    }

    /**
     * Changes the password of {@code Patient} in the {@code Credentials} file
     * @param userID The unique identifier (hospital ID) of the user whose password is being changed.
     * @param newPassword The new password to be set for the user.
     */
    public void changePassword(String userID, String newPassword){
        credentialsService.changePassword(userID,newPassword);
    }

    /**
     * Increment the number of login attempts of a User based on their {@code HospitalID}
     * @param userID The unique identifier (hospital ID) of the user whose login attempts are being incremented.
     */
    public void incrementNumberOfTries(String userID){
        if(credentialsService.isAccountLocked(userID)){
            return; // Return if the account is already Locked
        }
        credentialsService.incrementLoginAttempts(userID);
    }

    /**
     * Unlocks the account of a User, only to be done by the {@code Administrator}
     * @param userID The unique identifier (hospital ID) of the user whose account is being unlocked.
     */
    public void unlockAccount(String userID){
        credentialsService.unlockAccount(userID);
    }

}
