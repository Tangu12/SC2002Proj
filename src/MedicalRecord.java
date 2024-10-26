import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MedicalRecord {
	private String patientId;
	private String name;
	private int dateOfBirth;
	private String gender;
	private int phoneNumber;
	private String emailAddress;
	private String pastDiagnosis;
	private String pastPrescriptions;
	private String bloodType;
	
	public MedicalRecord(String patientId, String name, int dateOfBirth, String gender, int phoneNumber, String emailAddress, String pastDiagnosis, String pastPrescriptions, String bloodType) {
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

    public int getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(int dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
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
    

    public void viewMedRecord() {
        System.out.println("Patient ID: " + patientId);
        System.out.println("Name: " + name);
        System.out.println("Date of Birth: " + dateOfBirth);
        System.out.println("Gender: " + gender);
        System.out.println("Phone Number: " + phoneNumber);
        System.out.println("Email Address: " + emailAddress);
        System.out.println("Past Diagnosis: " + pastDiagnosis);
        System.out.println("Past Prescriptions: " + pastPrescriptions);
        System.out.println("Blood Type: " + bloodType);
    }

    public void updatePersonalInformation() {
    	Scanner scanner = new Scanner(System.in);
    	System.out.println("Choose the information to update: ");
    	System.out.println("1. Phone Number");
    	System.out.println("2. Email Address");
    	
    	int choice = scanner.nextInt();
    	switch(choice) {
    	case 1:
    		System.out.println("Enter new phone number: ");
    		String newPhoneNumber = scanner.nextLine();
    		try {
    			this.phoneNumber = Integer.parseInt(newPhoneNumber);
    			System.out.println("Phone number updated to: " + newPhoneNumber);
    		} catch (NumberFormatException e) {
    			System.out.println("Invalid phone number. Please enter numerical values only.");
    		}
    		break;
    	case 2:
    		System.out.println("Enter new email address: ");
    		String newEmailAddress = scanner.nextLine();
    		
    		if (newEmailAddress.contains("@") && newEmailAddress.contains(".")){
	    		this.emailAddress = newEmailAddress;
	    		System.out.println("Email address updated to: " + newEmailAddress);
    		}
    		else
    			System.out.println("Invalid email. Please try again.");
    		break;
    	    
    	default:
    		System.out.println("Invalid choice. Please select 1 or 2.");
    		break;
    	}
    }
    
    public void updatePrescriptions(String prescription) { //add condition: only doctor can update
        this.pastPrescriptions = prescription;
        System.out.println("Prescriptions updated.");
    }

    public void updateDiagnosis(String diagnosis) { //add condition: only doctor can update
        this.pastDiagnosis = diagnosis;
        System.out.println("Diagnosis updated.");
    }
}

