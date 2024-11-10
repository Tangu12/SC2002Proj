import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Pharmacist extends User {

    private List<Prescription> prescriptions; // List to store all prescriptions
    private MedicationInventory inventory; // Access to the medication inventory

    // Constructor to initialize the Pharmacist with name, hospital ID, and domain
    public Pharmacist(String name, String hospitalId, Domain domain, String gender, int age) {
        super(name, hospitalId, domain, gender, age);
        this.prescriptions = new ArrayList<>();
        //this.inventory = MedicationInventory.getInstance();
    }

    // Main menu for Pharmacist
    public void homePage() {
        int choice;
        Schedule schedule = new Schedule(); // Instance of Schedule for appointment management

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
                case 1 -> viewAllAppointments(schedule); // Use Schedule class to view appointments
                case 2 -> managePrescriptions();         // Manage prescriptions functionality
                case 3 -> viewMedicationInventory();      // View medication inventory
                case 4 -> submitReplenishmentRequest();   // Submit replenishment request
                case 5 -> System.out.println("Logging out.");
                default -> System.out.println("Invalid choice, please try again.");
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
                case 1 -> viewAllPrescriptions();
                case 2 -> addNewPrescription();
                case 3 -> updatePrescriptionStatus();
                case 4 -> searchPrescriptionByID();
                case 5 -> System.out.println("Returning to Main Menu.");
                default -> System.out.println("Invalid choice, please try again.");
            }
        } while (choice != 5);
    }

    // View all prescriptions
    private void viewAllPrescriptions() {
        if (prescriptions.isEmpty()) {
            System.out.println("No prescriptions available.");
            return;
        }
        
        System.out.println("\n--- Viewing All Prescriptions ---");
        for (Prescription prescription : prescriptions) {
            System.out.println("Prescription ID: " + prescription.getPrescriptionID() +
                    ", Patient ID: " + prescription.getPatientID() +
                    ", Doctor ID: " + prescription.getDoctorID() +
                    ", Date Issued: " + prescription.getDateIssued() +
                    ", Status: " + prescription.getStatus() +
                    ", Medicine: " + prescription.getMedicine() +
                    ", Dosage: " + prescription.getDosage() +
                    ", Instruction: " + prescription.getInstruction());
        }
    }

    // Add a new prescription
    private void addNewPrescription() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Prescription ID: ");
        String prescriptionID = scanner.nextLine();

        System.out.print("Enter Patient ID: ");
        String patientID = scanner.nextLine();

        System.out.print("Enter Doctor ID: ");
        String doctorID = scanner.nextLine();

        System.out.print("Enter Medicine Name: ");
        String medicine = scanner.nextLine();

        System.out.print("Enter Dosage: ");
        String dosage = scanner.nextLine();

        System.out.print("Enter Instructions: ");
        String instruction = scanner.nextLine();

        LocalDate dateIssued = LocalDate.now(); // Set current date as issued date
        Prescription prescription = new Prescription(prescriptionID, patientID, doctorID, dateIssued, medicine, dosage, instruction);
        prescriptions.add(prescription);

        System.out.println("New prescription added successfully.");
    }

    // Update the status of an existing prescription
    private void updatePrescriptionStatus() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Prescription ID to update: ");
        String prescriptionID = scanner.nextLine();

        Prescription prescription = findPrescriptionByID(prescriptionID);
        if (prescription == null) {
            System.out.println("Prescription ID not found.");
            return;
        }

        prescription.updateStatus();
    }

    // Search and display prescription by ID
    private void searchPrescriptionByID() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Prescription ID to search: ");
        String prescriptionID = scanner.nextLine();

        Prescription prescription = findPrescriptionByID(prescriptionID);
        if (prescription == null) {
            System.out.println("Prescription ID not found.");
        } else {
            System.out.println("Prescription ID: " + prescription.getPrescriptionID() +
                    ", Patient ID: " + prescription.getPatientID() +
                    ", Doctor ID: " + prescription.getDoctorID() +
                    ", Date Issued: " + prescription.getDateIssued() +
                    ", Status: " + prescription.getStatus() +
                    ", Medicine: " + prescription.getMedicine() +
                    ", Dosage: " + prescription.getDosage() +
                    ", Instruction: " + prescription.getInstruction());
        }
    }

    // Helper method to find a prescription by ID
    private Prescription findPrescriptionByID(String prescriptionID) {
        for (Prescription prescription : prescriptions) {
            if (prescription.getPrescriptionID().equals(prescriptionID)) {
                return prescription;
            }
        }
        return null;
    }

    // View medication inventory
    private void viewMedicationInventory() {
        System.out.println("\n--- Medication Inventory ---");
        inventory.viewInventory();
    }

    // Submit a replenishment request for a specific medicine
    private void submitReplenishmentRequest() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the name of the medicine for replenishment: ");
        String medicineName = scanner.nextLine();

        if (!inventory.isMedicineInInventory(medicineName)) {
            System.out.println("Medicine not found in the inventory.");
            return;
        }

        System.out.print("Enter the amount to request for replenishment: ");
        int requestAmount = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        inventory.submitReplenishmentRequest(medicineName, requestAmount);
        System.out.println("Replenishment request submitted for " + requestAmount + " units of " + medicineName + ".");
    }
}
