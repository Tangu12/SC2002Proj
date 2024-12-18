package Boundary.UserUI;


import Controllers.PharmacistController;
import Entity.Appointment;
import Entity.AppointmentList;
import Entity.MedicationInventory;
import Entity.Medicine;
import Entity.Enums.Status;
import Services.InputService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class PharmacistMainPage extends UserMainPage{
    private PharmacistController pharmacistController;
    private static int columnWidth = 20;
    
    public PharmacistMainPage(PharmacistController pharmacistController) {
        this.pharmacistController = pharmacistController;
    }

    public void homePage(){
        int choice;
        do {
			System.out.println(" ____  _                                     _     _   \n" +
					"|  _ \\| |__   __ _ _ __ _ __ ___   __ _  ___(_)___| |_ \n" +
					"| |_) | '_ \\ / _` | '__| '_ ` _ \\ / _` |/ __| / __| __|\n" +
					"|  __/| | | | (_| | |  | | | | | | (_| | (__| \\__ \\ |_ \n" +
					"|_|   |_| |_|\\__,_|_|  |_| |_| |_|\\__,_|\\___|_|___/\\__|");

            System.out.println("\nChoose the number of functions:\n"
                    + "(1) View Appointment Outcome Record\n"
                    + "(2) Manage Prescriptions\n"
                    + "(3) View Medication Inventory\n"
                    + "(4) Submit Replenishment Request\n"
                    + "(5) Logout\n"
            );
            choice = InputService.inputInteger();
            switch (choice) {
                case 1 -> viewAppointmentOutcomeRecord();
                case 2 -> updatePrescriptionStatus();
                case 3 -> viewMedicationInventory();
                case 4 -> submitReplenishmentRequest();
                case 5 -> System.out.println("Logging out.");
                default -> System.out.println("Invalid choice, please try again.");
            }
        } while (choice != 5);
    }

    public void viewAppointmentOutcomeRecord(){
    	if (AppointmentList.getInstance().getAppointmentList() == null || AppointmentList.getInstance().getAppointmentList().isEmpty()) {
			System.out.println("No appointments are found.");
			return;
		}

		// Print table header
		System.out.println("+" + "-".repeat(columnWidth) + "+" +
				"-".repeat(columnWidth) + "+" + "-".repeat(columnWidth) + "+" +
				"-".repeat(columnWidth) + "+" + "-".repeat(columnWidth) + "+" +
				"-".repeat(columnWidth) + "+" + "-".repeat(columnWidth) + "+" +
				"-".repeat(columnWidth) + "+" + "-".repeat(columnWidth) + "+" + "-".repeat(50) + "+" +
				"-".repeat(50) + "+" + "-".repeat(columnWidth) + "+" +
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
				+ "|" + formatCell("Medicine", 50)
				+ "|" + formatCell("Issued Date", columnWidth)
				+ "|" + formatCell("Dosage", columnWidth)
				+ "|" + formatCell("Instructions", 30) + "|");

		System.out.println("+" + "-".repeat(columnWidth) + "+" +
				"-".repeat(columnWidth) + "+" + "-".repeat(columnWidth) + "+" +
				"-".repeat(columnWidth) + "+" + "-".repeat(columnWidth) + "+" +
				"-".repeat(columnWidth) + "+" + "-".repeat(columnWidth) + "+" +
				"-".repeat(columnWidth) + "+" + "-".repeat(columnWidth) + "+" + "-".repeat(50) + "+" +
				"-".repeat(50) + "+" + "-".repeat(columnWidth) + "+" +
				"-".repeat(columnWidth) + "+" + "-".repeat(30) + "+");

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy H:mm");
		DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("d/M/yyyy");
		for (Appointment row : AppointmentList.getInstance().getAppointmentList()) {
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
			+ "|" + formatCell(row.getMedicine(), 50)
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
					+ "-".repeat(50) + "+"
					+ "-".repeat(columnWidth) + "+"
					+ "-".repeat(columnWidth) + "+"
					+ "-".repeat(30) + "+");
		}
    }
    
    public void updatePrescriptionStatus(){
    	DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("d/M/yyyy");
        int choice;
        do {
            System.out.println("\n--- Manage Prescriptions ---");
            System.out.println("(1) View All Prescriptions");
            System.out.println("(2) Update Prescription Status");
            System.out.println("(3) Search Prescription by ID");
            System.out.println("(4) Dispense Medicine");
            System.out.println("(5) Return to Main Menu");

            choice = InputService.inputInteger();

            switch (choice) {
                case 1:
                    viewAllPrescriptions();
                    break;
                case 2:
                		List<Integer> pendingPresciptionAppointmentsIndices1 = viewPendingPresciptionAppointments();
                		if(pendingPresciptionAppointmentsIndices1.isEmpty()) break;
                    System.out.print("Select the Appointment ID you want to update: ");
                    int appSelection1 = InputService.inputInteger();
                    if(appSelection1-1 >= pendingPresciptionAppointmentsIndices1.size() || appSelection1 <= 0) {System.out.println("Please only enter the available options"); return;}
                    System.out.println("Changing prescription status to 'Completed' (Y/N): ");
                    String changeStatus = InputService.inputString();
                    if(changeStatus.equals("Y")) {
                    		pharmacistController.updatePrescriptionStatus(pharmacistController.getAppointmentByID(AppointmentList.getInstance().getAppointmentList().get(pendingPresciptionAppointmentsIndices1.get(appSelection1-1)).getAppID()));
                    		System.out.println("Status is updated to Completed.");
                    }
                    else System.out.println("Prescription status is not changed.");
                    break;
                case 3:
                		System.out.print("Enter Appointment ID to search: ");
                    String prescriptionID = InputService.inputString().trim();
                    Appointment appointment = pharmacistController.getAppointmentByID(prescriptionID);
                    if(appointment == null) System.out.println("The appointment ID does not exist.");
                    else {
                    	System.out.println("+" + "-".repeat(columnWidth) + "+" +
            					"-".repeat(columnWidth) + "+" + "-".repeat(columnWidth) + "+" +
            					"-".repeat(columnWidth) + "+" + "-".repeat(50) + "+" + "-".repeat(50) + "+" +
            					"-".repeat(columnWidth) + "+" + "-".repeat(columnWidth) + "+" +
            					"-".repeat(30) + "+");

            			System.out.println("|" + formatCell("Appointment ID", columnWidth)
            					+ "|" + formatCell("Patient Name", columnWidth)
            					+ "|" + formatCell("Doctor Name", columnWidth)
            					+ "|" + formatCell("Status", columnWidth)
            					+ "|" + formatCell("Appointment Outcomes", 50)
            					+ "|" + formatCell("Medicine", 50)
            					+ "|" + formatCell("Issued Date", columnWidth)
            					+ "|" + formatCell("Dosage", columnWidth)
            					+ "|" + formatCell("Instructions", 30) + "|");

            			System.out.println("+" + "-".repeat(columnWidth) + "+" +
            					"-".repeat(columnWidth) + "+" + "-".repeat(columnWidth) + "+" +
            					"-".repeat(columnWidth) + "+" + "-".repeat(50) + "+" + "-".repeat(50) + "+" +
            					"-".repeat(columnWidth) + "+" + "-".repeat(columnWidth) + "+" +
            					"-".repeat(30) + "+");
                    	printPrescription(appointment);
                    }
                    break;
                case 4:
                		dispenseMedicine();
                		break;
                case 5:
                    System.out.println("Returning to Main Menu.");
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
                    break;
            }
        } while (choice != 5);
    }

    /*
    Prints out a Medication Inventory Table
    */
    public void viewMedicationInventory(){
        int i = 1;

        if (pharmacistController.getMedicationInventory().isEmpty()) {
            System.out.println("Inventory is empty.");
        } else {
            System.out.println("Current Inventory:");
            System.out.println("+" + "-".repeat(3) + "+"
                    + "-".repeat(30) + "+"
                    + "-".repeat(15) + "+"
					+ "-".repeat(15) + "+"
                    + "-".repeat(30) + "+");

            System.out.println("|" + formatCell("No.", 3)
                    + "|" + formatCell("Medicine Name", 30)
                    + "|" + formatCell("Current Stock", 15)
                    + "|" + formatCell("Request Amount", 15)
					+ "|" + formatCell("Low Level Stock Alert", 30)+ "|");

            System.out.println("+" + "-".repeat(3) + "+"
                    + "-".repeat(30) + "+"
                    + "-".repeat(15) + "+"
					+ "-".repeat(15) + "+"
                    + "-".repeat(30) + "+");
            for (Medicine medicine : pharmacistController.getMedicationInventory()) {
                //System.out.println(i + ". " + medicine);
                System.out.println("|" + formatCell(String.valueOf(i), 3)
                        + "|" + formatCell(medicine.getNameOfMedicine(), 30)
                        + "|" + formatCell(String.valueOf(medicine.getCurrentStock()), 15)
                        + "|" + formatCell(String.valueOf(medicine.getRequestAmount()), 15)
						+ "|" + formatCell(String.valueOf(medicine.getLowStockLevelAlert()), 30)+ "|");
                System.out.println("+" + "-".repeat(3) + "+"
                        + "-".repeat(30) + "+"
                        + "-".repeat(15) + "+"
                        + "-".repeat(15) + "+"
						+ "-".repeat(30) + "+");
                i++;
            }
        }
    }

    /*
    Updates the replenishment column in the MedicationInventory File
    */
    public void submitReplenishmentRequest(){
			viewMedicationInventory();
        	String medicine;
        	System.out.print("Select the Medicine for replenishment (-1 to exit): ");
        	int medSelection = InputService.inputInteger();
        	if(medSelection == -1) return;;
        	if(medSelection <= 0 || medSelection > MedicationInventory.getInventory().size()) {System.out.println("Not available medicine."); return;}
        	else medicine = MedicationInventory.getInventory().get(medSelection - 1).getNameOfMedicine();
        	
        try {
            System.out.print("Enter the amount to request for replenishment: \n");
            int requestAmount = InputService.inputInteger();
           pharmacistController.submitReplenishmentRequest(medicine, requestAmount); // Submit replenishment request
        } catch (Exception e) {
            System.out.println("Medicine not found. Please try again. \n");
        }
    }

    private static String formatCell(String value, int width) {
        if (value == " ") {
            value = "";
        }
        return String.format("%-" + width + "s", value);  // Left-align the text within the specified width
    }
    
    private void viewAllPrescriptions() {
    	
    	// Print table header
    			System.out.println("+" + "-".repeat(columnWidth) + "+" +
    					"-".repeat(columnWidth) + "+" + "-".repeat(columnWidth) + "+" +
    					"-".repeat(columnWidth) + "+" + "-".repeat(50) + "+" + "-".repeat(50) + "+" +
    					"-".repeat(columnWidth) + "+" + "-".repeat(columnWidth) + "+" +
    					"-".repeat(30) + "+");

    			System.out.println("|" + formatCell("Appointment ID", columnWidth)
    					+ "|" + formatCell("Patient Name", columnWidth)
    					+ "|" + formatCell("Doctor Name", columnWidth)
    					+ "|" + formatCell("Status", columnWidth)
    					+ "|" + formatCell("Appointment Outcomes", 50)
    					+ "|" + formatCell("Medicine", 50)
    					+ "|" + formatCell("Issued Date", columnWidth)
    					+ "|" + formatCell("Dosage", columnWidth)
    					+ "|" + formatCell("Instructions", 30) + "|");

    			System.out.println("+" + "-".repeat(columnWidth) + "+" +
    					"-".repeat(columnWidth) + "+" + "-".repeat(columnWidth) + "+" +
    					"-".repeat(columnWidth) + "+" + "-".repeat(50) + "+" + "-".repeat(50) + "+" +
    					"-".repeat(columnWidth) + "+" + "-".repeat(columnWidth) + "+" +
    					"-".repeat(30) + "+");
    	
        for (Appointment appointments : AppointmentList.getInstance().getAppointmentList()) {
            if (appointments.getStatusOfApp() == Status.PendingPrescription || appointments.getStatusOfApp() == Status.Completed) {
                printPrescription(appointments);
            }
        }
    }
    
    private void printPrescription(Appointment appointment) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        if (appointment == null) {
            System.out.println("Prescription not found.");
        } else {
            // Check if medicineIssuedDate is null and handle it accordingly
            String medicineIssuedDateStr = (appointment.getMedicineIssuedDate() != null)
                    ? appointment.getMedicineIssuedDate().format(formatter)
                    : "N/A";

            System.out.println("|" + formatCell(appointment.getAppID(), columnWidth)
                    + "|" + formatCell(appointment.getPatName(), columnWidth)
                    + "|" + formatCell(appointment.getDocName(), columnWidth)
                    + "|" + formatCell(appointment.getStatusOfApp().toString(), columnWidth)
                    + "|" + formatCell(appointment.getAppointOutcomeRecord(), 50)
                    + "|" + formatCell(appointment.getMedicine(), 50)
                    + "|" + formatCell(medicineIssuedDateStr, columnWidth)
                    + "|" + formatCell(appointment.getDosage(), columnWidth)
                    + "|" + formatCell(appointment.getInstructions(), 30) + "|");
            System.out.println("+" + "-".repeat(columnWidth) + "+"
                    + "-".repeat(columnWidth) + "+"
                    + "-".repeat(columnWidth) + "+"
                    + "-".repeat(columnWidth) + "+"
                    + "-".repeat(50) + "+"
                    + "-".repeat(50) + "+"
                    + "-".repeat(columnWidth) + "+"
                    + "-".repeat(columnWidth) + "+"
                    + "-".repeat(30) + "+");
        }
    }
    
    public List<Integer> viewPresciptionAppointments() {
        int i = 1;
        int index = 0;
        List<Integer> pendingIndices = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        
        System.out.println("+" + "-".repeat(3) + "+" + "-".repeat(columnWidth) + "+" +
				"-".repeat(columnWidth) + "+" + "-".repeat(columnWidth) + "+" +
				"-".repeat(columnWidth) + "+" + "-".repeat(50) + "+" + "-".repeat(50) + "+" +
				"-".repeat(columnWidth) + "+" + "-".repeat(columnWidth) + "+" +
				"-".repeat(30) + "+");

		System.out.println( "|" + formatCell("No.", 3)
				+ "|" + formatCell("Appointment ID", columnWidth)
				+ "|" + formatCell("Patient Name", columnWidth)
				+ "|" + formatCell("Doctor Name", columnWidth)
				+ "|" + formatCell("Status", columnWidth)
				+ "|" + formatCell("Appointment Outcomes", 50)
				+ "|" + formatCell("Medicine", 50)
				+ "|" + formatCell("Issued Date", columnWidth)
				+ "|" + formatCell("Dosage", columnWidth)
				+ "|" + formatCell("Instructions", 30) + "|");

		System.out.println("+" + "-".repeat(3) + "+" + "-".repeat(columnWidth) + "+" +
				"-".repeat(columnWidth) + "+" + "-".repeat(columnWidth) + "+" +
				"-".repeat(columnWidth) + "+" + "-".repeat(50) + "+" + "-".repeat(50) + "+" +
				"-".repeat(columnWidth) + "+" + "-".repeat(columnWidth) + "+" +
				"-".repeat(30) + "+");
		
        for (Appointment appointment : AppointmentList.getInstance().getAppointmentList()) {
            if (appointment.getStatusOfApp() == Status.PendingPrescription || appointment.getStatusOfApp() == Status.Completed) {
            	String medicineIssuedDateStr = (appointment.getMedicineIssuedDate() != null)
                        ? appointment.getMedicineIssuedDate().format(formatter)
                        : "N/A";

                System.out.println( "|" + formatCell(String.valueOf(i), 3)
                		+ "|" + formatCell(appointment.getAppID(), columnWidth)
                        + "|" + formatCell(appointment.getPatName(), columnWidth)
                        + "|" + formatCell(appointment.getDocName(), columnWidth)
                        + "|" + formatCell(appointment.getStatusOfApp().toString(), columnWidth)
                        + "|" + formatCell(appointment.getAppointOutcomeRecord(), 50)
                        + "|" + formatCell(appointment.getMedicine(), 50)
                        + "|" + formatCell(medicineIssuedDateStr, columnWidth)
                        + "|" + formatCell(appointment.getDosage(), columnWidth)
                        + "|" + formatCell(appointment.getInstructions(), 30) + "|");
                System.out.println( "+" + "-".repeat(3)
                		+ "+" + "-".repeat(columnWidth) + "+"
                        + "-".repeat(columnWidth) + "+"
                        + "-".repeat(columnWidth) + "+"
                        + "-".repeat(columnWidth) + "+"
                        + "-".repeat(50) + "+"
                        + "-".repeat(50) + "+"
                        + "-".repeat(columnWidth) + "+"
                        + "-".repeat(columnWidth) + "+"
                        + "-".repeat(30) + "+");
                i++;
                pendingIndices.add(index);
            }
            index++;
        }
        return pendingIndices;
    }
    
    public List<Integer> viewPendingPresciptionAppointments() {
        int i = 1;
        int index = 0;
        List<Integer> pendingIndices = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        
        System.out.println("+" + "-".repeat(3) + "+" + "-".repeat(columnWidth) + "+" +
				"-".repeat(columnWidth) + "+" + "-".repeat(columnWidth) + "+" +
				"-".repeat(columnWidth) + "+" + "-".repeat(50) + "+" + "-".repeat(50) + "+" +
				"-".repeat(columnWidth) + "+" + "-".repeat(columnWidth) + "+" +
				"-".repeat(30) + "+");

		System.out.println( "|" + formatCell("No.", 3)
				+ "|" + formatCell("Appointment ID", columnWidth)
				+ "|" + formatCell("Patient Name", columnWidth)
				+ "|" + formatCell("Doctor Name", columnWidth)
				+ "|" + formatCell("Status", columnWidth)
				+ "|" + formatCell("Appointment Outcomes", 50)
				+ "|" + formatCell("Medicine", 50)
				+ "|" + formatCell("Issued Date", columnWidth)
				+ "|" + formatCell("Dosage", columnWidth)
				+ "|" + formatCell("Instructions", 30) + "|");

		System.out.println("+" + "-".repeat(3) + "+" + "-".repeat(columnWidth) + "+" +
				"-".repeat(columnWidth) + "+" + "-".repeat(columnWidth) + "+" +
				"-".repeat(columnWidth) + "+" + "-".repeat(50) + "+" + "-".repeat(50) + "+" +
				"-".repeat(columnWidth) + "+" + "-".repeat(columnWidth) + "+" +
				"-".repeat(30) + "+");
		
        for (Appointment appointment : AppointmentList.getInstance().getAppointmentList()) {
            if (appointment.getStatusOfApp() == Status.PendingPrescription) {
            	String medicineIssuedDateStr = (appointment.getMedicineIssuedDate() != null)
                        ? appointment.getMedicineIssuedDate().format(formatter)
                        : "N/A";

                System.out.println( "|" + formatCell(String.valueOf(i), 3)
                		+ "|" + formatCell(appointment.getAppID(), columnWidth)
                        + "|" + formatCell(appointment.getPatName(), columnWidth)
                        + "|" + formatCell(appointment.getDocName(), columnWidth)
                        + "|" + formatCell(appointment.getStatusOfApp().toString(), columnWidth)
                        + "|" + formatCell(appointment.getAppointOutcomeRecord(), 50)
                        + "|" + formatCell(appointment.getMedicine(), 50)
                        + "|" + formatCell(medicineIssuedDateStr, columnWidth)
                        + "|" + formatCell(appointment.getDosage(), columnWidth)
                        + "|" + formatCell(appointment.getInstructions(), 30) + "|");
                System.out.println( "+" + "-".repeat(3)
                		+ "+" + "-".repeat(columnWidth) + "+"
                        + "-".repeat(columnWidth) + "+"
                        + "-".repeat(columnWidth) + "+"
                        + "-".repeat(columnWidth) + "+"
                        + "-".repeat(50) + "+"
                        + "-".repeat(50) + "+"
                        + "-".repeat(columnWidth) + "+"
                        + "-".repeat(columnWidth) + "+"
                        + "-".repeat(30) + "+");
                i++;
                pendingIndices.add(index);
            }
            index++;
        }
        
        if(pendingIndices.size() == 0) System.out.println("There is no appointment waiting for prescription.");
        return pendingIndices;
    }
    
    public void viewAvailableMed() {
    	int i = 1;
		if (MedicationInventory.getInventory().isEmpty()) {
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
					+ "|" + formatCell("Low Stock Alert", 15) + "|");

			System.out.println("+" + "-".repeat(3) + "+"
					+ "-".repeat(30) + "+"
					+ "-".repeat(15) + "+"
					+ "-".repeat(15) + "+");

			for (Medicine medicine : MedicationInventory.getInventory()) {
				System.out.println("|" + formatCell(String.valueOf(i), 3)
						+ "|" + formatCell(medicine.getNameOfMedicine(), 30)
						+ "|" + formatCell(String.valueOf(medicine.getCurrentStock()), 15)
						+ "|" + formatCell(String.valueOf(medicine.getLowStockLevelAlert()), 15) + "|");

				System.out.println("+" + "-".repeat(3) + "+"
						+ "-".repeat(30) + "+"
						+ "-".repeat(15) + "+"
						+ "-".repeat(15) + "+");
				i++;
			}
		}
    }
    
    // Use a specific amount of medicine
    public void dispenseMedicine() {
    		viewAvailableMed();
			System.out.print("Select the index of medicine to use: ");
			int choice = InputService.inputInteger();
			if (choice > MedicationInventory.getInventory().size() || choice <= 0) {
				System.out.println("Please enter the available choices!");
				return;
			}

		Medicine medicine = pharmacistController.findMedicineByName(MedicationInventory.getInventory().get(choice-1).getNameOfMedicine());
        if (medicine == null) {
            System.out.println("Medicine not found.");
            return;
        }

        System.out.print("Enter amount used: ");
        int amount = InputService.inputInteger();
        if (amount <= medicine.getCurrentStock()) {
        		pharmacistController.decrementStock(medicine, amount);
            System.out.println("Used " + amount + " units of " + MedicationInventory.getInventory().get(choice-1).getNameOfMedicine() + ".");
        } else {
            System.out.println("Insufficient stock.");
        }
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
}
