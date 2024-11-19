package Services.UserAccount;

import Entity.Enums.Domain;
import Entity.Enums.Gender;
import Entity.Repository.HospitalStaffRepository;
import Entity.User.Pharmacist;
import Services.CredentialsService;

/**
 * {@code PharmacistAccountService} which inherits the {@code IUserAccountService} interface
 */
public class PharmacistAccountService implements IUserAccountService<Pharmacist> {
    private CredentialsService credentialsService;
    private HospitalStaffRepository hospitalStaffRepository;

    /**
     * Constructor for PharmacistAccountService with Dependency Injection of the {@code CredentialsService} and the {@code HospitalStaffRepository}
     * @param credentialsService The service responsible for managing user credentials such as passwords and security questions.
     * @param hospitalStaffRepository The repository used for managing pharmacist data in the hospital staff records.
     */
    public PharmacistAccountService(CredentialsService credentialsService,HospitalStaffRepository hospitalStaffRepository) {
        this.credentialsService = credentialsService;
        this.hospitalStaffRepository = hospitalStaffRepository;
    }

    /**
     * Creates a new {@code Pharmacist} in the {@code HospitalStaff} and {@code Credentials} file
     * @param pharmacist The {@code Pharmacist} object containing the pharmacist's information to be added to the system.
     * @param plainTextPassword The plain text password for the pharmacist to be stored in the credentials system.
     * @param securityQuestion The security question set for the pharmacist account for recovery purposes.
     * @param plainTextSecurityAnswer The plain text answer to the security question provided by the pharmacist.
     */
    @Override
    public void createUserAccount(Pharmacist pharmacist, String plainTextPassword, String securityQuestion, String plainTextSecurityAnswer) {
        String userID = pharmacist.getUserID();
        credentialsService.createNewRecord(userID,plainTextPassword,securityQuestion,plainTextSecurityAnswer);
        hospitalStaffRepository.createRecord(userID,pharmacist.getName(),pharmacist.getDomain(),pharmacist.getGender(),pharmacist.getAge());
    }

    /**
     * Reads and return {@code Pharmacist} with matching {@code HospitalID}
     * @param userID The unique identifier (Hospital ID) of the pharmacist whose account information is being retrieved.
     * @return The {@code Pharmacist} object corresponding to the specified {@code userID}.
     */
    public Pharmacist getAccount(String userID) {
        String[] pharmacistParameters = hospitalStaffRepository.readRecord(userID);
        Pharmacist pharmacist = new Pharmacist(pharmacistParameters[0], pharmacistParameters[1], Integer.valueOf(pharmacistParameters[4]), Gender.valueOf(pharmacistParameters[3]), Domain.PHARMACIST);
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
     * @param pharmacist The {@code Pharmacist} object with updated information to be saved to the system.
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
     * @param userID The unique identifier (Hospital ID) of the pharmacist whose password is being verified.
     * @param plainTextPassword The plain text password entered by the pharmacist.
     * @return {@code true} if the entered password matches the one stored for the specified {@code userID}, {@code false} otherwise.
     */
    @Override
    public boolean verifyPassword(String userID, String plainTextPassword) {
        return credentialsService.checkPassword(userID,plainTextPassword);
    }

    /**
     * Verifies if the User has correctly answered their security question
     * @param userID The unique identifier (Hospital ID) of the pharmacist whose security question answer is being verified.
     * @param plainTextSecurityAnswer The plain text answer provided by the pharmacist to the security question.
     * @return {@code true} if the answer matches the one stored in the system, {@code false} otherwise.
     */
    public boolean verifySecurityQuestion(String userID, String plainTextSecurityAnswer){
        return credentialsService.verifySecurityQuestion(userID,plainTextSecurityAnswer);
    }

    /**
     * Returns the remaining logins that the User has before the User is locked out
     * @param userID The unique identifier (Hospital ID) of the pharmacist whose remaining login attempts are being queried.
     * @return The number of login attempts left for the specified user before they are locked out.
     */
    public int getNumberOfTriesLeft(String userID){
        return credentialsService.getNumberOfTriesLeft(userID);
    }

    /**
     * Changes the password of {@code Pharmacist} in the {@code Credentials} file
     * @param userID The unique identifier (Hospital ID) of the pharmacist whose password is being changed.
     * @param newPassword The new password to be set for the pharmacist.
     */
    public void changePassword(String userID, String newPassword){
        credentialsService.changePassword(userID,newPassword);
    }

    /**
     * Increment the number of login attempts of a User based on their {@code HospitalID}
     * @param userID The unique identifier (Hospital ID) of the pharmacist whose login attempts are to be incremented.
     */
    public void incrementNumberOfTries(String userID){
        if(credentialsService.isAccountLocked(userID)){
            return; // Return if the account is already Locked
        }
        credentialsService.incrementLoginAttempts(userID);
    }

    /**
     * Unlocks the account of a User, only to be done by the {@code Administrator}
     * @param userID The unique identifier (Hospital ID) of the pharmacist whose account is being unlocked.
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
