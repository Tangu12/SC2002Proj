package Services.UserAccount;

import Entity.Enums.Domain;
import Entity.Enums.Gender;
import Entity.MedicalRecord;
import Entity.Repository.HospitalStaffRepository;
import Entity.User.Doctor;
import Entity.User.Pharmacist;
import Services.CredentialsService;

import java.util.ArrayList;

/**
 * {@code PharmacistAccountService} which inherits the {@code IUserAccountService} interface
 */
public class PharmacistAccountService implements IUserAccountService<Pharmacist> {
    private CredentialsService credentialsService;
    private HospitalStaffRepository hospitalStaffRepository;

    /**
     * Constructor for PharmacistAccountService with Dependency Injection of the {@code CredentialsService} and the {@code HospitalStaffRepository}
     * @param credentialsService
     * @param hospitalStaffRepository
     */
    public PharmacistAccountService(CredentialsService credentialsService,HospitalStaffRepository hospitalStaffRepository) {
        this.credentialsService = credentialsService;
        this.hospitalStaffRepository = hospitalStaffRepository;
    }

    /**
     * Creates a new {@code Pharmacist} in the {@code HospitalStaff} and {@code Credentials} file
     * @param pharmacist
     * @param plainTextPassword
     * @param securityQuestion
     * @param plainTextSecurityAnswer
     */
    @Override
    public void createUserAccount(Pharmacist pharmacist, String plainTextPassword, String securityQuestion, String plainTextSecurityAnswer) {
        String userID = pharmacist.getUserID();
        credentialsService.createNewRecord(userID,plainTextPassword,securityQuestion,plainTextSecurityAnswer);
        hospitalStaffRepository.createRecord(userID,pharmacist.getName(),pharmacist.getDomain(),pharmacist.getGender(),pharmacist.getAge());
    }

    /**
     * Reads and return {@code Pharmacist} with matching {@code HospitalID}
     * @param userID
     * @return The {@code Pharmacist} with matching {@code HospitalID}
     */
    public Pharmacist getAccount(String userID) {
        String[] pharmacistParameters = hospitalStaffRepository.readRecord(userID);
        ArrayList<MedicalRecord> medicalRecords = new ArrayList<MedicalRecord>(); // how to get array of medical records?
        Pharmacist pharmacist = new Pharmacist(pharmacistParameters[0], pharmacistParameters[1], Integer.valueOf(pharmacistParameters[4]), Gender.valueOf(pharmacistParameters[3]), Domain.PHARMACIST, medicalRecords);
        return pharmacist;
    }


    /**
     * Deletes a {@code Pharmacist} Account from the {@code HospitalStaff} and {@code Credentials} file
     */
    @Override
    public void deleteUserAccount(String userID) {
        credentialsService.deleteRecord(userID);
        hospitalStaffRepository.deleteRecord(userID);
    }

    /**
     * Updates a {@code Pharmacist}'s parameters
     * @param pharmacist
     */
    @Override
    public void updateUserData(Pharmacist pharmacist) {
        String userID = pharmacist.getUserID();
        String name = pharmacist.getName();
        String role = String.valueOf(pharmacist.getDomain());
        String gender = String.valueOf(pharmacist.getGender());
        String age = String.valueOf(pharmacist.getAge());

        String[] user = {userID,name,role,gender,age};

        hospitalStaffRepository.updateRecord(user);
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
     * Changes the password of {@code Pharmacist} in the {@code Credentials} file
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

    /**
     * Loads the list of {@code Pharmacist} from the {@code HospitalStaff} file
     */
    public void loadPharmacistList(){
        hospitalStaffRepository.loadPharmacistList();
    }
}
