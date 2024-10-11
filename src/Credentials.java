import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

public class Credentials {
	final static String credentials = "credentials.txt";
	
	public static String generateSalt(){
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }
	
	public static String hashPassword(String password, String salt) throws Exception{
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(salt.getBytes());  // Add the unique salt
        byte[] hashedBytes = md.digest(password.getBytes());
        
        return Base64.getEncoder().encodeToString(hashedBytes);
    }
	
	public static boolean verifyCredentials(String inputID,String inputPassword) {
		boolean validPassword = false;
		String line;
		
		String workingDir = System.getProperty("user.dir");
        String credentialsPath = workingDir + "/program_files/"+credentials;
		
        try (BufferedReader br = new BufferedReader(new FileReader(credentialsPath))) {
            while ((line = br.readLine()) != null) {
            	
            	if(line.contains(inputID))
            	{
	                String[] credentials = line.split(",");
	                String password = credentials[1].trim();
	                String salt = credentials[2].trim();
	                
	                if (password.equals(hashPassword(inputPassword,salt))) {
	                    validPassword = true; // Authentication successful
	                    break;
	                }
            	}
            }
        } catch (IOException e) {
        	System.out.println("File Error !!");
            e.printStackTrace();
        } catch (Exception e) {
			e.printStackTrace();
		}
		
		return validPassword;
	}
    
    public static boolean checkValidUser(String inputID) {
    	// Flag to check for valid user
    	boolean validUser = false;
    	String line;
    	
    	String workingDir = System.getProperty("user.dir");
        String credentialsPath = workingDir + "/program_files/"+credentials;

        try (BufferedReader br = new BufferedReader(new FileReader(credentialsPath))) {
            while ((line = br.readLine()) != null) {
                String[] credentials = line.split(",");
                String username = credentials[0].trim();
                
                if (username.equals(inputID)) {
                    validUser = true; // Authentication successful
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    	return validUser;
    }
	
	
	
	
	
}
