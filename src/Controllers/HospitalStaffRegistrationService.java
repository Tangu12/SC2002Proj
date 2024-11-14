package Controllers;

import Entity.Enums.Domain;
import Entity.Repository.CredentialsRepository;
import Entity.User.*;
import Services.UserAccount.AdministratorAccountService;
import Services.UserAccount.DoctorAccountService;
import Services.UserAccount.PharmacistAccountService;

public class HospitalStaffRegistrationService {
    private CredentialsRepository credentialsRepository;
    private DoctorAccountService doctorAccountService;
    private PharmacistAccountService pharmacistAccountService;
    private AdministratorAccountService administratorAccountService;

    public HospitalStaffRegistrationService(CredentialsRepository credentialsRepository, DoctorAccountService doctorAccountService
                        , PharmacistAccountService pharmacistAccountService, AdministratorAccountService administratorAccountService) {
        this.credentialsRepository = credentialsRepository;
        this.doctorAccountService = doctorAccountService;
        this.pharmacistAccountService = pharmacistAccountService;
        this.administratorAccountService = administratorAccountService;
    }

    /*
    Looks through the file to find the number of users and creates a username based on the latest user.(eg if last user in file is P002, function returns P003)
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

    // Helper method
    private String getIdFormat() {
        int digits = String.valueOf(IUser.MAX_USERS).length();
        return "%s%0" + digits + "d";  // This creates format like "%s%04d" for 9999
    }

    /*
    Calls doctorAccountService.createUserAccount to create a new account for the doctor
    */
    public void registerDoctorAccount(Doctor doctor,String plainTextPassword, String securityQuestion, String plainTextSecurityAnswer){
        doctorAccountService.createUserAccount(doctor,plainTextPassword,securityQuestion,plainTextSecurityAnswer);
    }

    /*
    Calls pharmacistAccountService.createUserAccount to create a new account for the pharmacist
    */
    public void registerPharmacistAccount(Pharmacist pharmacist,String plainTextPassword, String securityQuestion, String plainTextSecurityAnswer){
        pharmacistAccountService.createUserAccount(pharmacist, plainTextPassword,securityQuestion,plainTextSecurityAnswer);
    }

    /*
    Calls administratorAccountService.createUserAccount to create a new account for the administrator
    */
    public void registerAdministratorAccount(Administrator administrator,String plainTextPassword, String securityQuestion, String plainTextSecurityAnswer){
        administratorAccountService.createUserAccount(administrator, plainTextPassword,securityQuestion,plainTextSecurityAnswer);
    }



}
