package Services.UserAccount;

import Entity.User.*;
import java.util.ArrayList;
import Services.CredentialsService;

/**
 * {@code AccountManager} which contains all the different types of account services
 */
public class AccountManager {
    private IUserAccountService<Patient> patientAccountService;   // Service for Patient
    private IUserAccountService<Doctor> doctorAccountService;
    private IUserAccountService<Pharmacist> pharmacistAccountService;
    private IUserAccountService<Administrator> administratorAccountService;
    private CredentialsService credentialsService;

    /**
     * Constructor for {@code AccountManager}
     * @param patientAccountService Service responsible for handling {@code Patient} accounts.
     * @param doctorAccountService Service responsible for handling {@code Doctor} accounts.
     * @param pharmacistAccountService Service responsible for handling {@code Pharmacist} accounts.
     * @param administratorAccountService Service responsible for handling {@code Administrator} accounts.
     * @param credentialsService Service responsible for handling user credentials and authentication.
     */
    public AccountManager(IUserAccountService<Patient> patientAccountService, IUserAccountService<Doctor> doctorAccountService, IUserAccountService<Pharmacist> pharmacistAccountService, IUserAccountService<Administrator> administratorAccountService, CredentialsService credentialsService) {
        this.patientAccountService = patientAccountService;
        this.doctorAccountService = doctorAccountService;
        this.pharmacistAccountService = pharmacistAccountService;
        this.administratorAccountService = administratorAccountService;
        this.credentialsService = credentialsService;
    }

    /**
     * Adds a new User to the Hospital based on the domain of the new User
     * @param user The user object (could be of type {@code Patient}, {@code Doctor}, {@code Pharmacist}, or {@code Administrator}).
     * @param plainTextPassword The user's password as plain text.
     * @param securityQuestion The security question for the user to answer during recovery or verification.
     * @param plainTextSecurityAnswer The answer to the security question in plain text.
     */
    public void addUser(Object user, String plainTextPassword, String securityQuestion, String plainTextSecurityAnswer) {
        if (user instanceof Patient) {
            patientAccountService.createUserAccount((Patient) user, plainTextPassword, securityQuestion, plainTextSecurityAnswer);
        } else if (user instanceof Doctor) {
            doctorAccountService.createUserAccount((Doctor) user, plainTextPassword, securityQuestion, plainTextSecurityAnswer);
        } else if (user instanceof Pharmacist) {
            pharmacistAccountService.createUserAccount((Pharmacist) user, plainTextPassword, securityQuestion, plainTextSecurityAnswer);
        } else if (user instanceof Administrator) {
            administratorAccountService.createUserAccount((Administrator) user, plainTextPassword, securityQuestion, plainTextSecurityAnswer);
        } else {
            System.out.println("Error!! User Type not supported.");
        }
    }

    /**
     * Reads and returns the domain of a User based on their {@code HospitalID}
     * @param userID The unique identifier for the user, typically in the format that indicates the user's role (e.g., 'P' for Patient, 'D' for Doctor).
     * @return The user object corresponding to the given {@code userID}, or {@code null} if no user is found.
     */
    public IUser readUser(String userID) {
        String role = String.valueOf(userID.charAt(0));
        switch (role) {
            case "P":
                Patient patient = patientAccountService.getAccount(userID);
                return patient;
            case "D":
                Doctor doctor = doctorAccountService.getAccount(userID);
                return doctor;
            case "R":
                Pharmacist pharmacist = pharmacistAccountService.getAccount(userID);
                return pharmacist;
            case "A":
                Administrator administrator = administratorAccountService.getAccount(userID);
                return administrator;
            default:
                System.out.println("Error!! User Type not supported.");
                return null;
        }
    }

    /**
     * Updates a User's information based on their domain
     * @param user The user object to update (could be of type {@code Patient}, {@code Doctor}, {@code Pharmacist}, or {@code Administrator}).
     */
    public void updateUserInfo(Object user) {
        if (user instanceof Patient) {
            patientAccountService.updateUserData((Patient) user);
        } else if (user instanceof Doctor) {
            doctorAccountService.updateUserData((Doctor) user);
        } else if (user instanceof Pharmacist) {
            pharmacistAccountService.updateUserData((Pharmacist) user);
        } else if (user instanceof Administrator) {
            administratorAccountService.updateUserData((Administrator) user);
        } else {
            System.out.println("Error!! User Type not supported.");
        }
    }

    /**
     * Gets the domain of the User and calls the resepctive functions to delete the User
     */
    public void deleteUser(String userID, Object userType) {
        if (userType instanceof Patient) {
            patientAccountService.deleteUserAccount(userID);
        } else if (userType instanceof Doctor) {
            doctorAccountService.deleteUserAccount(userID);
        } else if (userType instanceof Pharmacist) {
            pharmacistAccountService.deleteUserAccount(userID);
        } else if (userType instanceof Administrator) {
            administratorAccountService.deleteUserAccount(userID);
        } else {
            System.out.println("Error!! User Type not supported.");
        }
    }

    /**
     * Function to get the {@code HospitalID} of all the User's that have their account locked
     * @return Array list of the {@code HospitalID} of all the User's that have their account locked
     */
    public ArrayList<String> getAllLockedUserIDs() {
        ArrayList<String> lockedAccounts = new ArrayList<>();

        ArrayList<String> allUserIDs = credentialsService.getAllUserIDs();

        for(String userID : allUserIDs){
            if(credentialsService.isAccountLocked(userID)){
                lockedAccounts.add(userID);
            }
        }

        return lockedAccounts;
    }

    /**
     * Function to get the {@code HospitalID} of all the User's in the Hospital
     * @return Array list of the {@code HospitalID} of all the User's in the Hospital
     */
    public ArrayList<String> getAllUserIDs() {
        return credentialsService.getAllUserIDs();
    }

    public ArrayList<String> getAllUnlockedUserIDs() {
        ArrayList<String> unlockedAccounts = new ArrayList<>();
        ArrayList<String> allUserIDs = credentialsService.getAllUserIDs();
        for(String userID : allUserIDs){
            if(!credentialsService.isAccountLocked(userID)){
                unlockedAccounts.add(userID);
            }
        }
        return unlockedAccounts;
    }

    /**
     * Locks the account of a User by setting the number log in of attempts to -1
     * @param userID The unique identifier of the user whose account is to be locked.
     */
    public void lockAccount(String userID) {
        credentialsService.lockAccount(userID);
    }

    /**
     * Unlocks the account of a User by setting the number log in of attempts to 0
     * @param userID The unique identifier of the user whose account is to be unlocked.
     */
    public void unlockAccount(String userID){
        credentialsService.unlockAccount(userID);
    }
}
