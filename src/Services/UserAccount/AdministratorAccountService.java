package Services.UserAccount;

import Entity.Enums.Gender;
import Entity.Repository.HospitalStaffRepository;
import Entity.User.Administrator;
import Entity.User.Doctor;
import Services.CredentialsService;

public class AdministratorAccountService implements IUserAccountService<Administrator> {
    private CredentialsService credentialsService;
    private HospitalStaffRepository hospitalStaffRepository;

    // Dependency Injection
    public AdministratorAccountService(CredentialsService credentialsService,HospitalStaffRepository hospitalStaffRepository) {
        this.credentialsService = credentialsService;
        this.hospitalStaffRepository = hospitalStaffRepository;
    }


    // attributes -> (Staff ID,Name,Role,Gender,Age)
    // Creates a new Administrator Account
    @Override
    public void createUserAccount(Administrator administrator, String plainTextPassword, String securityQuestion, String plainTextSecurityAnswer) {
        String userID = administrator.getUserID();
        credentialsService.createNewRecord(userID,plainTextPassword,securityQuestion,plainTextSecurityAnswer);
        hospitalStaffRepository.createRecord(userID,administrator.getName(),administrator.getDomain(),administrator.getGender(),administrator.getAge());
    }

    // Reads and return Doctor Object with matching HospitalID
    public Administrator getAccount(String userID) {
        String[] administratorParameters = hospitalStaffRepository.readRecord(userID);
        Administrator administrator = new Administrator(administratorParameters[0], administratorParameters[1], Integer.valueOf(administratorParameters[4]), Gender.valueOf(administratorParameters[3]));
        return administrator;
    }

    // Deletes a User Account
    @Override
    public void deleteUserAccount(String userID) {
        credentialsService.deleteRecord(userID);
        hospitalStaffRepository.deleteRecord(userID);
    }

    // Updates User Data
    // (Staff ID,Name,Role,Gender,Age)
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
    Change Password of Administrator in the Credentials File
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
