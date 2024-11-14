package Entity.Repository;

import Entity.Enums.BloodType;
import Entity.Enums.Domain;
import Entity.Enums.Gender;
import Entity.MedicalRecord;
import Entity.Medicine;
import Entity.User.Patient;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class PatientDataRepository implements IRepository <String,String,Patient,Patient>{
    public static String path;

    public PatientDataRepository(String path) {
    	PatientDataRepository.path = path;
    }

    @Override
    public void createRecord(Object... attributes) {
        if (attributes.length != 1 || !(attributes[0] instanceof Patient)) {
            System.out.println("Error! Incorrect number of attributes provided.");
            return;
        }
        try {
            Patient patient = (Patient) attributes[0];
            // Check if patient already exists
            if (readRecord(patient.getUserID()) != null) {
                System.out.println("Error!! Patient already exists !!");
                return;
            }

            File file = new File(path);
            boolean isFileEmpty = file.length() == 0;

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
                // Add headers if the file is empty
                if (isFileEmpty) {
                    writer.write("UserID,Name,Age,Gender,Domain,ContactInfo,DateOfBirth,BloodType,MedicalHistory");
                    writer.newLine();
                }
                // prevent null entry from creating new line
                // String medicalHistory = (patient.getMedicalHistory() == null) ? "" : patient.getMedicalHistory().toString();

                writer.write(patient.getUserID() + "," + patient.getName() + "," + patient.getAge() + ","
                        + patient.getGender() + "," + patient.getDomain() + "," + patient.getContactInfo() + ","
                        + patient.getDateOfBirth() + "," + patient.getBloodType() + "," + patient.getMedicalHistory());
                writer.newLine();
                System.out.println("Patient Record Added to PatientData file successfully!");
            } catch (Exception e) {
                System.out.println("Error accessing Patient file !!");
                e.printStackTrace();
            }
        } catch (ClassCastException e) {
            System.out.println("Error: Invalid attribute types provided.");
            e.printStackTrace();
        }
    }

    @Override
    public Patient readRecord(String targetID) { // why cant i change this to String?
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            boolean isFirstLine = true;
            while ((line = reader.readLine()) != null) {
                if (isFirstLine) { // Skip the header row
                    isFirstLine = false;
                    continue;
                }
                String[] data = line.split(",");
                String patientID = data[0].trim();

                if (patientID.equals(targetID)) { // What is identifier?
                    // Create and return Medicine Object
                    String name = data[1].trim();
                    int age = Integer.parseInt(data[2].trim());
                    Gender gender = Gender.valueOf(data[3].trim());
                    Domain domain  = Domain.valueOf(data[4].trim());
//                    String medicalRecordsJson = data[5].trim(); // Assuming medicalRecords is in column 5
//                    Type medicalRecordListType = new TypeToken<ArrayList<MedicalRecord>>() {}.getType();
//                    ArrayList<MedicalRecord> medicalRecords = gson.fromJson(medicalRecordsJson, medicalRecordListType);
                    ArrayList<MedicalRecord> medicalRecords = null; // Array of medical records issue
                    String contactInfo = data[5].trim();
                    LocalDate dateOfBirth = LocalDate.parse(data[6].trim());
                    BloodType bloodType = BloodType.valueOf(data[7].trim());

                    return new Patient(patientID, name, age, gender, domain, medicalRecords,contactInfo,dateOfBirth,bloodType);
                }
            }

        } catch (Exception e) {
            System.out.println("Error accessing Patient file !!");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateRecord(Patient updatedPatient) {
        ArrayList<Patient> tempPatients = new ArrayList<>();
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
                String patientID = data[0].trim();
                String name = data[1].trim();
                int age = Integer.parseInt(data[2].trim());
                Gender gender = Gender.valueOf(data[3].trim());
                Domain domain  = Domain.valueOf(data[4].trim());
                String contactInfo = data[5].trim();
                LocalDate dateOfBirth = LocalDate.parse(data[6].trim());
                BloodType bloodType = BloodType.valueOf(data[7].trim());
                ArrayList<MedicalRecord> medicalRecords = null; // Array of medical records issue


                Patient temp = new Patient(patientID, name, age, gender, domain, medicalRecords,contactInfo,dateOfBirth,bloodType);

                // Update the record if it matches
                if (temp.getUserID().equalsIgnoreCase(updatedPatient.getUserID())) {
                    tempPatients.add(updatedPatient); // Add the updated record
                    isUpdated = true;
                } else {
                    tempPatients.add(temp); // Add existing record unchanged
                }
            }
        } catch (Exception e) {
            System.out.println("Error accessing Patient file !!");
            e.printStackTrace();
        }

        // Once the whole file is read, we copy the file back

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            //writer.write("HospitalID,Name,Age,Gender,Domain,Contact,DateOfBirth,BloodType,MedicalHistory\n");
            writer.newLine();

            for (Patient tempPatient : tempPatients) {
                writer.write(tempPatient.getUserID() + "," + tempPatient.getName() + "," + tempPatient.getAge() + "," + tempPatient.getGender() + "," + tempPatient.getDomain() + "," +tempPatient.getContactInfo()+","+tempPatient.getDateOfBirth()+"," +tempPatient.getBloodType()+","+tempPatient.getMedicalHistory());
                writer.newLine();
            }
            if (!isUpdated) {
                System.out.println("Error, Patient not found! File is unchanged.");
            }
        } catch (IOException e) {
            System.out.println("Error writing to Patient file!");
            e.printStackTrace();
        }
    }

    @Override
    public void deleteRecord(String deletePatientID) {
        ArrayList<Patient> tempPatients = new ArrayList<>();
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
                String patientID = data[0].trim();
                String name = data[1].trim();
                int age = Integer.parseInt(data[2].trim());
                Gender gender = Gender.valueOf(data[3].trim());
                Domain domain  = Domain.valueOf(data[4].trim());
                String contactInfo = data[5].trim();
                LocalDate dateOfBirth = LocalDate.parse(data[6].trim());
                BloodType bloodType = BloodType.valueOf(data[7].trim());
                ArrayList<MedicalRecord> medicalRecords = null; // Array of medical records issue

                Patient temp = new Patient(patientID, name, age, gender, domain, medicalRecords,contactInfo,dateOfBirth,bloodType);

                if (temp.getUserID().equalsIgnoreCase(deletePatientID)) {
                    isDeleted = true;
                } else {
                    tempPatients.add(temp); // Add existing record unchanged
                }
            }
        } catch (Exception e) {
            System.out.println("Error accessing Patient file !!");
            e.printStackTrace();
        }

        // Once the whole file is read, we copy the file back

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            writer.write("HospitalID,Name,Age,Gender,Domain,ContactInfo,MedicalRecords");
            writer.newLine();

            for (Patient tempPatient : tempPatients) {
                writer.write(tempPatient.getUserID() + "," + tempPatient.getName() + "," + tempPatient.getAge() + "," + tempPatient.getGender() + "," + tempPatient.getDomain() + "," +tempPatient.getContactInfo()+","+tempPatient.getDateOfBirth()+"," +tempPatient.getBloodType()+","+tempPatient.getMedicalHistory());
                writer.newLine();
            }
            if (!isDeleted) {
                System.out.println("Error, Patient not found! File is unchanged.");
            }
            else{
                System.out.println("Patient is successfully deleted from PatientData file!");
            }
        } catch (IOException e) {
            System.out.println("Error writing to Patient file!");
            e.printStackTrace();
        }
    }
    
    public static void loadPatientlist() {
		List<String[]> data = new ArrayList<>();

		//Read the CSV file
		try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
			String row;
			while ((row = reader.readLine()) != null) {
				String[] values = row.split(",");
				data.add(values);
			}
		} catch (IOException e) {
			//e.printStackTrace();
			System.out.println("File is not created yet!!");
		}
		boolean headerRow = true;
		for(String[] row : data) {
			if(headerRow) headerRow = false;
			else {
				String[] addingPatient = new String[2];
				addingPatient[0] = row[0];
				addingPatient[1] = row[1];
				Patient.getPatientList().add(addingPatient);
			}
		}
    }
}