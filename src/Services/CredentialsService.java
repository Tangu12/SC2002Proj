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

/**
 * {@code CredentialsService} class which handles all the logic dealing with the {@code Appointment} class
 */
public class CredentialsService {
    private CredentialsRepository credentialsRepository;


    /**
     * Constructor for the {@code CredentialsService} with Dependency Injection of {@code CredentialsRepository}
     * @param credentialsRepository
     */
    public CredentialsService(CredentialsRepository credentialsRepository) {
        this.credentialsRepository = credentialsRepository;
    }

    /**
     * The hashing function that hashes the password with the salt using the SHA-256 Algorithm
     * @param password
     * @param salt
     * @return
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

    /**
     * Generates a random 16-bit salt to hash password with
     * @return
     */
    public static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    /**
     * Given a plainTextPassword, it hashes the password and checks it with the stored password in the {@code Credentials} file
     * @param userID
     * @param plainTextPassword
     * @return True if the hashed password matches the password stored inside the {@code Credentials} file
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

    /**
     * Given a plainTextPassword, it hashes the password and stores it into the {@code Credentials} file to update the password
     * @param userID
     * @param newPlainTextPassword
     */
    public void changePassword(String userID, String newPlainTextPassword){
        String salt = generateSalt();
        String hashedPassword = hashPassword(newPlainTextPassword, salt);

        credentialsRepository.updateHashedPassword(userID,hashedPassword,salt);
    }

    /**
     * Finds the Security Question of a User based on their {@code HospitalID} and returns it
     * @param userID
     * @return The User's Security Question
     */
    public String getSecurityQuestion(String userID){
        Credentials userData = credentialsRepository.readRecord(userID);
        return userData.getSecurityQuestion();
    }

    /**
     * Verifies a User's Security Question based on their {@code HospitalID} and input Answer
     * @param userID
     * @param inputAnswer
     * @return True if the User's answer to the security question matches the answer in the {@code Credentials} file
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

    /**
     * Get the remaining number of attempts to log in for a User based on their {@code HospitalID}
     * @param userID
     * @return
     */
    public int getNumberOfTriesLeft(String userID){
        Credentials userData = credentialsRepository.readRecord(userID);
        return (maxLoginAttempts - userData.getLoginAttempts());
    }

    /**
     * Create a {@code Credential} in the {@code Credential} file
     * @param userID
     * @param plainTextPassword
     * @param securityQuestion
     * @param plainTextSecurityAnswer
     */
    public void createNewRecord(String userID, String plainTextPassword, String securityQuestion, String plainTextSecurityAnswer) {
        String salt = generateSalt();

        String hashedPassword = hashPassword(plainTextPassword, salt);
        String hashedSecurityAnswer = hashPassword(plainTextSecurityAnswer, salt);

        Credentials newCredentials = new Credentials(userID, hashedPassword, salt, securityQuestion, hashedSecurityAnswer,0);

        credentialsRepository.createRecord(newCredentials);
    }

    /**
     * Deletes a {@code Credential} in the {@code Credential} file based on their {@code HospitalID}
     * @param userID
     */
    public void deleteRecord(String userID){
        credentialsRepository.deleteRecord(userID);
    }

    /**
     * Updates the {@code Credential}s of a User in the {@code Credential} file
     * @param userData
     */
    public void updateRecord(Credentials userData){
        credentialsRepository.updateRecord(userData);
    }

    /**
     * Searches for the {@code Credential} of a User from the {@code Credential} file based on their UserID
     * @param userID
     * @return The {@code Credential} of the User
     */
    public Credentials getRecord(String userID){
        return credentialsRepository.readRecord(userID);
    }

    /**
     * Locks the User Account by setting the number of attempts to -1
     * @param userID
     */
    public void lockAccount(String userID){
        Credentials temp = credentialsRepository.readRecord(userID);
        temp.lockAccount();
        credentialsRepository.updateRecord(temp);
    }

    /**
     * Unlocks the User Account by setting the number of attempts to 0
     * @param userID
     */
    public void unlockAccount(String userID){
        Credentials temp = credentialsRepository.readRecord(userID);
        temp.unlockAccount();
        credentialsRepository.updateRecord(temp);
    }

    /**
     * Method to check if a User's account is locked
     * @param userID
     * @return True if the User's account is locked and False otherwise
     */
    public boolean isAccountLocked(String userID){
        Credentials temp = credentialsRepository.readRecord(userID);
        return temp.isAccountLocked();
    }

    /**
     * Method to increment the number of login attempts. Locks Account if number of attempts exceed 3
     * @param userID
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

    /**
     * Function to get the domain of the User based on their {@code HospitalID}
     * @param userID
     * @return The domain of the User
     */
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

    /**
     * Function to get the {@code HospitalID} of all the User's in the Hospital
     * @return Array list of the {@code HospitalID} of all the User's in the Hospital
     */
    public ArrayList<String> getAllUserIDs() {
        return credentialsRepository.getAllUserIDs();
    }

}
