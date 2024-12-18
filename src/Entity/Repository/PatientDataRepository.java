package Entity.Repository;

import Entity.Enums.BloodType;
import Entity.Enums.Domain;
import Entity.Enums.Gender;
import Entity.User.Patient;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * The Repository that accesses and changes everything related to {@code Patient} in the {@code Patient} database
 */
public class PatientDataRepository implements IRepository <String,String,Patient,Patient>{
    public static String path;

    /**
     * Constructor for the {@code PatientDataRepository} which initialises the path of which the {@code PatientDataRepository} reads from
     * @param path The path to the file where the {@code Patient} data is stored.
     */
    public PatientDataRepository(String path) {
    	PatientDataRepository.path = path;
    }

    /**
     * Creates a new {@code Patient} in the {@code Patient} file
     * @param attributes The attributes for creating the new {@code Patient}.
     *                  It expects a single object of type {@link Patient}.
     */
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

                writer.write(patient.getUserID() + "," + patient.getName() + "," + patient.getAge() + ","
                        + patient.getGender() + "," + patient.getDomain() + "," + patient.getContactInfo() + ","
                        + patient.getDateOfBirth() + "," + patient.getBloodType());
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

    /**
     * Looks through the {@code Patient} file to find the {@code Patient} matching the input, and returns the {@code Patient}.
     * @param targetID The unique identifier (ID) of the {@code Patient} to be found.
     *                 This is the ID used to search for the corresponding {@code Patient} record in the file.
     * @return The {@code Patient} record corresponding to the provided {@code targetID}, or {@code null} if no record is found.
     */
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
                    String contactInfo = data[5].trim();
                    LocalDate dateOfBirth = LocalDate.parse(data[6].trim());
                    BloodType bloodType = BloodType.valueOf(data[7].trim());

                    return new Patient(patientID, name, age, gender, domain,contactInfo,dateOfBirth,bloodType);
                }
            }

        } catch (Exception e) {
            System.out.println("Error accessing Patient file !!");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Updates the data of a {@code Patient} inside the {@code Patient} file
     * @param updatedPatient The updated {@code Patient} object that contains the new data for the existing {@code Patient} record.
     *                       This record will replace the old one with the same ID.
     */
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


                Patient temp = new Patient(patientID, name, age, gender, domain, contactInfo,dateOfBirth,bloodType);

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
            writer.write("UserID,Name,Age,Gender,Domain,ContactInfo,DateOfBirth,BloodType,MedicalHistory");
            writer.newLine();
            
            for (Patient tempPatient : tempPatients) {
                writer.write(tempPatient.getUserID() + "," + tempPatient.getName() + "," + tempPatient.getAge() + "," + tempPatient.getGender() + "," + tempPatient.getDomain() + "," +tempPatient.getContactInfo()+","+tempPatient.getDateOfBirth()+"," +tempPatient.getBloodType());
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

    /**
     * Deletes a {@code Patient} inside the {@code Patient} file
     * @param deletePatientID The unique identifier (ID) of the {@code Patient} to be deleted from the file.
     *                        If the ID matches an existing {@code Patient}, their record will be removed.
     */
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

                Patient temp = new Patient(patientID, name, age, gender, domain, contactInfo,dateOfBirth,bloodType);

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
                writer.write(tempPatient.getUserID() + "," + tempPatient.getName() + "," + tempPatient.getAge() + "," + tempPatient.getGender() + "," + tempPatient.getDomain() + "," +tempPatient.getContactInfo()+","+tempPatient.getDateOfBirth()+"," +tempPatient.getBloodType());
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

    /**
     * Loads the data of all the {@code Patient} inside the {@code Patients} file
     */
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
			System.out.println("File is not created yet!!");
		}
		boolean headerRow = true;
		for(String[] row : data) {
			if(headerRow) headerRow = false;
			else {
				Patient patient = new Patient(row[0], row[1], Integer.valueOf(row[2]), Gender.valueOf(row[3]), Domain.PATIENT, row[5], LocalDate.parse(row[6]), BloodType.valueOf(row[7]));
				Patient.getPatientList().add(patient);
			}
		}
    }
}
