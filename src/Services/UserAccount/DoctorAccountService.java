package Services.UserAccount;

import Entity.Enums.Department;
import Entity.Enums.Domain;
import Entity.Enums.Gender;
import Entity.Repository.HospitalStaffRepository;
import Entity.User.Doctor;
import Entity.User.Patient;
import Services.CredentialsService;

/**
 * {@code DoctorAccountService} which inherits the {@code IUserAccountService} interface
 */
public class DoctorAccountService implements IUserAccountService<Doctor> {
    private CredentialsService credentialsService;
    private HospitalStaffRepository hospitalStaffRepository;

    /**
     * Constructor for {@code DoctorAccountService} with Dependency Injection of the {@code CredentialsService} and the {@code HospitalStaffRepository}
     * @param credentialsService The service responsible for handling user credentials and authentication.
     * @param hospitalStaffRepository The repository responsible for storing and accessing hospital staff data.
     */
    public DoctorAccountService(CredentialsService credentialsService,HospitalStaffRepository hospitalStaffRepository) {
        this.credentialsService = credentialsService;
        this.hospitalStaffRepository = hospitalStaffRepository;
    }


    /**
     * Creates a new {@code Doctor} in the {@code HospitalStaff} and {@code Credentials} file
     * @param doctor The {@code Doctor} object containing the doctor's information to be added.
     * @param plainTextPassword The plain text password for the {@code Doctor}.
     * @param securityQuestion The security question for the doctor to be used for account recovery or verification.
     * @param plainTextSecurityAnswer The plain text answer to the security question.
     */
    @Override
    public void createUserAccount(Doctor doctor, String plainTextPassword, String securityQuestion, String plainTextSecurityAnswer) {
        String userID = doctor.getUserID();
        credentialsService.createNewRecord(userID,plainTextPassword,securityQuestion,plainTextSecurityAnswer);
        hospitalStaffRepository.createRecord(userID,doctor.getName(),doctor.getDepartment(),doctor.getGender(),doctor.getAge());
    }

    /**
     * Reads and return {@code Doctor} with matching {@code HospitalID}
     * @param userID The unique identifier (hospital ID) for the {@code Doctor} to be fetched.
     * @return The {@code Doctor} with the matching {@code HospitalID}.
     */
    public Doctor getAccount(String userID) {
        String[] doctorParameters = hospitalStaffRepository.readRecord(userID);

        String id = doctorParameters[0];
        String name = doctorParameters[1];
        Department department = Department.valueOf(doctorParameters[2]);
        Gender gender = Gender.valueOf(doctorParameters[3]);
        int age = Integer.valueOf(doctorParameters[4]);

        Doctor doctor = new Doctor(id,name,age,gender,department);
        return doctor;
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
     * @param doctor The {@code Doctor} object with updated information to be saved.
     */
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

    /**
     * Verifies a User has entered their correct password
     * @param userID The unique identifier (hospital ID) of the user whose password is being verified.
     * @param plainTextPassword The plain text password entered by the user.
     * @return {@code true} if the entered password matches the one stored for the user, {@code false} otherwise.
     */
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
     * Changes the password of {@code Doctor} in the {@code Credentials} file
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

    /**
     * Loads the list of {@code Doctor} from the {@code HospitalStaff} file
     */
    public void loadDoctorList(){
        hospitalStaffRepository.loadDoctorList();
    }
}
