import java.util.Scanner;

public class MedicalRecordApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MedicalRecord medicalRecord = new MedicalRecord("", "", "", "", "", "", "", "", "");

        int choice;

        do {
            System.out.println("\n--- Medical Record Management System ---");
            System.out.println("0. Create Medical Record");
            System.out.println("1. Add Patient Record");
            System.out.println("2. View Patient Record");
            System.out.println("3. Update Personal Information");
            System.out.println("4. Update Diagnosis");
            System.out.println("5. Update Prescriptions");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");

            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 0:
                    medicalRecord.createMedicalRecords();
                    break;
                case 1:
                    medicalRecord.addPatientRecord();
                    break;
                case 2:
                    medicalRecord.viewMedicalRecord();
                    break;
                case 3:
                    medicalRecord.updatePersonalInformation();
                    break;
                case 4:
                    System.out.println("Enter the patient ID: ");
                    String patientID = scanner.nextLine();
                    System.out.print("Enter new diagnosis: ");
                    String newDiagnosis = scanner.nextLine();
                    medicalRecord.updateDiagnosis(patientID,newDiagnosis);
                    break;
                case 5:
                    System.out.println("Enter the patient ID: ");
                    String patientId = scanner.nextLine();
                    System.out.print("Enter new prescription: ");
                    String newPrescription = scanner.nextLine();
                    medicalRecord.updatePrescriptions(patientId,newPrescription);
                    break;
                case 6:
                    System.out.println("Exiting the system.");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice >=0 && choice <=6);
    }
}




