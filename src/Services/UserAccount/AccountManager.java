package Services.UserAccount;

import Entity.User.Administrator;
import Entity.User.Doctor;
import Entity.User.Patient;
import Entity.User.Pharmacist;

public class AccountManager {
    private IUserAccountService<Patient> patientService;   // Service for Patient
    private IUserAccountService<Doctor> doctorService;
    private IUserAccountService<Pharmacist> pharmacistService;
    private IUserAccountService<Administrator> administratorService;

    public AccountManager(IUserAccountService<Patient> patientService, IUserAccountService<Doctor> doctorService,IUserAccountService<Pharmacist> pharmacistService,IUserAccountService<Administrator> administratorService) {
        this.patientService = patientService;
        this.doctorService = doctorService;
        this.pharmacistService = pharmacistService;
        this.administratorService = administratorService;
    }

    public void addUser(Object user, String plainTextPassword, String securityQuestion, String plainTextSecurityAnswer) {
        if (user instanceof Patient) {
            patientService.createUserAccount((Patient) user, plainTextPassword, securityQuestion, plainTextSecurityAnswer);
        } else if (user instanceof Doctor) {
            doctorService.createUserAccount((Doctor) user, plainTextPassword, securityQuestion, plainTextSecurityAnswer);
        } else if (user instanceof Pharmacist) {
            pharmacistService.createUserAccount((Pharmacist) user, plainTextPassword, securityQuestion, plainTextSecurityAnswer);
        } else if (user instanceof Administrator) {
            administratorService.createUserAccount((Administrator) user, plainTextPassword, securityQuestion, plainTextSecurityAnswer);
        } else {
            System.out.println("Error!! User Type not supported.");
        }
    }

    public void updateUserInfo(Object user) {
        if (user instanceof Patient) {
            patientService.updateUserData((Patient) user);
        } else if (user instanceof Doctor) {
            doctorService.updateUserData((Doctor) user);
        } else if (user instanceof Pharmacist) {
            pharmacistService.updateUserData((Pharmacist) user);
        } else if (user instanceof Administrator) {
            administratorService.updateUserData((Administrator) user);
        } else {
            System.out.println("Error!! User Type not supported.");
        }
    }

    public void deleteUser(String userID, Object userType) {
        if (userType instanceof Patient) {
            patientService.deleteUserAccount(userID);
        } else if (userType instanceof Doctor) {
            doctorService.deleteUserAccount(userID);
        } else if (userType instanceof Pharmacist) {
            pharmacistService.deleteUserAccount(userID);
        } else if (userType instanceof Administrator) {
            administratorService.deleteUserAccount(userID);
        } else {
            System.out.println("Error!! User Type not supported.");
        }
    }

}
