package Services;


import Entity.Credentials;
import Entity.Enums.Domain;
import Entity.Repository.CredentialsRepository;
import Entity.User.IUser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;

import static Entity.Credentials.maxLoginAttempts;

public class CredentialsService {
    private CredentialsRepository credentialsRepository;


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

    /*
    Returns Security Question
    */
    public String getSecurityQuestion(String userID){
        Credentials userData = credentialsRepository.readRecord(userID);
        return userData.getSecurityQuestion();
    }

    /*
    Verifies the Security Question based on the input Answer
    */
    public boolean verifySecurityQuestion(String userID, String inputAnswer){
        boolean successful = false;
        Credentials userData = credentialsRepository.readRecord(userID);

        String hashedSecurityAnswerFromFile = userData.getHashedSecurityAnswer();
        String saltFromFile = userData.getSalt();
        String hashedInputSecurityAnswer = hashPassword(inputAnswer, saltFromFile);

        if(hashedSecurityAnswerFromFile.equals(hashedInputSecurityAnswer)){
            successful = true;
        }

        return successful;
    }

    /*
    Get Number of Tries Left for User
    */
    public int getNumberOfTriesLeft(String userID){
        Credentials userData = credentialsRepository.readRecord(userID);
        return (maxLoginAttempts - userData.getLoginAttempts());
    }

    /*
    Create a new record in the credentials file
    */
    public void createNewRecord(String userID, String plainTextPassword, String securityQuestion, String plainTextSecurityAnswer) {
        String salt = generateSalt();

        String hashedPassword = hashPassword(plainTextPassword, salt);
        String hashedSecurityAnswer = hashPassword(plainTextSecurityAnswer, salt);

        Credentials newCredentials = new Credentials(userID, hashedPassword, salt, securityQuestion, hashedSecurityAnswer,0);

        credentialsRepository.createRecord(newCredentials);
    }

    /*
    Delete a record from the credentials file
    */
    public void deleteRecord(String userID){
        credentialsRepository.deleteRecord(userID);
    }

    /*
    Update a record from the credentials file
    */
    public void updateRecord(Credentials userData){
        credentialsRepository.updateRecord(userData);
    }

    /*
    Return a record from the credentials file based on UserID
    */
    public Credentials getRecord(String userID){
        return credentialsRepository.readRecord(userID);
    }

    /*
    Locks the User Account by setting the number of attempts to -1
    */
    public void lockAccount(String userID){
        Credentials temp = credentialsRepository.readRecord(userID);
        temp.lockAccount();
        credentialsRepository.updateRecord(temp);
    }

    public void unlockAccount(String userID){
        Credentials temp = credentialsRepository.readRecord(userID);
        temp.unlockAccount();
        credentialsRepository.updateRecord(temp);
    }

    /*
    Method to check if userAccount is locked
    */
    public boolean isAccountLocked(String userID){
        Credentials temp = credentialsRepository.readRecord(userID);
        return temp.isAccountLocked();
    }

    /*
    Method to increment the number of login attempts. Locks Account if number of attempts exceed 3
    */
    public void incrementLoginAttempts(String userID){
        if(isAccountLocked(userID)){
            return;
        }
        Credentials temp = credentialsRepository.readRecord(userID);
        int tries = temp.getLoginAttempts();
        if(++tries>maxLoginAttempts){
            temp.lockAccount();
        }
        temp.setLoginAttempts(tries);
        credentialsRepository.updateRecord(temp);
    }

    public Domain getUserDomain(String userID) {
        if (userID == null || userID.isEmpty()) {
            System.out.println("Invalid userID.");
            return null;
        }

        char userType = userID.charAt(0);
        switch (userType) {
            case 'D':
                return Domain.DOCTOR;
            case 'P':
                return Domain.PATIENT;
            case 'R':
                return Domain.PHARMACIST;
            case 'A':
                return Domain.ADMINISTRATOR;

            default:
                System.out.println("Unknown user type.");
                return null;
        }
    }

    public ArrayList<String> getAllUserIDs() {
        return credentialsRepository.getAllUserIDs();
    }

}
