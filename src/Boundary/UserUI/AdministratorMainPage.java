package Boundary.UserUI;

import Controllers.AdministratorController;
import Services.HospitalStaffRegistrationService;
import Entity.Appointment;
import Entity.AppointmentList;
import Entity.MedicationInventory;
import Entity.Medicine;
import Entity.Enums.Department;
import Entity.Enums.Domain;
import Entity.Enums.Gender;
import Entity.User.Administrator;
import Entity.User.Doctor;
import Entity.User.HospitalStaff;
import Entity.User.Pharmacist;
import Services.InputService;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AdministratorMainPage extends UserMainPage{
	private AdministratorController adminController;
	private HospitalStaffRegistrationService hospitalStaffRegistrationService;
	private final int columnWidth = 20;
	
    public AdministratorMainPage(AdministratorController adnController, HospitalStaffRegistrationService hospitalStaffRegistrationService) {
    	this.adminController = adnController;
		this.hospitalStaffRegistrationService = hospitalStaffRegistrationService;
    }
	
	public void homePage() {
        int choice;
        do {
			System.out.println("    _       _           _       _     _             _             \n" +
					"   / \\   __| |_ __ ___ (_)_ __ (_)___| |_ _ __ __ _| |_ ___  _ __ \n" +
					"  / _ \\ / _` | '_ ` _ \\| | '_ \\| / __| __| '__/ _` | __/ _ \\| '__|\n" +
					" / ___ \\ (_| | | | | | | | | | | \\__ \\ |_| | | (_| | || (_) | |   \n" +
					"/_/   \\_\\__,_|_| |_| |_|_|_| |_|_|___/\\__|_|  \\__,_|\\__\\___/|_|   ");

            System.out.println("\nChoose the number of functions:\n"
                    + "(1) View and Manage Hospital\n"
                    + "(2) View Appointment Details\n"
                    + "(3) Manage Inventory System\n"
                    + "(4) Logout");

            choice = InputService.inputInteger();

            switch (choice) {
                case 1 -> viewAndManageHospitalStaff();
                case 2 -> viewAppointmentDetails();
                case 3 -> manageInventorySystem();
                case 4 -> System.out.println("Logging out.");
                default -> System.out.println("Invalid choice, please try again.");
            }
        } while (choice != 4);
    }
	
	private void viewAndManageHospitalStaff() {
        int choice;

        do {
            System.out.println("\n--- Hospital Management ---");
            System.out.println("(1) Add Staff Member");
            System.out.println("(2) Remove Staff Member");
            System.out.println("(3) Update Staff Information");
            System.out.println("(4) View All Staff");
			System.out.println("(5) Manage User Account Lock");
            System.out.println("(6) Return to Main Menu");

            choice = InputService.inputInteger();

            switch (choice) {
                case 1 -> addStaffMember();
                case 2 -> removeStaffMember();
                case 3 -> updateStaffMember();
                case 4 -> displayAllStaff();
				case 5 -> manageUserAccountLock();
                case 6 -> System.out.println("Returning to Main Menu.");
                default -> System.out.println("Invalid choice, please try again.");
            }
        } while (choice != 6);
    }
	
	private void viewAppointmentDetails() {
        int choice;

        do {
            System.out.println("\n--- View Appointment Details ---");
            System.out.println("(1) View All Appointments");
            System.out.println("(2) View Appointments by Status");
            System.out.println("(3) Search by Appointment ID");
            System.out.println("(4) Search by Patient ID");
            System.out.println("(5) Search by Doctor ID");
            System.out.println("(6) Return to Main Menu");

            choice = InputService.inputInteger();

            switch (choice) {
                case 1 -> displayAppointmentList(AppointmentList.getInstance().getAppointmentList());
                case 2 -> viewAppointmentsByStatus();
                case 3 -> searchAppointmentByID();
                case 4 -> searchByPatientID();
                case 5 -> searchByDoctorID();
                case 6 -> System.out.println("Returning to Main Menu.");
                default -> System.out.println("Invalid choice, please try again.");
            }
        } while (choice != 6);
    }
	
	private void manageInventorySystem() {
	        int choice;
	        do {
	            System.out.println("\n--- Medication Inventory System ---");
	            System.out.println("(1) View Inventory");
	            System.out.println("(2) Add Medicine");
	            System.out.println("(3) Remove Medicine");
	            System.out.println("(4) Update Medicine");
	            System.out.println("(5) Check Inventory for Low Stock");
	            System.out.println("(6) Process Replenishment Requests");
	            System.out.println("(7) Return to Main Menu");

	            choice = InputService.inputInteger();

	            switch (choice) {
	                case 1 -> viewInventory();
	                case 2 -> addOrIncrementMedicine();
	                case 3 -> removeMedicine();
	                case 4 -> updateMedicine();
	                case 5 -> checkInventory();
	                case 6 -> processReplenishmentRequests();
	                case 7 -> System.out.println("Returning to Main Menu.");
	                default -> System.out.println("Invalid choice.");
	            }
	        } while (choice != 7);
	    }

	public void addStaffMember() {

        System.out.print("Enter role: \n(1). Doctor\n(2). Pharmacist\n(3). Administrator\n");
        int choice = InputService.inputInteger();
        Domain role;
        switch(choice) {
	        case 1:
	        	role = Domain.DOCTOR;
	        	break;
	        case 2:
	        	role = Domain.PHARMACIST;
	        	break;
	        case 3:
	        	role = Domain.ADMINISTRATOR;
	        	break;
	        default:
	        	System.out.println("Please only select available options!!");
	        	return;
        }

		String hospitalId = hospitalStaffRegistrationService.getUserName(role);
		System.out.println("Your Assigned Hospital ID is : \n"+ hospitalId +"\n");


		System.out.println("Please enter your password : ");
		String plainTextPassword = InputService.inputString();
		System.out.println("Please enter your Security Question : ");
		String securityQuestion = InputService.inputString();
		System.out.println("Please enter the answer to your Security Question : ");
		String plainTextSecurityAnswer = InputService.inputString().toLowerCase();

        System.out.print("Enter gender:\n(1). Male\n(2). Female\n");
        int choice1 = InputService.inputInteger();
        Gender gender;
        switch(choice1) {
	        case 1:
	        	gender = Gender.MALE;
	        	break;
	        case 2:
	        	gender = Gender.FEMALE;
	        	break;
	        default:
	        	System.out.println("Please only select available options!!");
	        	return;
	    }

		System.out.print("Enter name: ");
		String name = InputService.inputString();

		System.out.println("Enter Age: ");
        int age = InputService.inputInteger();
        switch (role) {
        case DOCTOR -> {
            //newStaff = new Doctor(name, hospitalId, domainRole, gender, age);
            System.out.println("Select the departmen:\n"
                    + "(1) Cardiology\n"
                    + "(2) Neurology\n"
                    + "(3) Oncology\n"
                    + "(4) Dermatology\n"
                    + "(5) Endocrinology\n"
                    + "(6) Gastroenterology\n"
                    + "(7) Nephrology\n"
                    + "(8) Pulmonology\n"
                    + "(9) Rheumatology\n"
                    + "(10) ObstetricsGynecology\n"
                    + "(11) Others\n");
            int choiceDep = InputService.inputInteger();
            Department dept;
            if (choiceDep == 1) dept = Department.Cardiology;
            else if (choiceDep == 2) dept = Department.Neurology;
            else if (choiceDep == 3) dept = Department.Oncology;
            else if (choiceDep == 4) dept = Department.Dermatology;
            else if (choiceDep == 5) dept = Department.Endocrinology;
            else if (choiceDep == 6) dept = Department.Gastroenterology;
            else if (choiceDep == 7) dept = Department.Nephrology;
            else if (choiceDep == 8) dept = Department.Pulmonology;
            else if (choiceDep == 9) dept = Department.Rheumatology;
            else if (choiceDep == 10) dept = Department.ObstetricsGynecology;
            else dept = Department.Others;
            Doctor doc = new Doctor(hospitalId, name, age, gender,dept);
            adminController.addStaffMember(doc,plainTextPassword,securityQuestion,plainTextSecurityAnswer);
            System.out.println("Doctor added: " + doc.getName());
        }
        case PHARMACIST -> {
            Pharmacist phar = new Pharmacist(hospitalId, name, age, gender, Domain.PHARMACIST);
            adminController.addStaffMember(phar,plainTextPassword,securityQuestion,plainTextSecurityAnswer);
            System.out.println("Pharmacist added: " + phar.getName());
        }
        case ADMINISTRATOR -> {
            Administrator admin = new Administrator(hospitalId, name, age, gender);
            adminController.addStaffMember(admin,plainTextPassword,securityQuestion,plainTextSecurityAnswer);
            System.out.println("Administrator added: " + admin.getName());
        }
        default -> throw new IllegalArgumentException("Invalid role");
        }
	}

	public void removeStaffMember() {
    	displayAllStaff();
        System.out.print("Enter the hospital ID to remove: ");
        String hospitalId = InputService.inputString();
        adminController.removeStaffMember(hospitalId);
    }
	
	public void updateStaffMember() {
		displayAllStaff();
        System.out.print("Enter the hospital ID to update: ");
        String hospitalId = InputService.inputString();
        Optional<? extends HospitalStaff> staffToadd = adminController.findStaffById(hospitalId);
        if(!staffToadd.isPresent()) {
        	System.out.println("ID does not exist to update!");
        	return;
        }
        System.out.print("Enter new name: ");
        String newName = InputService.inputString();
        System.out.print("Enter gender:\n(1). Male\n(2). Female\n");
        int choice1 = InputService.inputInteger();
        Gender gender;
        switch(choice1) {
	        case 1:
	        	gender = Gender.MALE;
	        	break;
	        case 2:
	        	gender = Gender.FEMALE;
	        	break;
	        default:
	        	System.out.println("Please only select available options!!");
	        	return;
	    }
        System.out.println("Enter Age: ");
        int age = InputService.inputInteger();
        Department dept = null;
        if(hospitalId.startsWith("D")) {
        	System.out.println("Select the departmen:\n"
                    + "(1) Cardiology\n"
                    + "(2) Neurology\n"
                    + "(3) Oncology\n"
                    + "(4) Dermatology\n"
                    + "(5) Endocrinology\n"
                    + "(6) Gastroenterology\n"
                    + "(7) Nephrology\n"
                    + "(8) Pulmonology\n"
                    + "(9) Rheumatology\n"
                    + "(10) ObstetricsGynecology\n"
                    + "(11) Others\n");
            int choiceDep = InputService.inputInteger();
            if (choiceDep == 1) dept = Department.Cardiology;
            else if (choiceDep == 2) dept = Department.Neurology;
            else if (choiceDep == 3) dept = Department.Oncology;
            else if (choiceDep == 4) dept = Department.Dermatology;
            else if (choiceDep == 5) dept = Department.Endocrinology;
            else if (choiceDep == 6) dept = Department.Gastroenterology;
            else if (choiceDep == 7) dept = Department.Nephrology;
            else if (choiceDep == 8) dept = Department.Pulmonology;
            else if (choiceDep == 9) dept = Department.Rheumatology;
            else if (choiceDep == 10) dept = Department.ObstetricsGynecology;
            else dept = Department.Others;
        }
        adminController.updateStaffMember(hospitalId, newName, age, gender, dept);
	}
	
	public void displayAllStaff() {
        System.out.println("All Doctors at the hospital:");
        System.out.println("+" + "-".repeat(10) + "+"
				+ "-".repeat(20) + "+"
				+ "-".repeat(10) + "+"
				+ "-".repeat(10) + "+"
				+ "-".repeat(20) + "+");

		System.out.println("|" + formatCell("ID", 10)
				+ "|" + formatCell("Doctor Name", 20)
				+ "|" + formatCell("Gender", 10)
				+ "|" + formatCell("Age", 10) 
				+ "|" + formatCell("Department", 20) + "|");

		System.out.println("+" + "-".repeat(10) + "+"
				+ "-".repeat(20) + "+"
				+ "-".repeat(10) + "+"
				+ "-".repeat(10) + "+"
				+ "-".repeat(20) + "+");
		int i = 1;
		for(Doctor doc : Doctor.getDoctorList()) {
			System.out.println("|" + formatCell(doc.getUserID(), 10)
					+ "|" + formatCell(doc.getName(), 20)
					+ "|" + formatCell(doc.getGender().toString(), 10)
					+ "|" + formatCell(String.valueOf(doc.getAge()), 10) 
					+ "|" + formatCell(doc.getDepartment().toString(), 20) + "|");
			System.out.println("+" + "-".repeat(10) + "+"
					+ "-".repeat(20) + "+"
					+ "-".repeat(10) + "+"
					+ "-".repeat(10) + "+"
					+ "-".repeat(20) + "+");
			i++;
		}

        System.out.println("All Pharmacists at the hospital:");
        System.out.println("+" + "-".repeat(10) + "+"
				+ "-".repeat(20) + "+"
				+ "-".repeat(10) + "+"
				+ "-".repeat(10) + "+");

		System.out.println("|" + formatCell("ID", 10)
				+ "|" + formatCell("Pharmacist Name", 20)
				+ "|" + formatCell("Gender", 10)
				+ "|" + formatCell("Age", 10) + "|");

		System.out.println("+" + "-".repeat(10) + "+"
				+ "-".repeat(20) + "+"
				+ "-".repeat(10) + "+"
				+ "-".repeat(10) + "+");
		int j = 1;
		for(Pharmacist ph : Pharmacist.getPharmacistList()) {
			System.out.println("|" + formatCell(ph.getUserID(), 10)
					+ "|" + formatCell(ph.getName(), 20)
					+ "|" + formatCell(ph.getGender().toString(), 10)
					+ "|" + formatCell(String.valueOf(ph.getAge()), 10) + "|");
			System.out.println("+" + "-".repeat(10) + "+"
					+ "-".repeat(20) + "+"
					+ "-".repeat(10) + "+"
					+ "-".repeat(10) + "+");
			j++;
		}

        System.out.println("All Administrators at the hospital:");
        System.out.println("+" + "-".repeat(10) + "+"
				+ "-".repeat(20) + "+"
				+ "-".repeat(10) + "+"
				+ "-".repeat(10) + "+");

		System.out.println("|" + formatCell("ID", 10)
				+ "|" + formatCell("Administartor Name", 20)
				+ "|" + formatCell("Gender", 10)
				+ "|" + formatCell("Age", 10) + "|");

		System.out.println("+" + "-".repeat(10) + "+"
				+ "-".repeat(20) + "+"
				+ "-".repeat(10) + "+"
				+ "-".repeat(10) + "+");
		int k = 1;
		for(Administrator adm : Administrator.getAdministratorList()) {
			System.out.println("|" + formatCell(adm.getUserID(), 10)
					+ "|" + formatCell(adm.getName(), 20)
					+ "|" + formatCell(adm.getGender().toString(), 10)
					+ "|" + formatCell(String.valueOf(adm.getAge()), 10) + "|");
			System.out.println("+" + "-".repeat(10) + "+"
					+ "-".repeat(20) + "+"
					+ "-".repeat(10) + "+"
					+ "-".repeat(10) + "+");
			k++;
		}
    }
	
	public void displayAppointmentList(ArrayList<Appointment> appointmentList) {
		if (appointmentList == null || appointmentList.isEmpty()) {
			System.out.println("No appointments are found.");
			return;
		}

		// Print table header
		System.out.println("+" + "-".repeat(columnWidth) + "+" +
				"-".repeat(columnWidth) + "+" + "-".repeat(columnWidth) + "+" +
				"-".repeat(columnWidth) + "+" + "-".repeat(columnWidth) + "+" +
				"-".repeat(columnWidth) + "+" + "-".repeat(columnWidth) + "+" +
				"-".repeat(columnWidth) + "+" + "-".repeat(columnWidth) + "+" + "-".repeat(50) + "+" +
				"-".repeat(columnWidth) + "+" + "-".repeat(columnWidth) + "+" +
				"-".repeat(columnWidth) + "+" + "-".repeat(30) + "+");
		
		System.out.println("|" + formatCell("Appointment ID", columnWidth)
				+ "|" + formatCell("App Date and Time", columnWidth)
				+ "|" + formatCell("Doctor ID", columnWidth)
				+ "|" + formatCell("Doctor Name", columnWidth)
				+ "|" + formatCell("Patient ID", columnWidth)
				+ "|" + formatCell("Patient Name", columnWidth)
				+ "|" + formatCell("Purpose", columnWidth)
				+ "|" + formatCell("Department", columnWidth)
				+ "|" + formatCell("Status", columnWidth)
				+ "|" + formatCell("Appointment Outcomes", 50) 
				+ "|" + formatCell("Medicine", columnWidth) 
				+ "|" + formatCell("Issued Date", columnWidth)
				+ "|" + formatCell("Dosage", columnWidth)
				+ "|" + formatCell("Instructions", 30) + "|");
		
		System.out.println("+" + "-".repeat(columnWidth) + "+" +
				"-".repeat(columnWidth) + "+" + "-".repeat(columnWidth) + "+" +
				"-".repeat(columnWidth) + "+" + "-".repeat(columnWidth) + "+" +
				"-".repeat(columnWidth) + "+" + "-".repeat(columnWidth) + "+" +
				"-".repeat(columnWidth) + "+" + "-".repeat(columnWidth) + "+" + "-".repeat(50) + "+" +
				"-".repeat(columnWidth) + "+" + "-".repeat(columnWidth) + "+" +
				"-".repeat(columnWidth) + "+" + "-".repeat(30) + "+");
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy H:mm");
		DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("d/M/yyyy");
		for (Appointment row : appointmentList) {
			System.out.println("|" + formatCell(row.getAppID(), columnWidth)
					+ "|" + formatCell(row.getTimeOfApp().format(formatter), columnWidth)
					+ "|" + formatCell(row.getDocID(), columnWidth)
					+ "|" + formatCell(row.getDocName(), columnWidth)
					+ "|" + formatCell(row.getPatID(), columnWidth)
					+ "|" + formatCell(row.getPatName(), columnWidth)
					+ "|" + formatCell((row.getPurposeOfApp() != null) ? row.getPurposeOfApp().toString() : " ", columnWidth)
					+ "|" + formatCell(row.getAppointmentDepartment().toString(), columnWidth)
					+ "|" + formatCell((row.getStatusOfApp() != null) ? row.getStatusOfApp().toString() : " ", columnWidth)
					+ "|" + formatCell(row.getAppointOutcomeRecord(), 50) 
					+ "|" + formatCell(row.getMedicine(), columnWidth)
					+ "|" + formatCell((row.getMedicineIssuedDate() != null) ? row.getMedicineIssuedDate().format(formatter1) : " ", columnWidth)
					+ "|" + formatCell(row.getDosage(), columnWidth)
					+ "|" + formatCell(row.getInstructions(), 30) + "|");
			
			System.out.println("+" + "-".repeat(columnWidth) + "+"
					+ "-".repeat(columnWidth) + "+"
					+ "-".repeat(columnWidth) + "+"
					+ "-".repeat(columnWidth) + "+"
					+ "-".repeat(columnWidth) + "+"
					+ "-".repeat(columnWidth) + "+"
					+ "-".repeat(columnWidth) + "+"
					+ "-".repeat(columnWidth) + "+"
					+ "-".repeat(columnWidth) + "+"
					+ "-".repeat(50) + "+"
					+ "-".repeat(columnWidth) + "+"
					+ "-".repeat(columnWidth) + "+"
					+ "-".repeat(columnWidth) + "+"
					+ "-".repeat(30) + "+");
		}
	}
	
	public void viewAppointmentsByStatus() {
		System.out.println("Choose appointment status to filter by:");
        System.out.println("(1) Confirmed");
        System.out.println("(2) Pending");
        System.out.println("(3) Completed");
        System.out.println("(4) Cancelled");
        System.out.println("(5) PrescriptionPending");

        int statusChoice = InputService.inputInteger();

        String status;
        switch (statusChoice) {
            case 1 -> status = "Confirmed";
            case 2 -> status = "Pending";
            case 3 -> status = "Completed";
            case 4 -> status = "Cancelled";
            case 5 -> status = "PrescriptionPending";
            default -> {
                System.out.println("Invalid status choice.");
                return;
            }
        }

        displayAppointmentList(adminController.getAppointmentsByStatus(status));
	}
	
	public void searchAppointmentByID() {
        System.out.println("Enter the Appointment ID:");
        String appointmentID = InputService.inputString();

        Appointment appointment = adminController.getAppointmentByID(appointmentID);
        if (appointment != null) {
        		ArrayList<Appointment> singleAppointmentList = new ArrayList<>();
            singleAppointmentList.add(appointment);
            displayAppointmentList(singleAppointmentList);
        } else {
            System.out.println("Appointment ID not found.");
        }
    }
	
	public void searchByPatientID() {
        System.out.println("Enter the Patient ID:");
        String patientID = InputService.inputString();

        displayAppointmentList(adminController.getAppointmentsByPatientID(patientID));
    }
	
	public void searchByDoctorID() {
		System.out.println("Enter the Doctor ID:");
        String doctorID = InputService.inputString();

        displayAppointmentList(adminController.getAppointmentsByDoctorID(doctorID));
	}

	public void viewInventory() {
		int i = 1;
		if (adminController.getMedicationInventory().isEmpty()) {
			System.out.println("Inventory is empty.");
		} else {
			System.out.println("Current Inventory:");
			System.out.println("+" + "-".repeat(3) + "+"
					+ "-".repeat(30) + "+"
					+ "-".repeat(15) + "+"
					+ "-".repeat(15) + "+"
					+ "-".repeat(15) + "+");

			System.out.println("|" + formatCell("No.", 3)
					+ "|" + formatCell("Medicine Name", 30)
					+ "|" + formatCell("Current Stock", 15)
					+ "|" + formatCell("Low Stock Alert", 15) 
					+ "|" + formatCell("Requested Amt", 15)+ "|");

			System.out.println("+" + "-".repeat(3) + "+"
					+ "-".repeat(30) + "+"
					+ "-".repeat(15) + "+"
					+ "-".repeat(15) + "+"
					+ "-".repeat(15) + "+");

			for (Medicine medicine : adminController.getMedicationInventory()) {
				System.out.println("|" + formatCell(String.valueOf(i), 3)
						+ "|" + formatCell(medicine.getNameOfMedicine(), 30)
						+ "|" + formatCell(String.valueOf(medicine.getCurrentStock()), 15)
						+ "|" + formatCell(String.valueOf(medicine.getLowStockLevelAlert()), 15) 
						+ "|" + formatCell(String.valueOf(medicine.getRequestAmount()), 15)+ "|");

				System.out.println("+" + "-".repeat(3) + "+"
						+ "-".repeat(30) + "+"
						+ "-".repeat(15) + "+"
						+ "-".repeat(15) + "+"
						+ "-".repeat(15) + "+");
				i++;
			}
		}
	}
	
	public void addOrIncrementMedicine() {
    	viewInventory();
        System.out.print("Please select the medicine to add stock (0 to add new medicine): ");
        int selection = InputService.inputInteger();
        if(selection < 0 || selection > MedicationInventory.getInventory().size()) { //user input unavailable choice
        	System.out.println("Please only select from the available choices.");
        	return;
        }
        	
        if (selection != 0 && adminController.isMedicineInInventory(MedicationInventory.getInventory().get(selection-1).getNameOfMedicine())) {
            System.out.print("Enter additional stock: ");
            int additionalStock = InputService.inputInteger();
            adminController.increaseCurrentStock(additionalStock, selection);
        } else {
        	System.out.print("Enter new medicine name: ");
        	String name = InputService.inputString();
        	if(adminController.isMedicineInInventory(name)) {
        		System.out.println("Medicine is inside the inventory! Please check again.");
        		return;
        	}
            System.out.print("Enter initial stock: ");
            int stock = InputService.inputInteger();
            System.out.print("Enter low stock alert level: ");
            int alertLevel = InputService.inputInteger();
            adminController.addMedicine(name, stock, alertLevel);
        }
    }
	
	// Remove Medicine from inventory
    public void removeMedicine() {
    	viewInventory();
        System.out.print("Select the medicine to remove: ");
        int choice = InputService.inputInteger();
        adminController.removeMedicine(choice);
    }
	
	public void updateMedicine() {
    	viewInventory();
    	System.out.print("Select the medicine to update: ");
        int choice = InputService.inputInteger();
        if(choice <= 0 || choice > MedicationInventory.getInventory().size()) { //user input unavailable choice
        	System.out.println("Please only select from the available choices: ");
        	return;
        }
        Medicine medicine = adminController.findMedicineByName(MedicationInventory.getInventory().get(choice-1).getNameOfMedicine());
        if (medicine == null) {
            System.out.println("Medicine not found.");
            return;
        }

        System.out.print("Enter new stock amount: ");
        int newStock = InputService.inputInteger();
        System.out.print("Enter new alert level: ");
        int newAlertLevel = InputService.inputInteger();
        adminController.updateMedicine(medicine, newStock, newAlertLevel);
        System.out.println("Updated " + MedicationInventory.getInventory().get(choice-1).getNameOfMedicine() + ": New stock = " + newStock + ", New alert level = " + newAlertLevel);
    }
    
    public void checkInventory() {
        boolean lowStockFound = false;
        for (Medicine medicine : MedicationInventory.getInventory()) {
            if (medicine.needsReplenishment()) {
                System.out.println("Low stock alert: " + medicine.getNameOfMedicine() +"Current Stock Level : "+ medicine.getCurrentStock() + " Low Stock alert is set at : " + medicine.getLowStockLevelAlert());
                lowStockFound = true;
            }
        }
        if (!lowStockFound) {
            System.out.println("All medicines are sufficiently stocked.");
        }
    }
    
    // Process all pending replenishment requests
    public void processReplenishmentRequests() {
    		List<Integer> medicineIndices = viewMedicineForReplenishmentRequests();
    		System.out.println("Select the medicine to replenish (-1 to exit): ");
    		int choice = InputService.inputInteger();
    		if (choice == -1) return;
    		if (choice<=0 || choice>medicineIndices.size()) {System.out.println("Please only select from the available choices"); return;}
    		System.out.println("How much you want to replenish: ");
    		int amount = InputService.inputInteger();
    		if (amount<0) {System.out.println("Unavailable amount!!"); return;}
    		adminController.processReplenishmentRequests(medicineIndices.get(choice-1), amount);
    }
    
    private static String formatCell(String value, int width) {
		if (value == " ") {
			value = "";
		}
		return String.format("%-" + width + "s", value);  // Left-align the text within the specified width
	}

	public void manageUserAccountLock(){
		int choice;

		do {
			System.out.println("\n--- Hospital Staff Management ---");


			// Show Locked Users
			ArrayList<String> lockedUsers = adminController.getAllLockedUserIDs();
			ArrayList<String> unlockedUsers = adminController.getAllUnlockedUserIDs();

			System.out.println("Locked Users :");
			for (String lockedUser : lockedUsers) {
				System.out.println(lockedUser);
			}
			System.out.println("---------------------------------");

			System.out.println("Unlocked Users :");
			for (String unlockedUser : unlockedUsers) {
				System.out.println(unlockedUser);
			}
			System.out.println("---------------------------------");

			System.out.println("(1) Lock an Account");
			System.out.println("(2) Unlock an Account");
			System.out.println("(3) Return to Main Menu");

			choice = InputService.inputInteger();

			switch (choice) {
				case 1 -> lockAccount();
				case 2 -> unlockAccount();
				case 3 -> System.out.println("Returning to Main Menu.");
				default -> System.out.println("Invalid choice, please try again.");
			}
		} while (choice != 3);
	}

	public void lockAccount() {
		ArrayList<String> lockedUsers = adminController.getAllLockedUserIDs();
		ArrayList<String> allUserIDs = adminController.getAllUserIDs();
		String input;

		do {
			System.out.println("Please Enter The Account That You Want To Lock: ");
			input = InputService.inputString();

			if (!allUserIDs.contains(input)) {
				System.out.println("This account does not exist.");
				return;
			}
			else if (lockedUsers.contains(input)) {
				System.out.println("This account is already locked.");
			}

		} while (lockedUsers.contains(input)||!allUserIDs.contains(input));

		adminController.lockAccount(input);
		System.out.println("Account " + input + " is locked.");
	}

	public void unlockAccount() {
		ArrayList<String> lockedUsers = adminController.getAllLockedUserIDs();
		ArrayList<String> allUserIDs = adminController.getAllUserIDs();
		String input;

		do {
			System.out.println("Please Enter The Account That You Want To Unlock: ");
			input = InputService.inputString();

			if (!allUserIDs.contains(input)) {
				System.out.println("This account does not exist.");
				return;
			}
			else if (!lockedUsers.contains(input)) {
				System.out.println("This account is already unlocked.");
				return;
			}

		} while (!lockedUsers.contains(input)||!allUserIDs.contains(input));

		adminController.unlockAccount(input);
		System.out.println("Account " + input + " is unlocked.");
	}
	
	public List<Integer> viewMedicineForReplenishmentRequests(){
		List<Integer> indices = new ArrayList<>();
		int i = 1;
		int index = 0;
        if (adminController.getMedicationInventory().isEmpty()) {
            System.out.println("Inventory is empty.");
        } else {
            System.out.println("Current Inventory:");
            System.out.println("+" + "-".repeat(3) + "+"
                    + "-".repeat(30) + "+"
                    + "-".repeat(15) + "+"
                    + "-".repeat(15) + "+");

            System.out.println("|" + formatCell("No.", 3)
                    + "|" + formatCell("Medicine Name", 30)
                    + "|" + formatCell("Current Stock", 15)
                    + "|" + formatCell("Requested Amount", 15) + "|");

            System.out.println("+" + "-".repeat(3) + "+"
                    + "-".repeat(30) + "+"
                    + "-".repeat(15) + "+"
                    + "-".repeat(15) + "+");
            for (Medicine medicine : adminController.getMedicationInventory()) {
                if(medicine.getRequestAmount() > 0) {
	                	System.out.println("|" + formatCell(String.valueOf(i), 3)
	                    + "|" + formatCell(medicine.getNameOfMedicine(), 30)
	                    + "|" + formatCell(String.valueOf(medicine.getCurrentStock()), 15)
	                    + "|" + formatCell(String.valueOf(medicine.getRequestAmount()), 15)+ "|");
		            System.out.println("+" + "-".repeat(3) + "+"
		                    + "-".repeat(30) + "+"
		                    + "-".repeat(15) + "+"
		                    + "-".repeat(15) + "+");
		            i++;
		            indices.add(index);
                }
                index++;
            }
        }
		return indices;
	}




}
