import java.util.List;
import java.util.ArrayList;

public class Administrator extends User {

    private MedicationInventory inventory;
    
    public Administrator(String name, String hospitalId, Domain domain, String gender, int age) {
        super(name, hospitalId, domain, gender, age);
        this.inventory = new MedicationInventory();
    }

    public void homePage() {
        int choice;
        do {
            System.out.println("\nChoose the number of functions:\n"
                    + "(1) View and Manage Hospital Staff\n"
                    + "(2) View Appointment Details\n"
                    + "(3) Manage Inventory System\n"
                    + "(4) Logout");

            choice = InputScanner.sc.nextInt();
            InputScanner.sc.nextLine();

            switch (choice) {
                case 1 -> viewAndManageHospitalStaff();
                case 2 -> viewAppointmentDetails();
                case 3 -> manageInventorySystem();
                case 4 -> System.out.println("Logging out.");
                default -> System.out.println("Invalid choice, please try again.");
            }
        } while (choice != 4);
    }

    // Manage Hospital Staff
    private void viewAndManageHospitalStaff() {
        HospitalStaff hospitalStaff = new HospitalStaff();
        int choice;

        do {
            System.out.println("\n--- Hospital Staff Management ---");
            System.out.println("(1) Add Staff Member");
            System.out.println("(2) Remove Staff Member");
            System.out.println("(3) Update Staff Information");
            System.out.println("(4) View All Staff");
            System.out.println("(5) Return to Main Menu");

            choice = InputScanner.sc.nextInt();
            InputScanner.sc.nextLine();

            switch (choice) {
                case 1 -> hospitalStaff.addStaffMember();
                case 2 -> hospitalStaff.removeStaffMember();
                case 3 -> hospitalStaff.updateStaffMember();
                case 4 -> hospitalStaff.displayAllStaff();
                case 5 -> System.out.println("Returning to Main Menu.");
                default -> System.out.println("Invalid choice, please try again.");
            }
        } while (choice != 5);
    }

    // View Appointment Details
    private void viewAppointmentDetails() {
        Schedule schedule = new Schedule();
        int choice;

        do {
            System.out.println("\n--- View Appointment Details ---");
            System.out.println("(1) View All Appointments");
            System.out.println("(2) View Appointments by Status");
            System.out.println("(3) Search by Appointment ID");
            System.out.println("(4) Search by Patient ID");
            System.out.println("(5) Search by Doctor ID");
            System.out.println("(6) Return to Main Menu");

            choice = InputScanner.sc.nextInt();
            InputScanner.sc.nextLine();

            switch (choice) {
                case 1 -> schedule.displayAppointmentList(Schedule.getAppointmentList());
                case 2 -> viewAppointmentsByStatus(schedule);
                case 3 -> searchAppointmentByID(schedule);
                case 4 -> searchByPatientID(schedule);
                case 5 -> searchByDoctorID(schedule);
                case 6 -> System.out.println("Returning to Main Menu.");
                default -> System.out.println("Invalid choice, please try again.");
            }
        } while (choice != 6);
    }

    private void viewAppointmentsByStatus(Schedule schedule) {
        System.out.println("Choose appointment status to filter by:");
        System.out.println("(1) Confirmed");
        System.out.println("(2) Pending");
        System.out.println("(3) Completed");
        System.out.println("(4) Cancelled");
        System.out.println("(5) PrescriptionPending");

        int statusChoice = InputScanner.sc.nextInt();
        InputScanner.sc.nextLine();

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

        ArrayList<Appointment> filteredAppointments = schedule.getAppointmentsByStatus(status);
        schedule.displayAppointmentList(filteredAppointments);
    }

    private void searchAppointmentByID(Schedule schedule) {
        System.out.println("Enter the Appointment ID:");
        String appointmentID = InputScanner.sc.nextLine();

        Appointment appointment = schedule.getAppointmentByID(appointmentID);
        if (appointment != null) {
        		ArrayList<Appointment> singleAppointmentList = new ArrayList<>();
            singleAppointmentList.add(appointment);
            schedule.displayAppointmentList(singleAppointmentList);
        } else {
            System.out.println("Appointment ID not found.");
        }
    }

    private void searchByPatientID(Schedule schedule) {
        System.out.println("Enter the Patient ID:");
        String patientID = InputScanner.sc.nextLine();

        ArrayList<Appointment> patientAppointments = schedule.getAppointmentsByPatientID(patientID);
        schedule.displayAppointmentList(patientAppointments);
    }

    private void searchByDoctorID(Schedule schedule) {
        System.out.println("Enter the Doctor ID:");
        String doctorID = InputScanner.sc.nextLine();

        ArrayList<Appointment> doctorAppointments = schedule.getAppointmentsByDoctorID(doctorID);
        schedule.displayAppointmentList(doctorAppointments);
    }

    // Manage Inventory System Menu
    private void manageInventorySystem() {
        int choice;
        do {
            System.out.println("\n--- Medication Inventory System ---");
            System.out.println("(1) View Inventory");
            System.out.println("(2) Add Medicine");
            System.out.println("(3) Remove Medicine");
            System.out.println("(4) Update Medicine");
            System.out.println("(5) Use Medicine");
            System.out.println("(6) Check Inventory for Low Stock");
            System.out.println("(7) Process Replenishment Requests");
            System.out.println("(8) Return to Main Menu");

            choice = InputScanner.sc.nextInt();
            InputScanner.sc.nextLine();

            switch (choice) {
                case 1 -> inventory.viewInventory();
                case 2 -> inventory.addOrIncrementMedicine();
                case 3 -> inventory.removeMedicine();
                case 4 -> inventory.updateMedicine();
                case 5 -> inventory.useMedicine();
                case 6 -> inventory.checkInventory();
                case 7 -> inventory.processReplenishmentRequests();
                case 8 -> System.out.println("Returning to Main Menu.");
                default -> System.out.println("Invalid choice.");
            }
        } while (choice != 8);
    }
}
