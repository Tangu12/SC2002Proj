package Entity.Repository;

import Entity.MedicalRecord;

import java.io.*;
import java.util.ArrayList;

public class MedicalRecordRepository implements IRepository<MedicalRecord,String,MedicalRecord,MedicalRecord>{
    public final String path;

    public MedicalRecordRepository(String path) {
        this.path = path;
    }

    public void createRecord(Object... attributes) {
        if(attributes.length != 1 && !(attributes[0] instanceof MedicalRecord)){
            System.out.println("Error! Attributes must be of type MedicalRecord");
            return;
        }
        MedicalRecord medicalRecord = (MedicalRecord) attributes[0];

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path, true))) {
            writer.write(medicalRecord.getAppointmentID() + "," + medicalRecord.getPatientId() + "," + medicalRecord.getName() + "," + medicalRecord.getDateOfBirth() + "," + medicalRecord.getGender() + "," + medicalRecord.getBloodType() + "," + medicalRecord.getPhoneNumber() + "," + medicalRecord.getEmailAddress() + "," + medicalRecord.getDiagnosis() + "," + medicalRecord.getPrescriptions());
            writer.newLine();
        } catch (Exception e) {
            System.out.println("Error accessing MedicationRecord file !!");
            e.printStackTrace();
        }
    }

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
                    String name = data[2].trim();
                    String dateOfBirth = data[3].trim();
                    String gender = data[4].trim(); // is gender now a String? or Enum?
                    String bloodType = data[5].trim();
                    String phoneNumber = data[6].trim();
                    String emailAddress = data[7].trim();
                    String diagnosis = data[8].trim();
                    String prescription = data[9].trim();

                    return new MedicalRecord(inputID, patientID, name, dateOfBirth, gender, bloodType, phoneNumber, emailAddress, diagnosis, prescription);
                }
            }

        } catch (Exception e) {
            System.out.println("Error accessing MedicationRecord file !!");
            e.printStackTrace();
        }
        return null;
    }

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
                String name = data[2].trim();
                String dateOfBirth = data[3].trim();
                String gender = data[4].trim(); // is gender now a String? or Enum?
                String bloodType = data[5].trim();
                String phoneNumber = data[6].trim();
                String emailAddress = data[7].trim();
                String diagnosis = data[8].trim();
                String prescription = data[9].trim();

                MedicalRecord temp = new MedicalRecord(appointmentID, patientID, name, dateOfBirth, gender, bloodType, phoneNumber, emailAddress, diagnosis, prescription);

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
            writer.write("HospitalID,Name,DateOfBirth,Gender,BloodType,PhoneNumber,Email,Diagnosis,Prescription\n");
            writer.newLine();

            for (MedicalRecord medicalRecord : tempMedicalRecords) {
                writer.write(medicalRecord.getPatientId() + "," + medicalRecord.getName() + "," + medicalRecord.getDateOfBirth() + "," + medicalRecord.getGender() + medicalRecord.getBloodType() + "," + medicalRecord.getPhoneNumber() + "," + medicalRecord.getEmailAddress() + "," + medicalRecord.getDiagnosis() + "," + medicalRecord.getPrescriptions());
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
                String name = data[2].trim();
                String dateOfBirth = data[3].trim();
                String gender = data[4].trim(); // is gender now a String? or Enum?
                String bloodType = data[5].trim();
                String phoneNumber = data[6].trim();
                String emailAddress = data[7].trim();
                String diagnosis = data[8].trim();
                String prescription = data[9].trim();

                MedicalRecord temp = new MedicalRecord(appointmentID, patientID, name, dateOfBirth, gender, bloodType, phoneNumber, emailAddress, diagnosis, prescription);

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
            writer.write("HospitalID,Name,DateOfBirth,Gender,BloodType,PhoneNumber,Email,Diagnosis,Prescription\n");
            writer.newLine();

            for (MedicalRecord medicalRecord : tempMedicalRecords) {
                writer.write(medicalRecord.getAppointmentID() + "," +medicalRecord.getPatientId() + "," + medicalRecord.getName() + "," + medicalRecord.getDateOfBirth() + "," + medicalRecord.getGender() + medicalRecord.getBloodType() + "," + medicalRecord.getPhoneNumber() + "," + medicalRecord.getEmailAddress() + "," + medicalRecord.getDiagnosis() + "," + medicalRecord.getPrescriptions());
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
