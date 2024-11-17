package Services.UserAccount;

import Entity.Enums.Gender;
import Entity.Repository.HospitalStaffRepository;
import Entity.User.Administrator;
import Entity.User.Doctor;
import Services.CredentialsService;

/**
 * {@code AdministratorAccountService} which inherits the {@code IUserAccountService} interface
 */
public class AdministratorAccountService implements IUserAccountService<Administrator> {
    private CredentialsService credentialsService;
    private HospitalStaffRepository hospitalStaffRepository;

    /**
     * Constructor for {@code AdministratorAccountService} with Dependency Injection of the {@code CredentialsService} and the {@code HospitalStaffRepository}
     * @param credentialsService
     * @param hospitalStaffRepository
     */
    public AdministratorAccountService(CredentialsService credentialsService,HospitalStaffRepository hospitalStaffRepository) {
        this.credentialsService = credentialsService;
        this.hospitalStaffRepository = hospitalStaffRepository;
    }

    /**
     * Creates a new {@code Administrator} in the {@code HospitalStaff} and {@code Credentials} file
     * @param administrator
     * @param plainTextPassword
     * @param securityQuestion
     * @param plainTextSecurityAnswer
     */
    @Override
    public void createUserAccount(Administrator administrator, String plainTextPassword, String securityQuestion, String plainTextSecurityAnswer) {
        String userID = administrator.getUserID();
        credentialsService.createNewRecord(userID,plainTextPassword,securityQuestion,plainTextSecurityAnswer);
        hospitalStaffRepository.createRecord(userID,administrator.getName(),administrator.getDomain(),administrator.getGender(),administrator.getAge());
    }

    /**
     * Reads and return {@code Administrator} with matching {@code HospitalID}
     * @param userID
     * @return The {@code Administrator} with matching {@code HospitalID}
     * */
    public Administrator getAccount(String userID) {
        String[] administratorParameters = hospitalStaffRepository.readRecord(userID);
        Administrator administrator = new Administrator(administratorParameters[0], administratorParameters[1], Integer.valueOf(administratorParameters[4]), Gender.valueOf(administratorParameters[3]));
        return administrator;
    }

    /**
     * Deletes a {@code Doctor} Account from the {@code HospitalStaff} and {@code Credentials} file
     */
    @Override
    public void deleteUserAccount(String userID) {
        credentialsService.deleteRecord(userID);
        hospitalStaffRepository.deleteRecord(userID);
    }

    /**
     * Updates a {@code Doctor}'s parameters
     * @param administrator
     */
    @Override
    public void updateUserData(Administrator administrator) {
        String userID = administrator.getUserID();
        String name = administrator.getName();
        String role = String.valueOf(administrator.getDomain());
        String gender = String.valueOf(administrator.getGender());
        String age = String.valueOf(administrator.getAge());

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
     * Changes the password of {@code Doctor} in the {@code Credentials} file
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
     * Loads the list of {@code Administrator} from the {@code HospitalStaff} file
     */
    public void loadAdministratorList(){
        hospitalStaffRepository.loadAdministrator();
    }
}
