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
     * @param credentialsRepository The {@code CredentialsRepository} that handles data access for user credentials.
     */
    public CredentialsService(CredentialsRepository credentialsRepository) {
        this.credentialsRepository = credentialsRepository;
    }

    /**
     * The hashing function that hashes the password with the salt using the SHA-256 Algorithm
     * @param password The plain text password to be hashed.
     * @param salt The salt to be used in the hashing process to ensure uniqueness.
     * @return The hashed password as a Base64-encoded string.
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
     * @return A randomly generated 16-byte salt, encoded as a Base64 string.
     */
    public static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    /**
     * Given a plainTextPassword, it hashes the password and checks it with the stored password in the {@code Credentials} file
     * @param userID The user ID to verify the password against.
     * @param plainTextPassword The plain text password input by the user.
     * @return True if the hashed password matches the stored password, otherwise false.
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
     * Given a plainTextPassword, it hashes the password and stores it into the {@code Credentials} file to update the password
     * @param userID The user ID whose password needs to be updated.
     * @param newPlainTextPassword The new plain text password to be set.
     */
    public void changePassword(String userID, String newPlainTextPassword){
        String salt = generateSalt();
        String hashedPassword = hashPassword(newPlainTextPassword, salt);

        credentialsRepository.updateHashedPassword(userID,hashedPassword,salt);
    }

    /**
     * Finds the Security Question of a User based on their {@code HospitalID} and returns it
     * @param userID The user ID of the user whose security question is needed.
     * @return The security question of the user.
     */
    public String getSecurityQuestion(String userID){
        Credentials userData = credentialsRepository.readRecord(userID);
        return userData.getSecurityQuestion();
    }

    /**
     * Verifies a User's Security Question based on their {@code HospitalID} and input Answer
     * @param userID The user ID to verify the security question against.
     * @param inputAnswer The answer provided by the user for the security question.
     * @return True if the input answer matches the stored answer, otherwise false.
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
     * @param userID The user ID to check the remaining login attempts for.
     * @return The number of login attempts remaining.
     */
    public int getNumberOfTriesLeft(String userID){
        Credentials userData = credentialsRepository.readRecord(userID);
        return (maxLoginAttempts - userData.getLoginAttempts());
    }

    /**
     * Create a {@code Credential} in the {@code Credential} file
     * @param userID The user ID to create a new credential for.
     * @param plainTextPassword The plain text password for the new user.
     * @param securityQuestion The security question to be assigned to the user.
     * @param plainTextSecurityAnswer The plain text answer to the user's security question.
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
     * @param userID The user ID whose credentials should be deleted.
     */
    public void deleteRecord(String userID){
        credentialsRepository.deleteRecord(userID);
    }

    /**
     * Updates the {@code Credential}s of a User in the {@code Credential} file
     * @param userData The updated user data to be saved.
     */

    public void updateRecord(Credentials userData){
        credentialsRepository.updateRecord(userData);
    }

    /**
     * Searches for the {@code Credential} of a User from the {@code Credential} file based on their UserID
     * @param userID The user ID whose credentials should be retrieved.
     * @return The {@code Credentials} object containing the user's data.
     */
    public Credentials getRecord(String userID){
        return credentialsRepository.readRecord(userID);
    }

    /**
     * Locks the User Account by setting the number of attempts to -1
     * @param userID The user ID of the account to be locked.
     */
    public void lockAccount(String userID){
        Credentials temp = credentialsRepository.readRecord(userID);
        temp.lockAccount();
        credentialsRepository.updateRecord(temp);
    }

    /**
     * Unlocks the User Account by setting the number of attempts to 0
     * @param userID The user ID of the account to be unlocked.
     */
    public void unlockAccount(String userID){
        Credentials temp = credentialsRepository.readRecord(userID);
        temp.unlockAccount();
        credentialsRepository.updateRecord(temp);
    }

    /**
     * Method to check if a User's account is locked
     * @param userID The user ID to check if the account is locked.
     * @return True if the User's account is locked and False otherwise.
     */
    public boolean isAccountLocked(String userID){
        Credentials temp = credentialsRepository.readRecord(userID);
        return temp.isAccountLocked();
    }

    /**
     * Method to increment the number of login attempts. Locks Account if number of attempts exceed 3
     * @param userID The user ID to increment login attempts for.
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
     * @param userID The user ID to determine the domain of.
     * @return The domain of the user (Doctor, Patient, Pharmacist, or Administrator).
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
