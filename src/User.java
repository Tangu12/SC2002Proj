import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;

enum domain{
	PATIENT,
	DOCTOR,
	PHARMACIST,
	ADMINISTRATOR
}

public class User {
	
    protected String name;
    protected String hospitalId;
    protected String password;
    protected domain domain;
    
    
    public User(String name, String hospitalId, domain domain) {
        this.name = name;
        this.hospitalId = hospitalId;
        // this.password = password;
        this.domain = domain;
    }


    public static User displayLoginInterface() {
		System.out.printf(" _  _                 _  _          _   __  __                                                 _     ___            _               \r\n"
        		+ "| || | ___  ___ _ __ (_)| |_  __ _ | | |  \\/  | __ _  _ _   __ _  __ _  ___  _ __   ___  _ _  | |_  / __| _  _  ___| |_  ___  _ __  \r\n"
        		+ "| __ |/ _ \\(_-<| '_ \\| ||  _|/ _` || | | |\\/| |/ _` || ' \\ / _` |/ _` |/ -_)| '  \\ / -_)| ' \\ |  _| \\__ \\| || |(_-<|  _|/ -_)| '  \\ \r\n"
        		+ "|_||_|\\___//__/| .__/|_| \\__|\\__,_||_| |_|  |_|\\__,_||_||_|\\__,_|\\__, |\\___||_|_|_|\\___||_||_| \\__| |___/ \\_, |/__/ \\__|\\___||_|_|_|\r\n"
        		+ "               |_|                                               |___/                                    |__/                      \r\n"
        		+ "");
		
		String inputID;
		String inputPassword;
		boolean validUserID;
		boolean successfulLogin;
		
		
		// Check for Valid UserID
		do {
		System.out.print("Please enter your unique Hospital ID : ");
		inputID = InputScanner.sc.nextLine().trim();
		
		validUserID = Credentials.checkValidUser(inputID);
		
		if(!validUserID) {
			System.out.println("User does not exist!");
		}
		} while(!validUserID);
		
		
		// Ask for password once UserID is Valid
		do {
		System.out.print("Please enter your password : ");
		inputPassword = InputScanner.sc.nextLine().trim();
		successfulLogin = Credentials.verifyCredentials(inputID,inputPassword);
		
		if(!successfulLogin) {
			System.out.println("Wrong Password!");
			}
		}
		
		while(!successfulLogin);
		// Login Success
		// Check the User's domain from the xlxs file and Create the specific User type object (Patient, Doctor..)
		// if domain == "Patient", call Patient constructor
		
		domain domain = null;
		User user = null;
		
		switch(inputID.charAt(0)) {
			case ('A'):
				domain = domain.ADMINISTRATOR;
				
				user = new Administrator("",inputID,domain);
				
				// Look for name in staff file
				user.setName("Admin Name");
				
				user.setHospitalId(inputID);
				user.setDomain(domain);
				
				break;
			case ('D'):
				domain = domain.DOCTOR;
			
				user = new Doctor("",inputID,domain);
			
				// Look for name in staff file
				user.setName("Doctor Name");
				
				user.setHospitalId(inputID);
				user.setDomain(domain);
				break;
			case ('P'):
				domain = domain.PATIENT;
			
				user = new Patient("",inputID,domain);
			
				// Look for name in patient file
				user.setName("Patient Name");
				
				user.setHospitalId(inputID);
				user.setDomain(domain);
				break;
			case ('R'):
				domain = domain.PHARMACIST;
			
				user = new Pharmacist("",inputID,domain);
			
				// Look for name in staff file
				user.setName("Pharmacist Name");
				
				user.setHospitalId(inputID);
				user.setDomain(domain);
				break;
		}
		
		try {
			System.out.println("Login Successful...");
			System.out.println("Welcome "+user.name+ " !");
			System.out.println("Redirecting to "+user.domain+" main page...");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return user;
	}



	/*
	* Terminal display:
	*
	*  if (User.getdomain == "Patient")
	*
	* Patients:
	* 1. View Medical Record
	* 2. Update Personal Information (Change Password inside?)
	* 3. View Available Apppointment slots
	*
	*
	*
	* else if (User.getdomain == "Doctor")
	*
	* Doctor:
	* 1. View Scheduled patients / Timetable
	* 2. View Patient Medical Record
	* 3. View Personal Schedule
	* 4. Set availability for Appointments
	* 5. etc.
	*
	* */

	public static void homePage(User user) {
		
		switch(user.getDomain()) {
		
			case PATIENT:
				// Typecast user into patient
				// display Patient log in page
				if(user instanceof Patient) {
				Patient patient_user = (Patient)user;
				patient_user.homePage();
				}
				break;
			
			case DOCTOR:
				// Typecast user into Doctor
				// display Doctor log in page
				if(user instanceof Doctor) {
					Doctor doctor_user = (Doctor)user;
					doctor_user.homePage();
				}
				break;
				
			case PHARMACIST:
				// Typecast user into Pharmacist
				// Display Pharmacist log in page
				if(user instanceof Pharmacist) {
					Pharmacist pharmacist_user = (Pharmacist)user;
					pharmacist_user.homePage();
				}
				
				break;
				
			case ADMINISTRATOR:
				// Typecast user into Admin
				// display Administrator log in page
				if(user instanceof Administrator) {
				Administrator administrator_user = (Administrator)user;
				administrator_user.homePage();
				}
				break;
			
			default:
				System.out.println("ERROR GETTING DOMAIN");
		}
		
		
		
	}



	public String getName() {
		return name;
	}

	public String getHospitalId() {
		return hospitalId;
	}

	// No get password as the password cannot be unencrypted

	public domain getDomain() {
		return domain;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}

	public void setDomain(domain domain) {
		this.domain = domain;
	}

	// What about other particulars to change, eg. Email, Name..?

	public static void changePassword(String hospitalId, String newPassword) {
//		System.out.println("Please enter your new password: ");
//		String new_password  = InputScanner.sc.nextLine().trim();

		String workingDir = System.getProperty("user.dir");
		String credentialsPath = workingDir + "/program_files/"+ Credentials.credentials;

		try {
			List<String> fileLines = Files.readAllLines(Paths.get(credentialsPath));
			List<String> updatedLines = new ArrayList<>();

			for (String line : fileLines) {
				// Check if the line starts with the hospital ID
				if (line.startsWith(hospitalId + ",")) {
					// Split the line by commas to isolate fields
					String[] credentials = line.split(",");
					// Replace the last element (password) with the new password
					credentials[3] = newPassword;
					// Join the parts back together into a line
					String updatedLine = String.join(",", credentials);
					updatedLines.add(updatedLine);
				} else {
					updatedLines.add(line);  // Keep other lines unchanged
				}
			}
			// Write the updated lines back to the file
			Files.write(Paths.get(credentialsPath), updatedLines);

			System.out.println("Password updated successfully for " + hospitalId);
		} catch (IOException e) {
			e.printStackTrace();
		}

		//		try (BufferedReader br = new BufferedReader(new FileReader(credentialsPath))) {
//			while ((line = br.readLine()) != null) {
//				String[] credentials = line.split(",");
//				String username = credentials[0].trim();
//
//				if (username.equals(hospitalId)) {
//					credentials[3] = newPassword; // Update new password
////					String updatedCredentials = String.join(",", credentials);
//					break;
//				}
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//        }

	}


}

