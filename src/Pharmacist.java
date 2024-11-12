import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import static Boundary.FormatCell.formatCell;

public class Pharmacist extends User {

    private static final int columnWidth = 20;

    // Constructor to initialize the Pharmacist with name, hospital ID, and domain
    public Pharmacist(String name, String hospitalId, Domain domain, String gender, int age) {
        super(name, hospitalId, domain, gender, age);
    }

    // Main menu for Pharmacist
    public void homePage() {
        int choice;
        Schedule schedule = new Schedule(); // Instance of Schedule for appointment management
        MedicationInventory medicationInventory = new MedicationInventory();  // Instance of Medical Inventory
        do {
            System.out.println("\nChoose the number of functions:\n"
                    + "(1) View All Appointments\n"
                    + "(2) Manage Prescriptions\n"
                    + "(3) View Medication Inventory\n"
                    + "(4) Submit Replenishment Request\n"
                    + "(5) Logout");

            choice = InputScanner.sc.nextInt();
            InputScanner.sc.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    viewAllAppointments(schedule); // Use Schedule class to view appointments
                    break;
                case 2:
                    managePrescriptions();         // Manage prescriptions functionality
                    break;
                case 3:
                    medicationInventory.viewInventory(); // View medication inventory
                    break;
                case 4:
                    System.out.print("Enter the name of the medicine for replenishment: \n");
                    String medicineName = InputScanner.sc.nextLine().trim();
                    try {
                        Medicine targetMedicine = medicationInventory.findMedicineByName(medicineName);
                        System.out.print("Enter the amount to request for replenishment: \n");
                        int requestAmount = InputScanner.sc.nextInt();
                        InputScanner.sc.nextLine();
                        medicationInventory.submitReplenishmentRequest(targetMedicine, requestAmount); // Submit replenishment request
                    } catch (Exception e) {
                        System.out.println("Medicine not found. Please try again. \n");
                    }
                    break;
                case 5:
                    System.out.println("Logging out.\n");
                    break;
                default:
                    System.out.println("Invalid choice, please try again.\n");
            }
        } while (choice != 5);
    }


    // View all appointments using Schedule class
    private void viewAllAppointments(Schedule schedule) {
        System.out.println("\n--- Viewing All Appointments ---");
        schedule.displayAppointmentList(Schedule.getAppointmentList());
    }


    // Manage prescriptions - includes viewing, adding, searching, and updating prescriptions
    private void managePrescriptions() {
        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("d/M/yyyy");
        Schedule schedule = new Schedule();
        int choice;
        do {
            System.out.println("\n--- Manage Prescriptions ---");
            System.out.println("(1) View All Prescriptions");
            System.out.println("(2) Add New Prescription");
            System.out.println("(3) Update Prescription Status");
            System.out.println("(4) Search Prescription by ID");
            System.out.println("(5) Return to Main Menu");

            choice = InputScanner.sc.nextInt();
            InputScanner.sc.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    viewAllPrescriptions();
                    break;
                case 2:
                    System.out.print("Enter Appointment ID: ");
                    String appointmentID = InputScanner.sc.nextLine();

                    System.out.print("Enter Medicine Name: ");
                    String medicine = InputScanner.sc.nextLine();

                    System.out.print("Enter Dosage: ");
                    String dosage = InputScanner.sc.nextLine();

                    System.out.print("Enter Instructions: ");
                    String instruction = InputScanner.sc.nextLine();

                    String dateIssued = String.valueOf(LocalDate.now().format(formatterDate));
                    Appointment targetAppointment = schedule.getAppointmentByID(appointmentID);
                    addNewPrescription(targetAppointment, dateIssued, medicine, dosage, instruction);
                    schedule.updateAppointmentFile(Schedule.getAppointmentList());
                    break;
                case 3:
                    System.out.print("Enter Appointment ID: ");
                    String appID = InputScanner.sc.nextLine().trim();
                    System.out.println("Enter new Prescription Status:\n"
                            + "1. Completed\n"
                            + "2. Pending Prescription\n");
                    int statusChoice = InputScanner.sc.nextInt();
                    InputScanner.sc.nextLine();
                    updatePrescriptionStatus(schedule.getAppointmentByID(appID), statusChoice);
                    schedule.updateAppointmentFile(Schedule.getAppointmentList());
                    break;
                case 4:
                    System.out.print("Enter Appointment ID to search: ");
                    String prescriptionID = InputScanner.sc.nextLine().trim();
                    Appointment appointment = schedule.getAppointmentByID(prescriptionID);
                    printPrescription(appointment);
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

    // View all prescriptions that are pending or completed
    private void viewAllPrescriptions() {
        int i = 1;
        for (Appointment appointments : Schedule.getAppointmentList()) {
            if (appointments.getStatusOfApp() == status.PrescriptionPending || appointments.getStatusOfApp() == status.Completed) {
                printPrescription(appointments);
                i++;
            }
        }
    }

    // Add a new prescription
    private void addNewPrescription(Appointment appointment, String dateIssued, String medicine, String dosage, String instructions) {
        appointment.setLocalDate(dateIssued);
        appointment.setMedicine(medicine);
        appointment.setDosage(dosage);
        appointment.setInstructions(instructions);
        System.out.println("New prescription added successfully.");
    }

    // Prints the information of a prescription
//    private void printPrescription(Appointment appointment) {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy H:mm");
//        if (appointment == null) {
//            System.out.println("Prescription not found.");
//        } else {
//            System.out.println("|" + formatCell(appointment.getAppID(), columnWidth)
//                    + "|" + formatCell(appointment.getAppID(), columnWidth)
//                    + "|" + formatCell(appointment.getPatID(), columnWidth)
//                    + "|" + formatCell(appointment.getDocID(), columnWidth)
//                    + "|" + formatCell(appointment.getStatusOfApp().toString(), columnWidth)
//                    + "|" + formatCell(appointment.getMedicineIssuedDate().format(formatter), columnWidth)
//                    + "|" + formatCell(appointment.getDosage(), columnWidth)
//                    + "|" + formatCell(appointment.getInstructions(), columnWidth) + "|");
//            System.out.println("+" + "-".repeat(columnWidth) + "+"
//                    + "-".repeat(columnWidth) + "+"
//                    + "-".repeat(columnWidth) + "+"
//                    + "-".repeat(columnWidth) + "+"
//                    + "-".repeat(columnWidth) + "+"
//                    + "-".repeat(columnWidth) + "+"
//                    + "-".repeat(columnWidth) + "+"
//                    + "-".repeat(columnWidth) + "+");
//        }
//    }


    private void printPrescription(Appointment appointment) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy H:mm");
        if (appointment == null) {
            System.out.println("Prescription not found.");
        } else {
            // Check if medicineIssuedDate is null and handle it accordingly
            String medicineIssuedDateStr = (appointment.getMedicineIssuedDate() != null)
                    ? appointment.getMedicineIssuedDate().format(formatter)
                    : "N/A";

            System.out.println("|" + formatCell(appointment.getAppID(), columnWidth)
                    + "|" + formatCell(appointment.getAppID(), columnWidth)
                    + "|" + formatCell(appointment.getPatID(), columnWidth)
                    + "|" + formatCell(appointment.getDocID(), columnWidth)
                    + "|" + formatCell(appointment.getStatusOfApp().toString(), columnWidth)
                    + "|" + formatCell(medicineIssuedDateStr, columnWidth)
                    + "|" + formatCell(appointment.getDosage(), columnWidth)
                    + "|" + formatCell(appointment.getInstructions(), columnWidth) + "|");
            System.out.println("+" + "-".repeat(columnWidth) + "+"
                    + "-".repeat(columnWidth) + "+"
                    + "-".repeat(columnWidth) + "+"
                    + "-".repeat(columnWidth) + "+"
                    + "-".repeat(columnWidth) + "+"
                    + "-".repeat(columnWidth) + "+"
                    + "-".repeat(columnWidth) + "+"
                    + "-".repeat(columnWidth) + "+");
        }
    }


    // Update the status of an existing prescription
    private void updatePrescriptionStatus(Appointment appointment, int statusChoice) {
        if (statusChoice == 1) {
            appointment.setStatusOfApp(status.Confirmed);
        } else if (statusChoice == 2) {
            appointment.setStatusOfApp(status.PrescriptionPending);
        } else {
            System.out.println("Invalid input. Please enter 1 or 2.");
        }
    }

}






//    private List<Prescription> prescriptions; // List to store all prescriptions
//    private MedicationInventory inventory; // Access to the medication inventory
//
//    // Constructor to initialize the Pharmacist with name, hospital ID, and domain
//    public Pharmacist(String name, String hospitalId, Domain domain, String gender, int age) {
//        super(name, hospitalId, domain, gender, age);
//        this.prescriptions = new ArrayList<>();
//        //this.inventory = MedicationInventory.getInstance();
//    }
//
//    // Main menu for Pharmacist
//    public void homePage() {
//        int choice;
//        Schedule schedule = new Schedule(); // Instance of Schedule for appointment management
//
//        do {
//            System.out.println("\nChoose the number of functions:\n"
//                    + "(1) View All Appointments\n"
//                    + "(2) Manage Prescriptions\n"
//                    + "(3) View Medication Inventory\n"
//                    + "(4) Submit Replenishment Request\n"
//                    + "(5) Logout");
//
//            choice = InputScanner.sc.nextInt();
//            InputScanner.sc.nextLine();  // Consume newline
//
//            switch (choice) {
//                case 1 -> viewAllAppointments(schedule); // Use Schedule class to view appointments
//                case 2 -> managePrescriptions();         // Manage prescriptions functionality
//                case 3 -> viewMedicationInventory();      // View medication inventory
//                case 4 -> submitReplenishmentRequest();   // Submit replenishment request
//                case 5 -> System.out.println("Logging out.");
//                default -> System.out.println("Invalid choice, please try again.");
//            }
//        } while (choice != 5);
//    }
//
//    // View all appointments using Schedule class
//    private void viewAllAppointments(Schedule schedule) {
//        System.out.println("\n--- Viewing All Appointments ---");
//        schedule.displayAppointmentList(Schedule.getAppointmentList());
//    }
//
//    // Manage prescriptions - includes viewing, adding, searching, and updating prescriptions
//    private void managePrescriptions() {
//        int choice;
//        do {
//            System.out.println("\n--- Manage Prescriptions ---");
//            System.out.println("(1) View All Prescriptions");
//            System.out.println("(2) Add New Prescription");
//            System.out.println("(3) Update Prescription Status");
//            System.out.println("(4) Search Prescription by ID");
//            System.out.println("(5) Return to Main Menu");
//
//            choice = InputScanner.sc.nextInt();
//            InputScanner.sc.nextLine(); // Consume newline
//
//            switch (choice) {
//                case 1 -> viewAllPrescriptions();
//                case 2 -> addNewPrescription();
//                case 3 -> updatePrescriptionStatus();
//                case 4 -> searchPrescriptionByID();
//                case 5 -> System.out.println("Returning to Main Menu.");
//                default -> System.out.println("Invalid choice, please try again.");
//            }
//        } while (choice != 5);
//    }
//
//    // View all prescriptions
//    private void viewAllPrescriptions() {
//        if (prescriptions.isEmpty()) {
//            System.out.println("No prescriptions available.");
//            return;
//        }
//
//        System.out.println("\n--- Viewing All Prescriptions ---");
//        for (Prescription prescription : prescriptions) {
//            System.out.println("Prescription ID: " + prescription.getPrescriptionID() +
//                    ", Patient ID: " + prescription.getPatientID() +
//                    ", Doctor ID: " + prescription.getDoctorID() +
//                    ", Date Issued: " + prescription.getDateIssued() +
//                    ", Status: " + prescription.getStatus() +
//                    ", Medicine: " + prescription.getMedicine() +
//                    ", Dosage: " + prescription.getDosage() +
//                    ", Instruction: " + prescription.getInstruction());
//        }
//    }
//
//    // Add a new prescription
//    private void addNewPrescription() {
//        Scanner scanner = new Scanner(System.in);
//
//        System.out.print("Enter Prescription ID: ");
//        String prescriptionID = scanner.nextLine();
//
//        System.out.print("Enter Patient ID: ");
//        String patientID = scanner.nextLine();
//
//        System.out.print("Enter Doctor ID: ");
//        String doctorID = scanner.nextLine();
//
//        System.out.print("Enter Medicine Name: ");
//        String medicine = scanner.nextLine();
//
//        System.out.print("Enter Dosage: ");
//        String dosage = scanner.nextLine();
//
//        System.out.print("Enter Instructions: ");
//        String instruction = scanner.nextLine();
//
//        LocalDate dateIssued = LocalDate.now(); // Set current date as issued date
//        Prescription prescription = new Prescription(prescriptionID, patientID, doctorID, dateIssued, medicine, dosage, instruction);
//        prescriptions.add(prescription);
//
//        System.out.println("New prescription added successfully.");
//    }
//
//    // Update the status of an existing prescription
//    private void updatePrescriptionStatus() {
//        Scanner scanner = new Scanner(System.in);
//
//        System.out.print("Enter Prescription ID to update: ");
//        String prescriptionID = scanner.nextLine();
//
//        Prescription prescription = findPrescriptionByID(prescriptionID);
//        if (prescription == null) {
//            System.out.println("Prescription ID not found.");
//            return;
//        }
//
//        prescription.updateStatus();
//    }
//
//    // Search and display prescription by ID
//    private void searchPrescriptionByID() {
//        Scanner scanner = new Scanner(System.in);
//
//        System.out.print("Enter Prescription ID to search: ");
//        String prescriptionID = scanner.nextLine();
//
//        Prescription prescription = findPrescriptionByID(prescriptionID);
//        if (prescription == null) {
//            System.out.println("Prescription ID not found.");
//        } else {
//            System.out.println("Prescription ID: " + prescription.getPrescriptionID() +
//                    ", Patient ID: " + prescription.getPatientID() +
//                    ", Doctor ID: " + prescription.getDoctorID() +
//                    ", Date Issued: " + prescription.getDateIssued() +
//                    ", Status: " + prescription.getStatus() +
//                    ", Medicine: " + prescription.getMedicine() +
//                    ", Dosage: " + prescription.getDosage() +
//                    ", Instruction: " + prescription.getInstruction());
//        }
//    }
//
//    // Helper method to find a prescription by ID
//    private Prescription findPrescriptionByID(String prescriptionID) {
//        for (Prescription prescription : prescriptions) {
//            if (prescription.getPrescriptionID().equals(prescriptionID)) {
//                return prescription;
//            }
//        }
//        return null;
//    }
//
//    // View medication inventory
//    private void viewMedicationInventory() {
//        System.out.println("\n--- Medication Inventory ---");
//        inventory.viewInventory();
//    }
//
//    // Submit a replenishment request for a specific medicine
//    private void submitReplenishmentRequest() {
//        Scanner scanner = new Scanner(System.in);
//
//        System.out.print("Enter the name of the medicine for replenishment: ");
//        String medicineName = scanner.nextLine();
//
//        if (!inventory.isMedicineInInventory(medicineName)) {
//            System.out.println("Medicine not found in the inventory.");
//            return;
//        }
//
//        System.out.print("Enter the amount to request for replenishment: ");
//        int requestAmount = scanner.nextInt();
//        scanner.nextLine(); // Consume newline
//
//        inventory.submitReplenishmentRequest(medicineName, requestAmount);
//        System.out.println("Replenishment request submitted for " + requestAmount + " units of " + medicineName + ".");
//    }
//}
