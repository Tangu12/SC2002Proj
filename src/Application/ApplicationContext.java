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

	/**
	 * Constructor for {@code ApplicationContext}
	 *
	 * @param appointmentsRepositoryPath The file path for the appointments repository.
	 * @param credentialsRepositoryPath The file path for the credentials repository.
	 * @param hospitalStaffRepositoryPath The file path for the hospital staff repository.
	 * @param medicationInventoryRepositoryPath The file path for the medication inventory repository.
	 * @param patientDataRepositoryPath The file path for the patient data repository.
	 */
	public ApplicationContext(String appointmentsRepositoryPath, String credentialsRepositoryPath,
							  String hospitalStaffRepositoryPath, String medicationInventoryRepositoryPath,
							  String patientDataRepositoryPath) {

		this.appointmentsRepositoryPath = appointmentsRepositoryPath;
		this.credentialsRepositoryPath = credentialsRepositoryPath;
		this.hospitalStaffRepositoryPath = hospitalStaffRepositoryPath;
		this.medicationInventoryRepositoryPath = medicationInventoryRepositoryPath;
		this.patientDataRepositoryPath = patientDataRepositoryPath;

		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// Create Entities
		// Create Repository
		this.appointmentsRepository= new AppointmentsRepository(appointmentsRepositoryPath);
		this.credentialsRepository= new CredentialsRepository(credentialsRepositoryPath);
		this.hospitalStaffRepository= new HospitalStaffRepository(hospitalStaffRepositoryPath);
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

	/**
	 * The getter method of the path of the {@code AppointmentRepository}
	 * @return The path of the {@code AppointmentRepository}
	 */
	public String getAppointmentsRepositoryPath() {
		return appointmentsRepositoryPath;
	}

	/**
	 * The setter method of the path of the {@code AppointmentRepository}
	 * @param appointmentsRepositoryPath The file path to set for the appointments repository.
	 */
	public void setAppointmentsRepositoryPath(String appointmentsRepositoryPath) {
		this.appointmentsRepositoryPath = appointmentsRepositoryPath;
	}

	/**
	 * The getter method of the path of the {@code CredentialsRepository}
	 * @return The path of the {@code CredentialsRepository}
	 */
	public String getCredentialsRepositoryPath() {
		return credentialsRepositoryPath;
	}

	/**
	 * The setter method of the path of the {@code CredentialsRepository}
	 * @param credentialsRepositoryPath The file path to set for the credentials repository.
	 */
	public void setCredentialsRepositoryPath(String credentialsRepositoryPath) {
		this.credentialsRepositoryPath = credentialsRepositoryPath;
	}

	/**
	 * The getter method of the path of the {@code HospitalStaffRepository}
	 * @return The path of the {@code HospitalStaffRepository}
	 */
	public String getHospitalStaffRepositoryPath() {
		return hospitalStaffRepositoryPath;
	}

	/**
	 * The setter method of the path of the {@code HospitalStaffRepository}
	 * @param hospitalStaffRepositoryPath The file path to set for the hospital staff repository.
	 */
	public void setHospitalStaffRepositoryPath(String hospitalStaffRepositoryPath) {
		this.hospitalStaffRepositoryPath = hospitalStaffRepositoryPath;
	}

	/**
	 * The getter method of the path of the {@code MedicalRecordRepository}
	 * @return The path of the {@code MedicalRecordRepository}
	 */
	public String getMedicalRecordRepositoryPath() {
		return medicalRecordRepositoryPath;
	}

	/**
	 * The setter method of the path of the {@code MedicalRecordRepository}
	 * @param medicalRecordRepositoryPath The file path to set for the medical record repository.
	 */
	public void setMedicalRecordRepositoryPath(String medicalRecordRepositoryPath) {
		this.medicalRecordRepositoryPath = medicalRecordRepositoryPath;
	}

	/**
	 * The getter method of the path of the {@code MedicationInventoryRepository}
	 * @return The path of the {@code MedicationInventoryRepository}
	 */
	public String getMedicationInventoryRepositoryPath() {
		return medicationInventoryRepositoryPath;
	}

	/**
	 * The setter method of the path of the {@code MedicationInventoryRepository}
	 * @param medicationInventoryRepositoryPath The file path to set for the medication inventory repository.
	 */
	public void setMedicationInventoryRepositoryPath(String medicationInventoryRepositoryPath) {
		this.medicationInventoryRepositoryPath = medicationInventoryRepositoryPath;
	}

	/**
	 * The getter method of the path of the {@code PatientDataRepository}
	 * @return The path of the {@code PatientDataRepository}
	 */
	public String getPatientDataRepositoryPath() {
		return patientDataRepositoryPath;
	}

	/**
	 * The setter method of the path of the {@code PatientDataRepository}
	 * @param patientDataRepositoryPath The file path to set for the patient data repository.
	 */
	public void setPatientDataRepositoryPath(String patientDataRepositoryPath) {
		this.patientDataRepositoryPath = patientDataRepositoryPath;
	}

	/**
	 * Gets the {@code AppointmentsRepository} instance
	 * @return The instance of {@code AppointmentsRepository}
	 */
	public AppointmentsRepository getAppointmentsRepository() {
		return appointmentsRepository;
	}

	/**
	 * Sets the {@code AppointmentRepository} instance
	 * @param appointmentsRepository The instance to set for the appointments repository.
	 */
	public void setAppointmentsRepository(AppointmentsRepository appointmentsRepository) {
		this.appointmentsRepository = appointmentsRepository;
	}

	/**
	 * Gets the {@code CredentialsRepository} instance
	 * @return The instance of {@code CredentialsRepository}
	 */
	public CredentialsRepository getCredentialsRepository() {
		return credentialsRepository;
	}

	/**
	 * Sets the {@code CredentialsService} instance
	 * @param credentialsRepository The instance to set for the credentials repository.
	 */
	public void setCredentialsRepository(CredentialsRepository credentialsRepository) {
		this.credentialsRepository = credentialsRepository;
	}

	/**
	 * Gets the {@code HospitalStaffRepository} instance
	 * @return The instance of {@code HospitalStaffRepository}
	 */
	public HospitalStaffRepository getHospitalStaffRepository() {
		return hospitalStaffRepository;
	}

	/**
	 * Sets the {@code HospitalStaffRepository} instance
	 * @param hospitalStaffRepository The instance to set for the hospital staff repository.
	 */
	public void setHospitalStaffRepository(HospitalStaffRepository hospitalStaffRepository) {
		this.hospitalStaffRepository = hospitalStaffRepository;
	}

	/**
	 * Gets the {@code MedicationInventoryRepository} instance
	 * @return The instance of {@code MedicationInventoryRepository}
	 */
	public MedicationInventoryRepository getMedicationInventoryRepository() {
		return medicationInventoryRepository;
	}

	/**
	 * Sets the {@code MedicationInventoryRepository} instance
	 * @param medicationInventoryRepository The instance to set for the medication inventory repository.
	 */
	public void setMedicationInventoryRepository(MedicationInventoryRepository medicationInventoryRepository) {
		this.medicationInventoryRepository = medicationInventoryRepository;
	}

	/**
	 * Gets the {@code PatientDataRepository} instance
	 * @return The {@code PatientDataRepository} instance
	 */
	public PatientDataRepository getPatientDataRepository() {
		return patientDataRepository;
	}

	/**
	 * Sets the {@code PatientDataRepository} instance
	 * @param patientDataRepository The instance to set for the patient data repository.
	 */
	public void setPatientDataRepository(PatientDataRepository patientDataRepository) {
		this.patientDataRepository = patientDataRepository;
	}

	/**
	 * Sets the {@code AppointmentService} instance
	 * @return The {@code AppointmentService} instance
	 */
	public AppointmentService getAppointmentService() {
		return appointmentService;
	}

	/**
	 * Sets the {@code AppointmentService


	/**
	 * Sets the {@code AppointmentService} instance
	 * @param appointmentService the {@code AppointmentService} instance
	 */
	public void setAppointmentService(AppointmentService appointmentService) {
		this.appointmentService = appointmentService;
	}

	/**
	 * Gets the {@code CredentialService} instance
	 * @return The {@code CredentialService} instance
	 */
	public CredentialsService getCredentialsService() {
		return credentialsService;
	}

	/**
	 * Sets the {@code CredentialsService} instance
	 *
	 * @param credentialsService The {@code CredentialsService} instance to set
	 */
	public void setCredentialsService(CredentialsService credentialsService) {
		this.credentialsService = credentialsService;
	}

	/**
	 * Gets the {@code ForgotPasswordService} instance
	 * @return The {@code ForgotPasswordService} instance
	 */
	public ForgotPasswordService getForgotPasswordService() {
		return forgotPasswordService;
	}

	/**
	 * Sets the {@code ForgotPasswordService} instance
	 * @param forgotPasswordService the ForgotPasswordService instance
	 */
	public void setForgotPasswordService(ForgotPasswordService forgotPasswordService) {
		this.forgotPasswordService = forgotPasswordService;
	}

	/**
	 * Gets the {@code MedicalInventoryService} instance
	 * @return the {@code MedicalInventoryService} instance
	 */
	public MedicalInventoryService getMedicalInventoryService() {
		return medicalInventoryService;
	}

	/**
	 * Sets the {@code MedicalInventoryService} instance
	 * @param medicalInventoryService the MedicalInventoryService instance
	 */
	public void setMedicalInventoryService(MedicalInventoryService medicalInventoryService) {
		this.medicalInventoryService = medicalInventoryService;
	}

	/**
	 * Gets the {@code PatientAccountService} instance
	 * @return the PatientAccountService instance
	 */
	public PatientAccountService getPatientAccountService() {
		return patientAccountService;
	}

	/**
	 * Sets the {@code PatientAccountService} instance
	 * @param patientAccountService the {@code PatientAccountService} instance
	 */
	public void setPatientAccountService(PatientAccountService patientAccountService) {
		this.patientAccountService = patientAccountService;
	}

	/**
	 * Gets the {@code DoctorAccountService} instance
	 * @return the instance of DoctorAccountService
	 */
	public DoctorAccountService getDoctorAccountService() {
		return doctorAccountService;
	}

	/**
	 * Sets the {@code DoctorAccountService} instance
	 * @param doctorAccountService the instance of DoctorAccountService
	 */
	public void setDoctorAccountService(DoctorAccountService doctorAccountService) {
		this.doctorAccountService = doctorAccountService;
	}

	/**
	 * Gets the {@code PharmacistAccountService} instance
	 * @return the {@code PharmacistAccountService} instance
	 */
	public PharmacistAccountService getPharmacistAccountService() {
		return pharmacistAccountService;
	}

	/**
	 * Sets the {@code PharmacistAccountService} instance
	 * @param pharmacistAccountService the instance of PharmacistAccountService
	 */
	public void setPharmacistAccountService(PharmacistAccountService pharmacistAccountService) {
		this.pharmacistAccountService = pharmacistAccountService;
	}

	/**
	 * Gets the {@code AdministratorAccountService} instance
	 * @return the instance of AdministratorAccountService
	 */
	public AdministratorAccountService getAdministratorAccountService() {
		return administratorAccountService;
	}

	/**
	 * Sets the {@code AdministratorAccountService} instance
	 * @param administratorAccountService the instance of AdministratorAccountService
	 */
	public void setAdministratorAccountService(AdministratorAccountService administratorAccountService) {
		this.administratorAccountService = administratorAccountService;
	}

	/**
	 * Gets the {@code HospitalStaffRegistrationService} instance
	 * @return the instance of HospitalStaffRegistrationService
	 */
	public HospitalStaffRegistrationService getHospitalStaffRegistrationService() {
		return hospitalStaffRegistrationService;
	}

	/**
	 * Sets the {@code HospitalStaffRegistrationService} instance
	 * @param hospitalStaffRegistrationService the instance of HospitalStaffRegistrationService
	 */
	public void setHospitalStaffRegistrationService(HospitalStaffRegistrationService hospitalStaffRegistrationService) {
		this.hospitalStaffRegistrationService = hospitalStaffRegistrationService;
	}

	/**
	 * Gets the {@code AccountManager} instance
	 * @return the instance of AccountManager
	 */
	public AccountManager getAccountManager() {
		return accountManager;
	}

	/**
	 * Sets the {@code AccountManager} instance
	 * @param accountManager the instance of AccountManager
	 */
	public void setAccountManager(AccountManager accountManager) {
		this.accountManager = accountManager;
	}

	/**
	 * Gets the {@code StaffManagementService} instance
	 * @return the instance of AccountManager StaffManagementService
	 */
	public StaffManagementService getStaffManagementService() {
		return staffManagementService;
	}

	/**
	 * Sets the {@code DoctorAccountService} instance
	 * @param staffManagementService the instance of AccountManager StaffManagementService
	 */
	public void setStaffManagementService(StaffManagementService staffManagementService) {
		this.staffManagementService = staffManagementService;
	}
}