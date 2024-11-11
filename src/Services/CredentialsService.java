package Services;


import Entity.Credentials;
import Entity.Repository.CredentialsRepository;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class CredentialsService {
    private CredentialsRepository credentialsRepository;
    final int maxLoginAttempts = 3;

    // Dependency Injection of CredentialsRepository
    public CredentialsService(CredentialsRepository credentialsRepository) {
        this.credentialsRepository = credentialsRepository;
    }

    /*
    Hashes the password with the salt using the SHA-256 Algorithm
    */
    public static String hashPassword(String password, String salt) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            System.out.println("Error getting SHA-256 Algorithm!");
            return null;
        }
        md.update(salt.getBytes());
        byte[] hashedBytes = md.digest(password.getBytes());
        return Base64.getEncoder().encodeToString(hashedBytes);
    }

    /*
    Generates a random 16-bit salt to hash password with
    */
    public static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    /*
    Given a plainTextPassword, it hashes the password and checks it with the stored password in the credentials file
    */
    public boolean checkPassword(String userID, String plainTextPassword){
        boolean successfulLogin = false;
        // readRecord returns a line in the credentials file.
        // Format is (UserID,hashedPassword,salt,plainPassword,securityQuestion,answerToSecurityQuestion)
        Credentials userData = credentialsRepository.readRecord(userID);

        String hashedPasswordFromFile = userData.getHashedPassword();
        String saltFromFile = userData.getSalt();
        String hashedInputPassword = hashPassword(plainTextPassword, saltFromFile);

        if(hashedPasswordFromFile.equals(hashedInputPassword)){
            successfulLogin = true;
            System.out.println("Login Successful!");
        }

        return successfulLogin;
    }

    /*
    Given a plainTextPassword, it hashes the password and stores it into the Credentials File to update the password
    */
    public void changePassword(String userID, String newPlainTextPassword){
        String salt = generateSalt();
        String hashedPassword = hashPassword(newPlainTextPassword, salt);

        credentialsRepository.updateHashedPassword(userID,hashedPassword,salt);
    }

    // Get security Question
    public String getSecurityQuestion(String userID){
        Credentials userData = credentialsRepository.readRecord(userID);
        return userData.getSecurityQuestion();
    }

    // Verify Security Question
    public boolean verifySecurityQuestion(String userID, String inputAnswer){
        boolean successful = false;
        Credentials userData = credentialsRepository.readRecord(userID);

        String hashedSecurityAnswerFromFile = userData.getHashedSecurityAnswer();
        String saltFromFile = userData.getSalt();
        String hashedInputSecurityAnswer = hashPassword(inputAnswer, saltFromFile);

        if(hashedSecurityAnswerFromFile.equals(hashedInputSecurityAnswer)){
            successful = true;
            System.out.println("Login Successful!");
        }

        return successful;
    }

    // Get Tries Left
    public int getNumberOfTriesLeft(String userID){
        Credentials userData = credentialsRepository.readRecord(userID);
        return (maxLoginAttempts - userData.getLoginAttempts());
    }

    // Create New Record
    public void createNewRecord(String userID, String plainTextPassword, String securityQuestion, String plainTextSecurityAnswer) {
        String salt = generateSalt();

        String hashedPassword = hashPassword(plainTextPassword, salt);
        String hashedSecurityAnswer = hashPassword(plainTextSecurityAnswer, salt);

        Credentials newCredentials = new Credentials(userID, hashedPassword, salt, securityQuestion, hashedSecurityAnswer,0);

        credentialsRepository.createRecord(newCredentials);
    }

    public void deleteRecord(String userID){
        credentialsRepository.deleteRecord(userID);
    }

    public void updateRecord(Credentials userData){
        credentialsRepository.updateRecord(userData);
    }

    public Credentials getRecord(String userID){
        return credentialsRepository.readRecord(userID);
    }
}
