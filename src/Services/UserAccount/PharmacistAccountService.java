package Services.UserAccount;

import Entity.Repository.HospitalStaffRepository;
import Entity.User.Pharmacist;
import Services.CredentialsService;

public class PharmacistAccountService implements IUserAccountService<Pharmacist> {
    private CredentialsService credentialsService;
    private HospitalStaffRepository hospitalStaffRepository;

    // Dependency Injection
    public PharmacistAccountService(CredentialsService credentialsService,HospitalStaffRepository hospitalStaffRepository) {
        this.credentialsService = credentialsService;
        this.hospitalStaffRepository = hospitalStaffRepository;
    }

    // attributes -> (Staff ID,Name,Role,Gender,Age)
    // Creates a new Pharmacist Account
    @Override
    public void createUserAccount(Pharmacist pharmacist, String plainTextPassword, String securityQuestion, String plainTextSecurityAnswer) {
        String userID = pharmacist.getUserID();
        credentialsService.createNewRecord(userID,plainTextPassword,securityQuestion,plainTextSecurityAnswer);
        hospitalStaffRepository.createRecord(userID,pharmacist.getName(),pharmacist.getDomain(),pharmacist.getGender(),pharmacist.getAge());
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
    public void updateUserData(Pharmacist pharmacist) {
        String userID = pharmacist.getUserID();
        String name = pharmacist.getName();
        String role = String.valueOf(pharmacist.getDomain());
        String gender = String.valueOf(pharmacist.getGender());
        String age = String.valueOf(pharmacist.getAge());

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
    Change Password of Pharmacist in the Credentials File
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
