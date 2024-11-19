import Application.ApplicationContext;
import Boundary.*;
import Controllers.ChangePasswordController;
import Controllers.LoginController;
import Controllers.PatientRegistrationController;
import Entity.AppointmentList;
import Entity.Repository.AppointmentsRepository;
import Entity.Repository.HospitalStaffRepository;
import Entity.Repository.PatientDataRepository;
import Entity.User.Administrator;
import Entity.User.Doctor;
import Entity.User.Pharmacist;

public class mainPage {

	public static void main(String[] args) {
		String workingDir = System.getProperty("user.dir");
		String credentialsPath = workingDir + "/src/Tests/TestingFiles/credentials.txt";
        String patientsDataPath = workingDir + "/src/Tests/TestingFiles/patientsData.txt";
        String hospitalDataPath = workingDir + "/src/Tests/TestingFiles/HospitalStaff.csv";
        String medicationInventoryPath = workingDir + "/src/Tests/TestingFiles/MedicationInventory.csv";
        String appointmentsDataPath = workingDir + "/src/Tests/TestingFiles/appointments.csv";
        
        ApplicationContext applicationContext = new ApplicationContext(appointmentsDataPath, credentialsPath, hospitalDataPath, medicationInventoryPath, patientsDataPath);
        
        //Load all the data
        AppointmentsRepository.loadAppointments();
        HospitalStaffRepository.loadAdministrator();
		HospitalStaffRepository.loadDoctorList();
		HospitalStaffRepository.loadPharmacistList();
		PatientDataRepository.loadPatientlist();
		applicationContext.getMedicalInventoryService().loadInventoryFromFile();
		
		//Remove old unscheduled appointments
		applicationContext.getAppointmentService().cleanUpUnscheduledAppointments();
        
        // Setup Controllers
        PatientRegistrationController patientRegistrationController= new PatientRegistrationController(applicationContext.getCredentialsRepository(), applicationContext.getPatientAccountService());
        LoginController loginController = new LoginController(applicationContext.getCredentialsService(), applicationContext.getAccountManager(), applicationContext.getForgotPasswordService());
        ChangePasswordController changePasswordController = new ChangePasswordController(applicationContext.getCredentialsService());

        // Create Boundaries
        PatientRegistrationUI patientRegistrationUI = new PatientRegistrationUI(patientRegistrationController);
        LogoutUI logoutUI = new LogoutUI();
        LoginUI loginUI = new LoginUI(loginController, applicationContext.getAccountManager(), applicationContext.getCredentialsService());
        ChangePasswordUI changePasswordUI= new ChangePasswordUI(changePasswordController);
        WelcomeUI welcomeUI = new WelcomeUI(patientRegistrationUI,logoutUI,loginUI,changePasswordUI);
        
        welcomeUI.welcomeUI(applicationContext);
        
        //Update all the data at the end
        HospitalStaffRepository.updateHospitalStaffFile(Administrator.getAdministratorList(), Doctor.getDoctorList(), Pharmacist.getPharmacistList());
        AppointmentsRepository.updateAppointmentFile(AppointmentList.getInstance().getAppointmentList());
        applicationContext.getMedicalInventoryService().saveInventoryToFile();
	}

}
