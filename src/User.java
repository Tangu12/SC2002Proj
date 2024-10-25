import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;


public class User {
    protected String name;
    protected String hospitalId;
    protected String password;
    protected String domain;
    

    public User(String name, String hospitalId, String password, String domain) {
        this.name = name;
        this.hospitalId = hospitalId;
        // this.password = password;
        this.domain = domain;
    }


    public static void displayLoginInterface() {
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
		System.out.print("Login Successful...");
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

	public void homePage(User current_user) {
        if (current_user.getDomain().equals("Patient")) {
			// display Patient log in page
        }
		else if (current_user.getDomain().equals("Doctor")) {
			// display Doctor log in page
		}
		else if (current_user.getDomain().equals("Pharmacist")) {
			// display Pharmacist log in page
		}
		else if (current_user.getDomain().equals("Administrator")) {
			// display Administrator log in page
		}
	}



	public String getName() {
		return name;
	}

	public String getHospitalId() {
		return hospitalId;
	}

	// No get password as the password cannot be unencrypted

	public String getDomain() {
		return domain;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}

	public void setDomain(String domain) {
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

