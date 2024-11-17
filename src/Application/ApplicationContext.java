package Application;

import Entity.MedicationInventory;
import Entity.Repository.*;
import Services.*;
import Services.UserAccount.*;

public class ApplicationContext {

    private String appointmentsRepositoryPath;
    private String credentialsRepositoryPath;
    private String hospitalStaffRepositoryPath;
    private String medicalRecordRepositoryPath;
    private String medicationInventoryRepositoryPath;
    private String patientDataRepositoryPath;
    
    private AppointmentsRepository appointmentsRepository;
    private CredentialsRepository credentialsRepository;
    private HospitalStaffRepository hospitalStaffRepository;
    private MedicalRecordRepository medicalRecordRepository;
    private MedicationInventoryRepository medicationInventoryRepository;
    private PatientDataRepository patientDataRepository;
    
    private AppointmentService appointmentService;
    private CredentialsService credentialsService;
    private ForgotPasswordService forgotPasswordService;
    private MedicalInventoryService medicalInventoryService;
    //private MedicalRecordService medicalRecordService;
    
    private PatientAccountService patientAccountService;
    private DoctorAccountService doctorAccountService;
    private PharmacistAccountService pharmacistAccountService;
    private AdministratorAccountService administratorAccountService;
    
    private HospitalStaffRegistrationService hospitalStaffRegistrationService;

    private AccountManager accountManager;
    private StaffManagementService staffManagementService;

    public ApplicationContext(String appointmentsRepositoryPath,String credentialsRepositoryPath
            ,String hospitalStaffRepositoryPath,String medicationInventoryRepositoryPath
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
        this.appointmentsRepository= new AppointmentsRepository(appointmentsRepositoryPath);
        this.credentialsRepository= new CredentialsRepository(credentialsRepositoryPath);
        this.hospitalStaffRepository= new HospitalStaffRepository(hospitalStaffRepositoryPath);
        this.medicalRecordRepository = new MedicalRecordRepository(medicalRecordRepositoryPath);
        this.medicationInventoryRepository = new MedicationInventoryRepository(medicationInventoryRepositoryPath);
        this.patientDataRepository = new PatientDataRepository(patientDataRepositoryPath);
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        // Create Services
        this.appointmentService = new AppointmentService(appointmentsRepository);
        this.credentialsService = new CredentialsService(credentialsRepository);
        this.forgotPasswordService = new ForgotPasswordService(credentialsService);
        this.medicalInventoryService = new MedicalInventoryService(MedicationInventory.getInstance(),medicationInventoryRepository);
        //this.medicalRecordService = new MedicalRecordService(appointmentsRepository);

        // User Account Services
        this.patientAccountService = new PatientAccountService(credentialsService,patientDataRepository);
        this.doctorAccountService = new DoctorAccountService(credentialsService,hospitalStaffRepository);
        this.pharmacistAccountService = new PharmacistAccountService(credentialsService,hospitalStaffRepository);
        this.administratorAccountService = new AdministratorAccountService(credentialsService,hospitalStaffRepository);

        this.hospitalStaffRegistrationService = new HospitalStaffRegistrationService(credentialsRepository,doctorAccountService,pharmacistAccountService,administratorAccountService);

        this.accountManager = new AccountManager(patientAccountService,doctorAccountService,pharmacistAccountService,administratorAccountService,credentialsService);
        this.staffManagementService = new StaffManagementService(doctorAccountService,administratorAccountService,pharmacistAccountService,hospitalStaffRegistrationService);
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    }

	public String getAppointmentsRepositoryPath() {
		return appointmentsRepositoryPath;
	}

	public void setAppointmentsRepositoryPath(String appointmentsRepositoryPath) {
		this.appointmentsRepositoryPath = appointmentsRepositoryPath;
	}

	public String getCredentialsRepositoryPath() {
		return credentialsRepositoryPath;
	}

	public void setCredentialsRepositoryPath(String credentialsRepositoryPath) {
		this.credentialsRepositoryPath = credentialsRepositoryPath;
	}

	public String getHospitalStaffRepositoryPath() {
		return hospitalStaffRepositoryPath;
	}

	public void setHospitalStaffRepositoryPath(String hospitalStaffRepositoryPath) {
		this.hospitalStaffRepositoryPath = hospitalStaffRepositoryPath;
	}

	public String getMedicalRecordRepositoryPath() {
		return medicalRecordRepositoryPath;
	}

	public void setMedicalRecordRepositoryPath(String medicalRecordRepositoryPath) {
		this.medicalRecordRepositoryPath = medicalRecordRepositoryPath;
	}

	public String getMedicationInventoryRepositoryPath() {
		return medicationInventoryRepositoryPath;
	}

	public void setMedicationInventoryRepositoryPath(String medicationInventoryRepositoryPath) {
		this.medicationInventoryRepositoryPath = medicationInventoryRepositoryPath;
	}

	public String getPatientDataRepositoryPath() {
		return patientDataRepositoryPath;
	}

	public void setPatientDataRepositoryPath(String patientDataRepositoryPath) {
		this.patientDataRepositoryPath = patientDataRepositoryPath;
	}

	public AppointmentsRepository getAppointmentsRepository() {
		return appointmentsRepository;
	}

	public void setAppointmentsRepository(AppointmentsRepository appointmentsRepository) {
		this.appointmentsRepository = appointmentsRepository;
	}

	public CredentialsRepository getCredentialsRepository() {
		return credentialsRepository;
	}

	public void setCredentialsRepository(CredentialsRepository credentialsRepository) {
		this.credentialsRepository = credentialsRepository;
	}

	public HospitalStaffRepository getHospitalStaffRepository() {
		return hospitalStaffRepository;
	}

	public void setHospitalStaffRepository(HospitalStaffRepository hospitalStaffRepository) {
		this.hospitalStaffRepository = hospitalStaffRepository;
	}

	public MedicalRecordRepository getMedicalRecordRepository() {
		return medicalRecordRepository;
	}

	public void setMedicalRecordRepository(MedicalRecordRepository medicalRecordRepository) {
		this.medicalRecordRepository = medicalRecordRepository;
	}

	public MedicationInventoryRepository getMedicationInventoryRepository() {
		return medicationInventoryRepository;
	}

	public void setMedicationInventoryRepository(MedicationInventoryRepository medicationInventoryRepository) {
		this.medicationInventoryRepository = medicationInventoryRepository;
	}

	public PatientDataRepository getPatientDataRepository() {
		return patientDataRepository;
	}

	public void setPatientDataRepository(PatientDataRepository patientDataRepository) {
		this.patientDataRepository = patientDataRepository;
	}

	public AppointmentService getAppointmentService() {
		return appointmentService;
	}

	public void setAppointmentService(AppointmentService appointmentServic) {
		this.appointmentService = appointmentServic;
	}

	public CredentialsService getCredentialsService() {
		return credentialsService;
	}

	public void setCredentialsService(CredentialsService credentialsService) {
		this.credentialsService = credentialsService;
	}

	public ForgotPasswordService getForgotPasswordService() {
		return forgotPasswordService;
	}

	public void setForgotPasswordService(ForgotPasswordService forgotPasswordService) {
		this.forgotPasswordService = forgotPasswordService;
	}

	public MedicalInventoryService getMedicalInventoryService() {
		return medicalInventoryService;
	}

	public void setMedicalInventoryService(MedicalInventoryService medicalInventoryService) {
		this.medicalInventoryService = medicalInventoryService;
	}

//	public MedicalRecordService getMedicalRecordService() {
//		return medicalRecordService;
//	}
//
//	public void setMedicalRecordService(MedicalRecordService medicalRecordService) {
//		this.medicalRecordService = medicalRecordService;
//	}

	public PatientAccountService getPatientAccountService() {
		return patientAccountService;
	}

	public void setPatientAccountService(PatientAccountService patientAccountService) {
		this.patientAccountService = patientAccountService;
	}

	public DoctorAccountService getDoctorAccountService() {
		return doctorAccountService;
	}

	public void setDoctorAccountService(DoctorAccountService doctorAccountService) {
		this.doctorAccountService = doctorAccountService;
	}

	public PharmacistAccountService getPharmacistAccountService() {
		return pharmacistAccountService;
	}

	public void setPharmacistAccountService(PharmacistAccountService pharmacistAccountService) {
		this.pharmacistAccountService = pharmacistAccountService;
	}

	public AdministratorAccountService getAdministratorAccountService() {
		return administratorAccountService;
	}

	public void setAdministratorAccountService(AdministratorAccountService administratorAccountService) {
		this.administratorAccountService = administratorAccountService;
	}

	public HospitalStaffRegistrationService getHospitalStaffRegistrationService() {
		return hospitalStaffRegistrationService;
	}

	public void setHospitalStaffRegistrationService(HospitalStaffRegistrationService hospitalStaffRegistrationService) {
		this.hospitalStaffRegistrationService = hospitalStaffRegistrationService;
	}

	public AccountManager getAccountManager() {
		return accountManager;
	}

	public void setAccountManager(AccountManager accountManager) {
		this.accountManager = accountManager;
	}

	public StaffManagementService getStaffManagementService() {
		return staffManagementService;
	}

	public void setStaffManagementService(StaffManagementService staffManagementService) {
		this.staffManagementService = staffManagementService;
	}
}