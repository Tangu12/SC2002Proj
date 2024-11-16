package Application;

import Controllers.AdministratorController;
import Entity.MedicationInventory;
import Entity.Repository.*;
import Entity.User.Administrator;
import Services.*;
import Services.UserAccount.*;

public class ApplicationContext {

    private String appointmentsRepositoryPath;
    private String credentialsRepositoryPath;
    private String hospitalStaffRepositoryPath;
    private String medicalRecordRepositoryPath;
    private String medicationInventoryRepositoryPath;
    private String patientDataRepositoryPath;

    public ApplicationContext(String appointmentsRepositoryPath,String credentialsRepositoryPath
            ,String hospitalStaffRepositoryPath,String medicalRecordRepositoryPath,String medicationInventoryRepositoryPath
            ,String patientDataRepositoryPath) {

        this.appointmentsRepositoryPath = appointmentsRepositoryPath;
        this.credentialsRepositoryPath = credentialsRepositoryPath;
        this.hospitalStaffRepositoryPath = hospitalStaffRepositoryPath;
        this.medicalRecordRepositoryPath = medicalRecordRepositoryPath;
        this.medicationInventoryRepositoryPath = medicationInventoryRepositoryPath;
        this.patientDataRepositoryPath = patientDataRepositoryPath;

        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // Create Entities
        // Create Repository
        AppointmentsRepository appointmentsRepository= new AppointmentsRepository(appointmentsRepositoryPath);
        CredentialsRepository credentialsRepository= new CredentialsRepository(credentialsRepositoryPath);
        HospitalStaffRepository hospitalStaffRepository= new HospitalStaffRepository(hospitalStaffRepositoryPath);
        MedicalRecordRepository medicalRecordRepository = new MedicalRecordRepository(medicalRecordRepositoryPath);
        MedicationInventoryRepository medicationInventoryRepository = new MedicationInventoryRepository(medicationInventoryRepositoryPath);
        PatientDataRepository patientDataRepository = new PatientDataRepository(patientDataRepositoryPath);
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        // Create Services
        AppointmentService appointmentService = new AppointmentService(appointmentsRepository);
        CredentialsService credentialsService = new CredentialsService(credentialsRepository);
        ForgotPasswordService forgotPasswordService = new ForgotPasswordService(credentialsService);
        MedicalInventoryService medicalInventoryService = new MedicalInventoryService(MedicationInventory.getInstance(),medicationInventoryRepository);
        MedicalRecordService medicalRecordService = new MedicalRecordService(appointmentsRepository);

        // User Account Services
        PatientAccountService patientAccountService = new PatientAccountService(credentialsService,patientDataRepository);
        DoctorAccountService doctorAccountService = new DoctorAccountService(credentialsService,hospitalStaffRepository);
        PharmacistAccountService pharmacistAccountService = new PharmacistAccountService(credentialsService,hospitalStaffRepository);
        AdministratorAccountService administratorAccountService = new AdministratorAccountService(credentialsService,hospitalStaffRepository);

        HospitalStaffRegistrationService hospitalStaffRegistrationService = new HospitalStaffRegistrationService(credentialsRepository,doctorAccountService,pharmacistAccountService,administratorAccountService);

        AccountManager accountManager = new AccountManager(patientAccountService,doctorAccountService,pharmacistAccountService,administratorAccountService,credentialsService);
        StaffManagementService staffManagementService = new StaffManagementService(doctorAccountService,administratorAccountService,pharmacistAccountService,hospitalStaffRegistrationService);
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    }



}