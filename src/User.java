import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;

// TODO create account feature, change password
// Make security question not case sensitive
// Make secuity question type and encrypt
// Looks for ID in credentials and set automatically
// Exception Handling
// Add format Name

enum Domain {
	PATIENT, DOCTOR, PHARMACIST, ADMINISTRATOR
}

public class User {

	public static final String PATIENT_PREFIX = "P";
	public static final String DOCTOR_PREFIX = "D";
	public static final String PHARMACIST_PREFIX = "R";
	public static final String ADMIN_PREFIX = "A";

	public static final int MAX_USERS = 999;

	protected String name;
	protected String hospitalId;
	protected String password;
	protected Domain domain;

	public User(String name, String hospitalId, Domain domain) {
		this.name = name;
		this.hospitalId = hospitalId;
		this.domain = domain;
	}

	public static User welcomeScreenInterface() {
		System.out.printf(
				" _  _                 _  _          _   __  __                                                 _     ___            _               \r\n"
						+ "| || | ___  ___ _ __ (_)| |_  __ _ | | |  \\/  | __ _  _ _   __ _  __ _  ___  _ __   ___  _ _  | |_  / __| _  _  ___| |_  ___  _ __  \r\n"
						+ "| __ |/ _ \\(_-<| '_ \\| ||  _|/ _` || | | |\\/| |/ _` || ' \\ / _` |/ _` |/ -_)| '  \\ / -_)| ' \\ |  _| \\__ \\| || |(_-<|  _|/ -_)| '  \\ \r\n"
						+ "|_||_|\\___//__/| .__/|_| \\__|\\__,_||_| |_|  |_|\\__,_||_||_|\\__,_|\\__, |\\___||_|_|_|\\___||_||_| \\__| |___/ \\_, |/__/ \\__|\\___||_|_|_|\r\n"
						+ "               |_|                                               |___/                                    |__/                      \r\n"
						+ "");

		boolean quit = false;
		String quit_confirmation;
		int choice;
		User user = null;

		do {
			System.out.println("Welcome to Hospital Management System !");
			System.out.println("Please input your choice : ");
			System.out.println("(1) Register as New User ");
			System.out.println("(2) Login as Existing User ");
			System.out.println("(3) Exit System");

			try {
			choice = InputScanner.sc.nextInt();
			}
			catch(InputMismatchException e) {
				choice = 0;
			}
			
			switch (choice) {
			case 1:
				user = createAccount();
				if (user != null) {
					quit = true;
				}
				break;
			case 2:
				// clear buffer
				String temp;
				temp = InputScanner.sc.nextLine();

				user = displayLoginInterface();
				if (user != null) {
					quit = true;
				}
				break;
			default:
				InputScanner.sc.nextLine();
				System.out.println("Are you sure you want to exit? Y/N");
				quit_confirmation = InputScanner.sc.next().trim();

				while (!quit_confirmation.equals("Y") && !quit_confirmation.equals("N")) {
					System.out.println("Please enter a valid choice");
					quit_confirmation = InputScanner.sc.next().trim();
				}

				quit = quit_confirmation.equals("Y");
				break;
			}
		} while (!quit);
		return user;
	}

	public static User displayLoginInterface() {
		int choice;
		String inputID;
		String inputPassword;
		boolean validUserID;
		boolean successfulLogin = false;
		boolean securitycheck = false;
		int login_attempts = 0;

		// Check for Valid UserID
		do {
			System.out.print("Please enter your unique Hospital ID : ");

			inputID = InputScanner.sc.nextLine().trim();
			validUserID = Credentials.checkValidUser(inputID);
			if (!validUserID) {
				System.out.println("User does not exist!");
			}
		} while (!validUserID);

		// Ask for password once UserID is Valid
		do {
			System.out.print("(1) Please enter your password \n");
			System.out.print("(2) Forgot password \n");
			try {
				choice = InputScanner.sc.nextInt();
				InputScanner.sc.nextLine();
			} catch (InputMismatchException e) {
				System.out.println("Invalid input. Please enter 1 or 2: \n");
				InputScanner.sc.nextLine();
				continue;
			}

			switch (choice) {
			case 1:
				System.out.print("Password : \n");
				inputPassword = InputScanner.sc.nextLine().trim();
				successfulLogin = Credentials.verifyCredentials(inputID, inputPassword);
				if (!successfulLogin) {
					System.out.println("Wrong Password!");
					login_attempts++;
					if (login_attempts >= 3) {
						System.out.println("Too many attempts. Please change your password: ");
						// Ask security question, if fail kick out to main page
						securitycheck = Credentials.askSecurityQuestion(inputID);
						if (!securitycheck) {
							System.out.println("Wrong answer to security question. You have been logged out. ");
							return null;
						}
						System.out.println("Please enter your new password: ");
						String new_password = InputScanner.sc.nextLine().trim();
						changePassword(inputID, new_password);
						return null;
					}
				}
				break;

			case 2:
				System.out.println("To change your password, please answer this security question: ");
				// Ask security question, if fail kick out to main page
				securitycheck = Credentials.askSecurityQuestion(inputID);
				if (!securitycheck) {
					System.out.println("Wrong answer to security question. You have been logged out. ");
					return null;
				}
				System.out.println("Please enter your new password: ");
				String new_password = InputScanner.sc.nextLine().trim();
				changePassword(inputID, new_password);
				break;
			default:
				System.out.println("Invalid choice. Please enter 1 or 2 : ");
				break;
			}

			if (successfulLogin) {
				// Login Success
				// Check the User's domain from the xlxs file and Create the specific User type
				// object (Patient, Doctor..)
				// if domain == "Patient", call Patient constructor

				Domain domain = null;
				User user = null;

				switch (inputID.charAt(0)) {
				case ('A'):
					domain = Domain.ADMINISTRATOR;

					user = new Administrator("", inputID, domain);

					// Look for name in staff file
					user.setName("Admin Name");

					user.setHospitalId(inputID);
					user.setDomain(domain);
					break;
				case ('D'):
					domain = Domain.DOCTOR;

					user = new Doctor("", inputID, domain);

					// Look for name in staff file
					List<String> doctors = new ArrayList<>();
					FileIO file = new FileIO("program_files/doctors.csv");
					List<String[]> data = file.getAllRows();
					for (String[] row : data) {
						if (row[0].equalsIgnoreCase(inputID)) {
							user.setName(row[1]);
						}
					}

					user.setHospitalId(inputID);
					user.setDomain(domain);
					break;
				case ('P'):
					domain = Domain.PATIENT;

					user = new Patient("", inputID, domain);

					// Look for name in patient file
					user.setName("Patient Name");

					user.setHospitalId(inputID);
					user.setDomain(domain);
					break;
				case ('R'):
					domain = Domain.PHARMACIST;

					user = new Pharmacist("", inputID, domain);

					// Look for name in staff file
					user.setName("Pharmacist Name");

					user.setHospitalId(inputID);
					user.setDomain(domain);
					break;
				default:
					System.out.println("Invalid domain. Exiting login.");
					return null;
				}

				try {
					System.out.println("Login Successful...");
					System.out.println("Welcome " + user.name + " !");
					System.out.println("Redirecting to " + user.domain + " main page...");
				} catch (Exception e) {
					e.printStackTrace();
				}
				return user;
			}
		} while (!successfulLogin && login_attempts < 3);
		System.out.println("Login failed.");

		return null;
	}

	public static boolean homePage(User user) {
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
		return false;
	}

	private static User createAccount() {
		int choice = 0;
		String choice_confirmation = "";
		boolean confirm_choice = false;
		Domain userDomain = null;
		User newUser = null;

		// Choose Domain
		do {
			System.out.println("Please Enter Your Domain : ");
			System.out.println("(1) Patient");
			System.out.println("(2) Doctor");
			System.out.println("(3) Pharmacist");
			System.out.println("(4) Administrator");

			try {
				choice = InputScanner.sc.nextInt();
			} catch (InputMismatchException e) {
				choice = 0;
			}
			InputScanner.sc.nextLine(); // Clear buffer

			switch (choice) {
			case 1:
				userDomain = Domain.PATIENT;
				System.out.println("You have selected 'Patient' as your option. Would you like to continue? (Y/N) -");
				break;
			case 2:
				userDomain = Domain.DOCTOR;
				System.out.println("You have selected 'Doctor' as your option. Would you like to continue? (Y/N) -");
				break;
			case 3:
				userDomain = Domain.PHARMACIST;
				System.out
						.println("You have selected 'Pharmacist' as your option. Would you like to continue? (Y/N) -");
				break;
			case 4:
				userDomain = Domain.ADMINISTRATOR;
				System.out.println(
						"You have selected 'Administrator' as your option. Would you like to continue? (Y/N) -");
				break;
			default:
				System.out.println("Please enter a valid choice ");
				continue;
			}

			choice_confirmation = InputScanner.sc.next().trim();
			while (!choice_confirmation.equals("Y") && !choice_confirmation.equals("N")) {
				System.out.println("Please enter a valid choice");
				choice_confirmation = InputScanner.sc.next().trim();
			}
			confirm_choice = choice_confirmation.equals("Y");

		} while (!confirm_choice);

		// Generate new username
		String newUsername = null;
		try {
			newUsername = Credentials.generateUsername(userDomain);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Your assigned Hospital ID will be: " + newUsername);

		// Get user details
		System.out.println("Please enter your full name: ");
		InputScanner.sc.nextLine(); // Clear buffer
		String fullName = InputScanner.sc.nextLine().trim();

		// Get password
		System.out.println("Please enter your password: ");
		String password = InputScanner.sc.nextLine().trim();

		// Get security question and answer

		String securityQuestion = null;

		do {
			System.out.println("Please select a security question: ");

			System.out.println("(1) What is your Favourite Colour?");
			System.out.println("(2) What is your Favourite Food?");
			System.out.println("(3) What is your Favourite Subject?");
			System.out.println("(4) What is your Favourite Show?");

			try {
				choice = InputScanner.sc.nextInt();
			}

			catch (InputMismatchException e) {
				InputScanner.sc.nextLine();
				choice = 0;
			}

			switch (choice) {
			case 1:
				securityQuestion = "What is your Favourite Colour?";
				break;
			case 2:
				securityQuestion = "What is your Favourite Food?";
				break;
			case 3:
				securityQuestion = "What is your Favourite Subject?";
				break;
			case 4:
				securityQuestion = "What is your Favourite Show?";
				break;
			default:
				System.out.println("Please enter a valid choice");
				continue;
			}
		} while (choice == 0);

		System.out.println("Please enter the answer to your security question: ");
		InputScanner.sc.nextLine();
		String securityAnswer = InputScanner.sc.nextLine().trim();

		// Create appropriate user type based on domain
		switch (userDomain) {
		case PATIENT:
			newUser = new Patient(fullName, newUsername, userDomain);
			break;
		case DOCTOR:
			newUser = new Doctor(fullName, newUsername, userDomain);
			break;
		case PHARMACIST:
			newUser = new Pharmacist(fullName, newUsername, userDomain);
			break;
		case ADMINISTRATOR:
			newUser = new Administrator(fullName, newUsername, userDomain);
			break;
		}

		// Generate salt and hash password
		try {
			String workingDir = System.getProperty("user.dir");
			String credentialsPath = workingDir + "/program_files/" + Credentials.credentials;

			String salt = Credentials.generateSalt();
			String hashedPassword = Credentials.hashPassword(password, salt);

			BufferedWriter writer = new BufferedWriter(new FileWriter(credentialsPath, true));

			writer.write(newUsername + "," + hashedPassword + "," + salt + "," + password + "," + securityQuestion + ","
					+ securityAnswer);
			writer.newLine();
			writer.close();

			System.out.println("Account created successfully!");
			return newUser;
		} catch (Exception e) {
			System.out.println("Error creating account");
			return null;
		}
	}

	public String getName() {
		return name;
	}

	public String getHospitalId() {
		return hospitalId;
	}

	public Domain getDomain() {
		return domain;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}

	public void setDomain(Domain domain) {
		this.domain = domain;
	}

	// What about other particulars to change, eg. Email, Name..?

	public static void changePassword(String hospitalId, String newPassword) {
//		System.out.println("Please enter your new password: ");
//		String new_password  = InputScanner.sc.nextLine().trim();

		String workingDir = System.getProperty("user.dir");
		String credentialsPath = workingDir + "/program_files/" + Credentials.credentials;

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
					credentials[1] = Credentials.hashPassword(newPassword, credentials[2]);
					// Join the parts back together into a line
					String updatedLine = String.join(",", credentials);
					updatedLines.add(updatedLine);
				} else {
					updatedLines.add(line); // Keep other lines unchanged
				}
			}
			// Write the updated lines back to the file
			Files.write(Paths.get(credentialsPath), updatedLines);

			System.out.println("Password updated successfully for " + hospitalId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
