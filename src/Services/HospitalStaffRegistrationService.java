package Services;

import Entity.Enums.Domain;
import Entity.Repository.CredentialsRepository;
import Entity.User.*;
import Services.UserAccount.AdministratorAccountService;
import Services.UserAccount.DoctorAccountService;
import Services.UserAccount.PharmacistAccountService;

/**
 * {@code HospitalStaffRegistrationService} class which handles all the logic dealing with the registration of new {@code HospitalStaff}
 */
public class HospitalStaffRegistrationService {
    private CredentialsRepository credentialsRepository;
    private DoctorAccountService doctorAccountService;
    private PharmacistAccountService pharmacistAccountService;
    private AdministratorAccountService administratorAccountService;

    /**
     * Constructor for {@code HospitalStaffRegistrationService}
     */
    public HospitalStaffRegistrationService(CredentialsRepository credentialsRepository, DoctorAccountService doctorAccountService
                        , PharmacistAccountService pharmacistAccountService, AdministratorAccountService administratorAccountService) {
        this.credentialsRepository = credentialsRepository;
        this.doctorAccountService = doctorAccountService;
        this.pharmacistAccountService = pharmacistAccountService;
        this.administratorAccountService = administratorAccountService;
    }

    /**
     * Looks through the file to find the number of users and creates a {@code HospitalID} based on the latest User.(eg if last user in file is P002, function returns P003)
     * @param domain The domain type representing the type of user to register (e.g., DOCTOR, PATIENT, PHARMACIST, ADMINISTRATOR).
     * @return A formatted user ID based on the domain and current user count, or {@code null} if the maximum number of users for that domain has been reached.
     */
    public String getUserName(Domain domain){
        int userCount = credentialsRepository.countUsersByType(domain);
        String prefix = null;

        if(userCount < IUser.MAX_USERS){
            switch (domain) {
                case DOCTOR -> prefix = IUser.DOCTOR_PREFIX;
                case PATIENT -> prefix = IUser.PATIENT_PREFIX;
                case PHARMACIST -> prefix = IUser.PHARMACIST_PREFIX;
                case ADMINISTRATOR -> prefix = IUser.ADMIN_PREFIX;
                default -> {
                    System.out.println("Invalid domain specified.");
                    return null;
                }
            }
            return String.format(getIdFormat(), prefix, userCount + 1);
        }
        else{
            switch(domain) {
                case DOCTOR -> prefix = IUser.DOCTOR_PREFIX;
                case PHARMACIST -> prefix = IUser.PHARMACIST_PREFIX;
                case ADMINISTRATOR -> prefix = IUser.ADMIN_PREFIX;
                default -> prefix = null;
            }

            System.out.println("Maximum User ID ("+prefix + IUser.MAX_USERS + ") reached");
            return null;
        }
    }

    /**
     * Helper method
     * @return The format string for user ID, like "%s%04d" for 9999 users.
     */
    private String getIdFormat() {
        int digits = String.valueOf(IUser.MAX_USERS).length();
        return "%s%0" + digits + "d";  // This creates format like "%s%04d" for 9999
    }

    /**
     * Creates an account for a new {@code Doctor} using the {@code DoctorAccountService} class
     * @param doctor The {@code Doctor} object representing the doctor to register.
     * @param plainTextPassword The plain text password to be used for the doctor's account.
     * @param securityQuestion The security question for account recovery.
     * @param plainTextSecurityAnswer The plain text answer to the security question.
     */
    public void registerDoctorAccount(Doctor doctor,String plainTextPassword, String securityQuestion, String plainTextSecurityAnswer){
        doctorAccountService.createUserAccount(doctor,plainTextPassword,securityQuestion,plainTextSecurityAnswer);
    }


    /**
     * Create an account for a new {@code Pharmacist} using the {@code PharmacistAccountService} class
     * @param pharmacist The {@code Pharmacist} object representing the pharmacist to register.
     * @param plainTextPassword The plain text password to be used for the pharmacist's account.
     * @param securityQuestion The security question for account recovery.
     * @param plainTextSecurityAnswer The plain text answer to the security question.
     */

    public void registerPharmacistAccount(Pharmacist pharmacist,String plainTextPassword, String securityQuestion, String plainTextSecurityAnswer){
        pharmacistAccountService.createUserAccount(pharmacist, plainTextPassword,securityQuestion,plainTextSecurityAnswer);
    }

    /**
     * Create an account for a new {@code Administrator} using the {@code AdministratorAccountService} class
     @param administrator The {@code Administrator} object representing the administrator to register.
      * @param plainTextPassword The plain text password to be used for the administrator's account.
     * @param securityQuestion The security question for account recovery.
     * @param plainTextSecurityAnswer The plain text answer to the security question.
     */
    public void registerAdministratorAccount(Administrator administrator,String plainTextPassword, String securityQuestion, String plainTextSecurityAnswer){
        administratorAccountService.createUserAccount(administrator, plainTextPassword,securityQuestion,plainTextSecurityAnswer);
    }
}
