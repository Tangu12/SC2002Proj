package Services.UserAccount;

import Entity.Repository.PatientDataRepository;
import Entity.User.Patient;
import Services.CredentialsService;


public class PatientAccountService implements IUserAccountService<Patient> {
    private CredentialsService credentialsService;
    private PatientDataRepository patientDataRepository;

    public PatientAccountService(CredentialsService credentialsService, PatientDataRepository patientDataRepository) {
        this.credentialsService = credentialsService;
        this.patientDataRepository = patientDataRepository;
    }

    /*
    Creates a new entry in the credentials file and the patient data file
    */
    public void createUserAccount(Patient patient,String plainTextPassword,String securityQuestion,String plainTextSecurityAnswer){
        String userID = patient.getUserID();
        credentialsService.createNewRecord(userID,plainTextPassword,securityQuestion,plainTextSecurityAnswer);
        patientDataRepository.createRecord(patient);
    }

    // Reads and return Patient Object with matching HospitalID
    public Patient getAccount(String userID) {
        return patientDataRepository.readRecord(userID);
    }

    /*
    Deletes a patient from the patient data file and the credentials file
    */
    public void deleteUserAccount(String userID){
        credentialsService.deleteRecord(userID);
        patientDataRepository.deleteRecord(userID);
    }

    /*
    Updates a patient record in the PatientData file
    */
    public void updateUserData(Patient patient){
        patientDataRepository.updateRecord(patient);
    }

    @Override
    public boolean verifyPassword(String UserID, String plainTextPassword) {
        return credentialsService.checkPassword(UserID,plainTextPassword);
    }

    /*
    Verifies if the answer given to a security question is correct or not
    */
    public boolean verifySecurityQuestion(String userID, String plainTextSecurityAnswer){
        return credentialsService.verifySecurityQuestion(userID,plainTextSecurityAnswer);
    }

    /*
    Returns the remaining logins that the user has before the user is locked out
    */
    public int getNumberOfTriesLeft(String userID){
        return credentialsService.getNumberOfTriesLeft(userID);
    }

    /*
    Change Password of Patient in the Credentials File
    */
    public void changePassword(String userID, String newPassword){
        credentialsService.changePassword(userID,newPassword);
    }

    /*
    Lock account (system)
    */
    public void incrementNumberOfTries(String userID){
        if(credentialsService.isAccountLocked(userID)){
            return; // Return if the account is already Locked
        }
        credentialsService.incrementLoginAttempts(userID);
    }

    /* Unlock account after too many attempts (to be done by admin) */
    public void unlockAccount(String userID){
        credentialsService.unlockAccount(userID);
    }

}
