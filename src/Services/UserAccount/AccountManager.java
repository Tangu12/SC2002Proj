package Services.UserAccount;

import Entity.User.*;

public class AccountManager {
    private IUserAccountService<Patient> patientAccountService;   // Service for Patient
    private IUserAccountService<Doctor> doctorAccountService;
    private IUserAccountService<Pharmacist> pharmacistAccountService;
    private IUserAccountService<Administrator> administratorAccountService;

    public AccountManager(IUserAccountService<Patient> patientAccountService, IUserAccountService<Doctor> doctorAccountService, IUserAccountService<Pharmacist> pharmacistAccountService, IUserAccountService<Administrator> administratorAccountService) {
        this.patientAccountService = patientAccountService;
        this.doctorAccountService = doctorAccountService;
        this.pharmacistAccountService = pharmacistAccountService;
        this.administratorAccountService = administratorAccountService;
    }

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

}
