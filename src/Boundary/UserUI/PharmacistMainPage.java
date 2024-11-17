package Boundary.UserUI;


import Controllers.PharmacistController;
import Entity.Appointment;
import Entity.AppointmentList;
import Entity.MedicationInventory;
import Entity.Medicine;
import Entity.Enums.Status;
import Services.AppointmentService;
import Services.InputService;
import Services.MedicalInventoryService;
import Utils.clearScreen;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PharmacistMainPage extends UserMainPage{
    private PharmacistController pharmacistController;
    private static int columnWidth = 20;
    
    public PharmacistMainPage(PharmacistController pharmacistController) {
        this.pharmacistController = pharmacistController;
    }

    public void homePage(){
        int choice;
        do {
            System.out.println("\nChoose the number of functions:\n"
                    + "(1) View Appointment Outcome Record\n"
                    + "(2) Update Prescription Status\n"
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
            System.out.println("(2) Update Prescription");
            System.out.println("(3) Update Prescription Status");
            System.out.println("(4) Search Prescription by ID");
            System.out.println("(5) Return to Main Menu");

            choice = InputService.inputInteger();

            switch (choice) {
                case 1:
                    viewAllPrescriptions();
                    break;
                case 2:
                		List<Integer> pendingPresciptionAppointmentsIndices = viewPresciptionAppointments();
                    System.out.print("Select the index of the Appointment ID you want to update (e.g, 1): ");
                    int appSelection = InputService.inputInteger();
                    if(appSelection-1 >= pendingPresciptionAppointmentsIndices.size()) {System.out.println("Please only enter the available options"); return;}
                    int medSelection;
                    String medicine = " ";
                    do {
                    	viewAvailableMed();
                    	System.out.print("Select the Medicine (-1 to end): ");
                    	medSelection = InputService.inputInteger();
                    	if(medSelection == -1) break;
                    	else if(medSelection <= 0 || medSelection > MedicationInventory.getInventory().size()) System.out.println("Not available medicine.");
                    	else {
                    		if(medicine == " ") medicine = MedicationInventory.getInventory().get(medSelection - 1).getNameOfMedicine();
                    		else medicine = medicine + "/" + MedicationInventory.getInventory().get(medSelection - 1).getNameOfMedicine();
                    	}
                        
                    } while(medSelection != -1);

                    System.out.print("Enter Dosage: ");
                    String dosage = InputService.inputString();

                    System.out.print("Enter Instructions: ");
                    String instruction = InputService.inputString();

                    String dateIssued = String.valueOf(LocalDate.now().format(formatterDate));
                    Appointment targetAppointment = pharmacistController.getAppointmentByID(AppointmentList.getInstance().getAppointmentList().get(pendingPresciptionAppointmentsIndices.get(appSelection-1)).getAppID());
                    pharmacistController.addNewPrescription(targetAppointment, dateIssued, medicine, dosage, instruction);
                    break;
                case 3:
                		List<Integer> pendingPresciptionAppointmentsIndices1 = viewPresciptionAppointments();
                    System.out.print("Select the Appointment ID you want to update: ");
                    int appSelection1 = InputService.inputInteger();
                    if(appSelection1-1 >= pendingPresciptionAppointmentsIndices1.size()) {System.out.println("Please only enter the available options"); return;}
                    System.out.println("Enter new Prescription Status:\n"
                            + "1. Completed\n"
                            + "2. Pending Prescription\n");
                    int statusChoice = InputService.inputInteger();
                    pharmacistController.updatePrescriptionStatus(pharmacistController.getAppointmentByID(AppointmentList.getInstance().getAppointmentList().get(pendingPresciptionAppointmentsIndices1.get(appSelection1-1)).getAppID()), statusChoice);
                    break;
                case 4:
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
}
