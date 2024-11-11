package Services;

import Entity.Repository.CredentialsRepository;
import Entity.Repository.PatientDataRepository;
import Entity.User.Patient;

public class PatientAccountService {
    private CredentialsService credentialsService;
    private PatientDataRepository patientDataRepository;

    public PatientAccountService(CredentialsService credentialsService, PatientDataRepository patientDataRepository) {
        this.credentialsService = credentialsService;
        this.patientDataRepository = patientDataRepository;
    }

    public void createPatientAccount(Patient patient,String plainTextPassword,String securityQuestion,String plainTextSecurityAnswer){
        String userID = patient.getUserID();
        credentialsService.createNewRecord(userID,plainTextPassword,securityQuestion,plainTextSecurityAnswer);
        patientDataRepository.createRecord(patient);
    }

    // Delete Account
    public void deletePatientAccount(String userID){
        credentialsService.deleteRecord(userID);
        patientDataRepository.deleteRecord(userID);
    }

    // Create new hospital staff class
    // Doctor, pharmacist and admin inherit from this class
    // Refactor Inherit HospitalStaff return super.getID

    // UpdateDetails

    // VerifySecurityQuestion
    // Get remaining logins

    // Change Password via correct securityAnswer

    // Lock account (system)
    // Unlock account after too many attempts (admin)


}
