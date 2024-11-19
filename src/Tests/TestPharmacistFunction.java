package Tests;

import Boundary.UserUI.DoctorMainPage;
import Boundary.UserUI.PharmacistMainPage;
import Controllers.DoctorController;
import Services.HospitalStaffRegistrationService;
import Controllers.PharmacistController;
import Entity.AppointmentList;
import Entity.MedicationInventory;
import Entity.Repository.AppointmentsRepository;
import Entity.Repository.CredentialsRepository;
import Entity.Repository.HospitalStaffRepository;
import Entity.Repository.MedicationInventoryRepository;
import Entity.Repository.PatientDataRepository;
import Entity.User.Administrator;
import Entity.User.Doctor;
import Entity.User.Patient;
import Entity.User.Pharmacist;
import Services.AppointmentService;
import Services.CredentialsService;
import Services.MedicalInventoryService;
import Services.StaffManagementService;
import Services.UserAccount.AccountManager;
import Services.UserAccount.AdministratorAccountService;
import Services.UserAccount.DoctorAccountService;
import Services.UserAccount.IUserAccountService;
import Services.UserAccount.PatientAccountService;
import Services.UserAccount.PharmacistAccountService;

public class TestPharmacistFunction {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// Create Repository
			//HospitalStaffRepository hospitalStaffRepository = new HospitalStaffRepository("program_files/HospitalStaff.csv");
			HospitalStaffRepository hospitalStaffRepository = new HospitalStaffRepository("src/Tests/TestingFiles/HospitalStaff.csv");
			HospitalStaffRepository.loadAdministrator();
			HospitalStaffRepository.loadDoctorList();
			HospitalStaffRepository.loadPharmacistList();
			
			MedicationInventoryRepository medicationInventoryRepository = new MedicationInventoryRepository("src/Tests/TestingFiles/MedicationInventory.csv");

			CredentialsRepository credentialsRepository = new CredentialsRepository("src/Tests/TestingFiles/credentials.txt");

			PatientDataRepository patientRepository = new PatientDataRepository("src/Tests/TestingFiles/patientsData.txt");
			
			AppointmentsRepository appRepo = new AppointmentsRepository("src/Tests/TestingFiles/appointments.csv");
			AppointmentsRepository.loadAppointments();
			// Create Services
			CredentialsService credentialsService = new CredentialsService(credentialsRepository);

			IUserAccountService<Patient> patientService = new PatientAccountService(credentialsService,patientRepository);
			IUserAccountService<Doctor> doctorService = new DoctorAccountService(credentialsService,hospitalStaffRepository);
			IUserAccountService<Pharmacist> pharmacistService = new PharmacistAccountService(credentialsService,hospitalStaffRepository);
			IUserAccountService<Administrator> administratorService = new AdministratorAccountService(credentialsService,hospitalStaffRepository);

			HospitalStaffRegistrationService hospitalStaffRegistrationService = new HospitalStaffRegistrationService(credentialsRepository,(DoctorAccountService) doctorService
					,(PharmacistAccountService) pharmacistService,(AdministratorAccountService)administratorService);

			StaffManagementService staffManagementService = new StaffManagementService((DoctorAccountService) doctorService,
					(AdministratorAccountService) administratorService,(PharmacistAccountService) pharmacistService,hospitalStaffRegistrationService);
			MedicalInventoryService medicalInventoryService = new MedicalInventoryService(MedicationInventory.getInstance(), medicationInventoryRepository);
			medicalInventoryService.loadInventoryFromFile();
			
			AppointmentService appointmentService = new AppointmentService(appRepo);



			AccountManager accountManager = new AccountManager(patientService,doctorService,pharmacistService,administratorService,credentialsService);

			// Create Controller
			DoctorController docController = new DoctorController(Doctor.getDoctorList().get(0), appointmentService);
			PharmacistController phController = new PharmacistController(medicalInventoryService, appointmentService, Pharmacist.getPharmacistList().get(0));

			// Create Boundary
			DoctorMainPage mainPage = new DoctorMainPage(docController);
			PharmacistMainPage mainPage1 = new PharmacistMainPage(phController);

			mainPage1.homePage();

			// Testing Functions
			HospitalStaffRepository.updateHospitalStaffFile(Administrator.getAdministratorList(), Doctor.getDoctorList(), Pharmacist.getPharmacistList());
			AppointmentsRepository.updateAppointmentFile(AppointmentList.getInstance().getAppointmentList());
	}
}
