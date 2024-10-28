import java.io.*;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;


public class MedicalRecord {

    private static final String FILE_NAME = "program_files/MedicalRecord.txt";

    private String patientId;
    private String name;
    private String dateOfBirth;
    private String gender;
    private String phoneNumber;
    private String emailAddress;
    private String pastDiagnosis;
    private String pastPrescriptions;
    private String bloodType;

    public MedicalRecord(String patientId, String name, String dateOfBirth, String gender, String phoneNumber, String emailAddress, String pastDiagnosis, String pastPrescriptions, String bloodType) {
        this.patientId = patientId;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.pastDiagnosis = pastDiagnosis;
        this.pastPrescriptions = pastPrescriptions;
        this.bloodType = bloodType;
    }




    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPastDiagnosis() {
        return pastDiagnosis;
    }

    public void setPastDiagnosis(String pastDiagnosis) {
        this.pastDiagnosis = pastDiagnosis;
    }

    public String getPastPrescriptions() {
        return pastPrescriptions;
    }

    public void setPastPrescriptions(String pastPrescriptions) {
        this.pastPrescriptions = pastPrescriptions;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public void createMedicalRecords() {
        try {
            File medicalRecordsFile = new File(FILE_NAME);
            if (medicalRecordsFile.createNewFile()) {
                FileWriter headerWriter = new FileWriter(medicalRecordsFile);
                headerWriter.write("HospitalID,Name,DateOfBirth,Gender,BloodType,PhoneNumber,Email,PastDiagnosis,PastPrescription\n");
                headerWriter.close();
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true));
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void viewMedicalRecord() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Patient ID: ");
        String patientId = scanner.nextLine().trim();

        String line;
        String workingDir = System.getProperty("user.dir");
        String patientFilePath = workingDir + "/program_files/MedicalRecord.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(patientFilePath))) {
            boolean recordFound = false;

            while ((line = br.readLine()) != null) {
                // Split line into fields
                String[] patientData = line.split(",");
                String id = patientData[0].trim();

                // Check if this line corresponds to the specified patient ID
                if (id.equals(patientId)) {
                    recordFound = true;
                    // Display patient's information
                    System.out.println("Patient ID: " + patientData[0].trim());
                    System.out.println("Name: " + patientData[1].trim());
                    System.out.println("Date of Birth: " + patientData[2].trim());
                    System.out.println("Gender: " + patientData[3].trim());
                    System.out.println("Blood Type: " + patientData[4].trim());
                    System.out.println("Phone Number: " + patientData[5].trim());
                    System.out.println("Email Address: " + patientData[6].trim());
                    System.out.println("Past Diagnosis: " + patientData[7].trim());
                    System.out.println("Past Prescriptions: " + patientData[8].trim());
                    break;
                }
            }

            // If no record is found
            if (!recordFound) {
                System.out.println("No record found for Patient ID: " + patientId);
            }
        } catch (IOException e) {
            System.out.println("File Error!!");
            e.printStackTrace();
        }
    }

    public void updatePersonalInformation() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Patient ID: ");
        String patientId = scanner.nextLine().trim();

        String workingDir = System.getProperty("user.dir");
        String patientFilePath = workingDir + "/program_files/MedicalRecord.txt";
        List<String> updatedRecords = new ArrayList<>();
        boolean recordFound = false;

        try (BufferedReader br = new BufferedReader(new FileReader(patientFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] patientData = line.split(",");
                String id = patientData[0].trim();

                if (id.equals(patientId)) {
                    recordFound = true;

                    System.out.println("Choose the information to update: ");
                    System.out.println("1. Phone Number");
                    System.out.println("2. Email Address");

                    int choice = scanner.nextInt();
                    scanner.nextLine();

                    switch (choice) {
                        case 1:
                            System.out.print("Enter new phone number: ");
                            String newPhoneNumber = scanner.nextLine();
                            try {
                                Long.parseLong(newPhoneNumber);
                                patientData[5] = newPhoneNumber;
                                System.out.println("Phone number updated to: " + newPhoneNumber);
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid phone number. Please enter numerical values only.");
                            }
                            break;
                        case 2:
                            System.out.print("Enter new email address: ");
                            String newEmailAddress = scanner.nextLine();

                            if (newEmailAddress.contains("@") && newEmailAddress.contains(".")) {
                                patientData[6] = newEmailAddress;
                                System.out.println("Email address updated to: " + newEmailAddress);
                            } else {
                                System.out.println("Invalid email. Please try again.");
                            }
                            break;

                        default:
                            System.out.println("Invalid choice. Please select 1 or 2.");
                            break;
                    }

                    String updatedLine = String.join(",", patientData);
                    updatedRecords.add(updatedLine);
                } else {
                    updatedRecords.add(line);
                }
            }
        } catch (IOException e) {
            System.out.println("File Error !!");
            e.printStackTrace();
            return;
        }

        if (!recordFound) {
            System.out.println("No record found for Patient ID: " + patientId);
            return;
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(patientFilePath))) {
            for (String updatedRecord : updatedRecords) {
                bw.write(updatedRecord);
                bw.newLine();
            }
            System.out.println("Patient record updated successfully.");
        } catch (IOException e) {
            System.out.println("Error writing to the file.");
            e.printStackTrace();
        }
    }

    public void addPatientRecord() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter Patient ID: ");
        this.patientId = scanner.nextLine();

        System.out.println("Enter Name: ");
        this.name = scanner.nextLine();

        System.out.println("Enter Date of Birth (YYYY-MM-DD): ");
        this.dateOfBirth = scanner.nextLine();

        System.out.println("Enter Gender: ");
        this.gender = scanner.nextLine();

        System.out.println("Enter Blood Type: ");
        this.bloodType = scanner.nextLine();

        System.out.println("Enter Phone Number: ");
        this.phoneNumber = scanner.nextLine();

        System.out.println("Enter Email Address: ");
        this.emailAddress = scanner.nextLine();

        System.out.println("Enter Past Diagnosis: ");
        this.pastDiagnosis = scanner.nextLine();

        System.out.println("Enter Past Prescriptions: ");
        this.pastPrescriptions = scanner.nextLine();

        String workingDir = System.getProperty("user.dir");
        String patientFilePath = workingDir + "/program_files/MedicalRecord.txt";

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(patientFilePath, true))) {
            String newRecord = String.join(",",
                    this.patientId,
                    this.name,
                    this.dateOfBirth,
                    this.gender,
                    this.bloodType,
                    this.phoneNumber,
                    this.emailAddress,
                    this.pastDiagnosis,
                    this.pastPrescriptions);
            bw.write(newRecord);
            bw.newLine();
            System.out.println("Patient record added successfully.");
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
            e.printStackTrace();
        }
    }




    public void updateDiagnosis(String patientId, String diagnosis) {
        Scanner scanner = new Scanner(System.in);
        String workingDir = System.getProperty("user.dir");
        String patientFilePath = workingDir + "/program_files/MedicalRecord.txt";
        List<String> updatedRecords = new ArrayList<>();
        boolean recordFound = false;

        try (BufferedReader br = new BufferedReader(new FileReader(patientFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] patientData = line.split(",");
                String id = patientData[0].trim();

                if (id.equals(patientId)) {
                    recordFound = true;
                    patientData[7] = diagnosis;
                    System.out.println("Diagnosis updated to: " + diagnosis);
                }

                updatedRecords.add(String.join(",", patientData));
            }
        } catch (IOException e) {
            System.out.println("File Error !!");
            e.printStackTrace();
            return;
        }


        if (!recordFound) {
            System.out.println("No record found for Patient ID: " + patientId);
            return;
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(patientFilePath))) {
            for (String updatedRecord : updatedRecords) {
                bw.write(updatedRecord);
                bw.newLine();
            }
            System.out.println("Patient record updated successfully.");
        } catch (IOException e) {
            System.out.println("Error writing to the file.");
            e.printStackTrace();
        }
    }




    public void updatePrescriptions(String patientId, String prescription) {
        Scanner scanner = new Scanner(System.in);
        String workingDir = System.getProperty("user.dir");
        String patientFilePath = workingDir + "/program_files/MedicalRecord.txt";
        List<String> updatedRecords = new ArrayList<>();
        boolean recordFound = false;

        try (BufferedReader br = new BufferedReader(new FileReader(patientFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] patientData = line.split(",");
                String id = patientData[0].trim();

                if (id.equals(patientId)) {
                    recordFound = true;
                    patientData[8] = prescription;
                    System.out.println("Prescriptions updated to: " + prescription);
                }

                updatedRecords.add(String.join(",", patientData));
            }
        } catch (IOException e) {
            System.out.println("File Error !!");
            e.printStackTrace();
            return;
        }


        if (!recordFound) {
            System.out.println("No record found for Patient ID: " + patientId);
            return;
        }


        try (BufferedWriter bw = new BufferedWriter(new FileWriter(patientFilePath))) {
            for (String updatedRecord : updatedRecords) {
                bw.write(updatedRecord);
                bw.newLine();
            }
            System.out.println("Patient record updated successfully.");
        } catch (IOException e) {
            System.out.println("Error writing to the file.");
            e.printStackTrace();
        }
    }

}

