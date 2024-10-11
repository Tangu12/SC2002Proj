import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;



public class User {
    String name;
    String hospitalId;
    String password;
    String domain;
    

    public User(String name, String hospitalId, String password, String domain) {
        this.name = name;
        this.hospitalId = hospitalId;
        this.password = password;
        this.domain = domain;
    }

    public void changePassword(String hospitalId, String new_password) {
    	
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
		System.out.print("Login Successful...");
	}
    
    
    
    
    
}
