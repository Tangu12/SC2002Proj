import java.time.LocalDate;
import java.util.Scanner;

public class Prescription {
    // Enum for prescription status
    public enum PrescriptionStatus {
        ISSUED, DISPENSED, COMPLETED, CANCELLED
    }

    // Fields
    private String prescriptionID;
    private String patientID;
    private String doctorID;
    private LocalDate dateIssued;
    private PrescriptionStatus status;
    private String medicine;
    private String dosage;
    private String instruction;

    // Constructor
    public Prescription(String prescriptionID, String patientID, String doctorID, LocalDate dateIssued, String medicine, String dosage, String instruction) {
        this.prescriptionID = prescriptionID;
        this.patientID = patientID;
        this.doctorID = doctorID;
        this.dateIssued = dateIssued;
        this.medicine = medicine;
        this.dosage = dosage;
        this.instruction = instruction;
        this.status = PrescriptionStatus.ISSUED; // Default status
    }

    // Getters
    public String getPrescriptionID() {
        return prescriptionID;
    }

    public String getPatientID() {
        return patientID;
    }

    public String getDoctorID() {
        return doctorID;
    }

    public LocalDate getDateIssued() {
        return dateIssued;
    }

    public PrescriptionStatus getStatus() {
        return status;
    }

    public String getMedicine() {
        return medicine;
    }

    public String getDosage() {
        return dosage;
    }

    public String getInstruction() {
        return instruction;
    }

    // Setter for status
    public void setStatus(PrescriptionStatus status) {
        this.status = status;
    }

    // Method to update the prescription status based on user input
    public void updateStatus() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose the new status for Prescription ID: " + prescriptionID + "\n"
                + "(1) ISSUED\n"
                + "(2) DISPENSED\n"
                + "(3) COMPLETED\n"
                + "(4) CANCELLED");
        
        int statusChoice = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        switch (statusChoice) {
            case 1 -> this.status = PrescriptionStatus.ISSUED;
            case 2 -> this.status = PrescriptionStatus.DISPENSED;
            case 3 -> this.status = PrescriptionStatus.COMPLETED;
            case 4 -> this.status = PrescriptionStatus.CANCELLED;
            default -> System.out.println("Invalid status choice.");
        }
        
        System.out.println("Prescription status updated to: " + this.status);
    }
}
