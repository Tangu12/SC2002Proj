package Entity.Repository;

import Entity.MedicalRecord;

import java.io.*;
import java.util.ArrayList;

/**
 * The Repository that accesses and changes everything related to {@code MedicalRecord} in the {@code MedicalRecord} database
 */
public class MedicalRecordRepository implements IRepository<MedicalRecord,String,MedicalRecord,MedicalRecord>{
    public final String path;

    /**
     * Constructor for the {@code MedicalRecordsRepository} which initialises the path of which the {@code MedicalRecordsRepository} reads from
     * @param path
     */
    public MedicalRecordRepository(String path) {
        this.path = path;
    }

    /**
     * Creates a new {@code MedicalRecord} at the bottom of the {@code MedicalRecord} database
     * @param attributes
     */
    public void createRecord(Object... attributes) {
        if(attributes.length != 1 && !(attributes[0] instanceof MedicalRecord)){
            System.out.println("Error! Attributes must be of type MedicalRecord");
            return;
        }
        MedicalRecord medicalRecord = (MedicalRecord) attributes[0];

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path, true))) {
            writer.write(medicalRecord.getAppointmentID() + "," + medicalRecord.getPatientId() + "," + medicalRecord.getAppointmentOutcomeRecord() + "," + medicalRecord.getMedicine() + medicalRecord.getMedicineIssueDate() + "," + medicalRecord.getDosage() + "," + medicalRecord.getInstructions());
            writer.newLine();
        } catch (Exception e) {
            System.out.println("Error accessing MedicationRecord file !!");
            e.printStackTrace();
        }
    }

    /**
     * Finds a specific {@code MedicalRecord} based on its {@code appointmentID}
     * @param inputID
     * @return The {@code MedicalRecord} with the corresponding {@code appointmentID} as inputID
     */
    public MedicalRecord readRecord(String inputID) { // Find first instance of the person's patient ID?
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            boolean isFirstLine = true;
            while ((line = reader.readLine()) != null) {
                if (isFirstLine) { // Skip the header row
                    isFirstLine = false;
                    continue;
                }
                String[] data = line.split(",");
                String targetID = data[0].trim();

                if (targetID.equals(inputID)) {
                    // Create and return Medicine Object

                    String patientID = data[1].trim();
                    String appointmentOutcomeRecord = data[2].trim();
                    String medicine = data[3].trim();
                    String medicineIssueDate = data[4].trim();
                    String dosage = data[5].trim();
                    String instructions = data[6].trim();

                    return new MedicalRecord(inputID, patientID, appointmentOutcomeRecord, medicine, medicineIssueDate, dosage, instructions);
                }
            }

        } catch (Exception e) {
            System.out.println("Error accessing MedicationRecord file !!");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Updates the {@code MedicalRecord} of a specific {@code MedicalRecord} inside {@code MedicalRecord} file
     * @param updatedRecord
     */
    public void updateRecord(MedicalRecord updatedRecord) {
        ArrayList<MedicalRecord> tempMedicalRecords = new ArrayList<>();
        boolean isUpdated = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            boolean isFirstLine = true;
            while ((line = reader.readLine()) != null) {
                if (isFirstLine) { // Skip the header row
                    isFirstLine = false;
                    continue;
                }

                String[] data = line.split(",");
                String appointmentID = data[0].trim();
                String patientID = data[1].trim();
                String appointmentOutcomeRecord = data[2].trim();
                String medicine = data[3].trim();
                String medicineIssueDate = data[4].trim();
                String dosage = data[5].trim();
                String instructions = data[6].trim();

                MedicalRecord temp = new MedicalRecord(appointmentID, patientID, appointmentOutcomeRecord, medicine, medicineIssueDate, dosage, instructions);

                // Update the record if it matches
                if (temp.getAppointmentID().equalsIgnoreCase(updatedRecord.getAppointmentID())) {
                    tempMedicalRecords.add(updatedRecord); // Add the updated record
                    isUpdated = true;
                } else {
                    tempMedicalRecords.add(temp); // Add existing record unchanged
                }
            }
        } catch (Exception e) {
            System.out.println("Error accessing MedicationRecord file !!");
            e.printStackTrace();
        }

        // Once the whole file is read, we copy the file back

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            writer.write("AppointmentID,HospitalID,Appointment Outcome Record,Medicine,Medicine Issue Date,Dosage,Instructions\n");
            writer.newLine();

            for (MedicalRecord medicalRecord : tempMedicalRecords) {
                writer.write(medicalRecord.getAppointmentID() + "," + medicalRecord.getPatientId() + "," + medicalRecord.getAppointmentOutcomeRecord() + "," + medicalRecord.getMedicine() + medicalRecord.getMedicineIssueDate() + "," + medicalRecord.getDosage() + "," + medicalRecord.getInstructions());
                writer.newLine();
            }
            if (!isUpdated) {
                System.out.println("Error, MedicalRecord not found! File is unchanged.");
            }
        } catch (IOException e) {
            System.out.println("Error writing to MedicalRecord file!");
            e.printStackTrace();
        }
    }


    /**
     * Deletes a {@code MedicalRecord} from the {@code MedicalRecord} file
     * @param deleteRecord
     */
    public void deleteRecord(MedicalRecord deleteRecord) {
        ArrayList<MedicalRecord> tempMedicalRecords = new ArrayList<>();
        boolean isDeleted = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            boolean isFirstLine = true;
            while ((line = reader.readLine()) != null) {
                if (isFirstLine) { // Skip the header row
                    isFirstLine = false;
                    continue;
                }

                String[] data = line.split(",");
                String appointmentID = data[0].trim();
                String patientID = data[1].trim();
                String appointmentOutcomeRecord = data[2].trim();
                String medicine = data[3].trim();
                String medicineIssueDate = data[4].trim();
                String dosage = data[5].trim();
                String instructions = data[6].trim();

                MedicalRecord temp = new MedicalRecord(appointmentID, patientID, appointmentOutcomeRecord, medicine, medicineIssueDate, dosage, instructions);

                // Update the record if it matches
                if (temp.getAppointmentID().equalsIgnoreCase(deleteRecord.getAppointmentID())) {
                    isDeleted = true;
                } else {
                    tempMedicalRecords.add(temp); // Add existing record unchanged
                }
            }
        } catch (Exception e) {
            System.out.println("Error accessing MedicationRecord file !!");
            e.printStackTrace();
        }

        // Once the whole file is read, we copy the file back

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            writer.write("AppointmentID,HospitalID,Appointment Outcome Records,Medicine,Medicine Issue Date,Dosage,Instructions\n");
            writer.newLine();

            for (MedicalRecord medicalRecord : tempMedicalRecords) {
                writer.write(medicalRecord.getAppointmentID() + "," + medicalRecord.getPatientId() + "," + medicalRecord.getAppointmentOutcomeRecord() + "," + medicalRecord.getMedicine() + medicalRecord.getMedicineIssueDate() + "," + medicalRecord.getDosage() + "," + medicalRecord.getInstructions());
                writer.newLine();
            }
            if (!isDeleted) {
                System.out.println("Error, MedicalRecord not found! File is unchanged.");
            }
        } catch (IOException e) {
            System.out.println("Error writing to MedicalRecord file!");
            e.printStackTrace();
        }
    }
}
