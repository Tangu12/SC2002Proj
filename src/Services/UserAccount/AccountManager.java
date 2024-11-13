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

    public void addUser(){

    }
    public void updateUser(){

    }
    public void deleteUser(){

    }
    public void loginUser(){

    }


}
