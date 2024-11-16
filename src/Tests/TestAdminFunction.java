package Tests;

import Boundary.UserUI.AdministratorMainPage;
import Controllers.AdministratorController;
import Services.HospitalStaffRegistrationService;
import Entity.MedicationInventory;
import Entity.Enums.Gender;
import Entity.Repository.*;
import Entity.User.Administrator;
import Entity.User.Doctor;
import Entity.User.Patient;
import Entity.User.Pharmacist;
import Services.CredentialsService;
import Services.MedicalInventoryService;
import Services.StaffManagementService;
import Services.UserAccount.*;

public class TestAdminFunction {

	public static void main(String[] args) throws Exception {
//		AppointmentsRepository.loadAppointments();
//		PatientMainPage test = new PatientMainPage();
//		test.mainpage();
//        AppointmentsRepository.updateAppointmentFile(AppointmentList.getAppointmentList());
//		AppointmentsRepository appRepo = new AppointmentsRepository();
//		Boolean avail = true;
//		String appID = "APP123";
//		String timeOfAppString = "20/10/2024 14:30";  // Example date and time in dd/MM/yyyy HH:mm format
//		String docID = "DOC456";
//		String docName = "Dr. John Smi";
//		String patID = "PAT789";
//		String patName = "John Doe";
//		Purpose purposeOfApp = Purpose.Consultation;  // Replace with an actual value from your Purpose enum
//		Department department = Department.Cardiology;  // Replace with an actual value from your Department enum
//		Status statusOfApp = Status.Confirmed;  // Replace with an actual value from your Status enum
//		String appointOutcomeRecord = "Follow-up required";
//		String medicine = "Aspirin";
//		String medicineIssuedDateString = "22/10/2024";  // Example date in dd/MM/yyyy format
//		String dosage = "100 mg";
//		String instructions = "Take with food";
//
//		// Parse the date strings into LocalDateTime and LocalDate objects
//		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("d/M/yyyy H:mm");
//		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("d/M/yyyy");
//		LocalDateTime timeOfApp = LocalDateTime.parse(timeOfAppString, dateTimeFormatter);
//		LocalDate medicineIssuedDate = LocalDate.parse(medicineIssuedDateString, dateFormatter);
//
//		// Creating the Appointment object
//		Appointment app = new Appointment(
//		    true,
//		    appID,
//		    timeOfAppString,
//		    docID,
//		    docName,
//		    patID,
//		    patName,
//		    purposeOfApp,
//		    department,
//		    statusOfApp,
//		    appointOutcomeRecord,
//		    medicine,
//		    medicineIssuedDateString,
//		    dosage,
//		    instructions
//		);
////		appRepo.createRecord(true,
////			    appID,
////			    timeOfAppString,
////			    docID,
////			    docName,
////			    patID,
////			    patName,
////			    purposeOfApp,
////			    department,
////			    statusOfApp,
////			    appointOutcomeRecord,
////			    medicine,
////			    medicineIssuedDateString,
////			    dosage,
////			    instructions);
//		appRepo.deleteRecord(appID);

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



		AccountManager accountManager = new AccountManager(patientService,doctorService,pharmacistService,administratorService,credentialsService);

		// Create Entity
		Administrator admin = new Administrator("Checking", "Checking", 33,Gender.MALE);

		// Create Controller
		AdministratorController controller = new AdministratorController(admin, staffManagementService, medicalInventoryService,accountManager);

		// Create Boundary
		AdministratorMainPage mainPage = new AdministratorMainPage(controller,hospitalStaffRegistrationService);

		mainPage.homePage();

		// Testing Functions
		HospitalStaffRepository.updateHospitalStaffFile(Administrator.getAdministratorList(), Doctor.getDoctorList(), Pharmacist.getPharmacistList());
	}

}
