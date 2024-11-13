package Services;

import Entity.Repository.HospitalStaffRepository;
import Entity.User.Doctor;

public class DoctorAccountService implements IUserAccountService<Doctor>{
    private CredentialsService credentialsService;
    private HospitalStaffRepository hospitalStaffRepository;

    // Dependency Injection
    public DoctorAccountService(CredentialsService credentialsService,HospitalStaffRepository hospitalStaffRepository) {
        this.credentialsService = credentialsService;
        this.hospitalStaffRepository = hospitalStaffRepository;
    }


    // attributes -> (Staff ID,Name,Role,Gender,Age)
    // Creates a new Administrator Account
    @Override
    public void createUserAccount(Doctor doctor, String plainTextPassword, String securityQuestion, String plainTextSecurityAnswer) {
        String userID = doctor.getUserID();
        credentialsService.createNewRecord(userID,plainTextPassword,securityQuestion,plainTextSecurityAnswer);
        hospitalStaffRepository.createRecord(userID,doctor.getName(),doctor.getDomain(),doctor.getGender(),doctor.getAge());
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
    public void updateUserData(Doctor doctor) {
        String userID = doctor.getUserID();
        String name = doctor.getName();
        String role = String.valueOf(doctor.getDomain());
        String gender = String.valueOf(doctor.getGender());
        String age = String.valueOf(doctor.getAge());

        String[] user = {userID,name,role,gender,age};

        hospitalStaffRepository.updateRecord(user);
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
    Change Password of Doctor in the Credentials File
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
