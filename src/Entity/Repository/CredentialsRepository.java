package Entity.Repository;

import Entity.Credentials;
import Entity.Enums.Domain;
import Entity.User.IUser;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


/**
 * The Repository that accesses and changes everything related to {@code Credentials} in the {@code Credentials} database
 */
public class CredentialsRepository implements IRepository <String,String, Credentials,Credentials> {
    public final String path;

    /**
     * Constructor for the {@code CredentialsRepository} which initialises the path of which the {@code CredentialsRepository} reads from
     * @param path
     */
    public CredentialsRepository(String path) {
        this.path = path;
    }

    /**
     * Searches database for a specific {@code Credentials}
     * @param userID
     * @return The {@code Credentials} corresponding to the UserID given
     */
    @Override
    public Credentials readRecord(String userID) {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] row = line.split(",");

                if (row.length > 0 && row[0].equals(userID)) {
                    return new Credentials(
                            row[0], // userID
                            row[1], // hashedPassword
                            row[2], // salt
                            row[3], // securityQuestion
                            row[4],  // hashedSecurityAnswer
                            Integer.parseInt(row[5])  // loginAttempts
                    );
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading the Credentials file.");
            e.printStackTrace();
        }

        // Return null if userID is not found
        return null;
    }


    /**
     * Looks through the {@code Credentials} file and deletes the row corresponding to the UserID given.
     * @param userID
     */
    @Override
    public void deleteRecord(String userID) {
        List<Credentials> credentialsDatabase = new ArrayList<>();
        boolean isDeleted = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");

                Credentials credentials = new Credentials(
                        data[0],  // userID
                        data[1],  // hashedPassword
                        data[2],  // salt
                        data[3],  // securityQuestion
                        data[4],   // hashedSecurityAnswer
                        Integer.parseInt(data[5])
                );

                // Ignore the record if it matches the name
                if (credentials.getUserID().equalsIgnoreCase(userID)) {
                    isDeleted = true; // Do not add the record
                } else {
                    credentialsDatabase.add(credentials); // Add existing record unchanged
                }
            }
        } catch (Exception e) {
            System.out.println("Error accessing the Credentials file !");
            e.printStackTrace();
        }

        // Once the whole file is read, we copy the file back
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            for (Credentials credential : credentialsDatabase) {
                writer.write(credential.getUserID() + "," +
                        credential.getHashedPassword() + "," +
                        credential.getSalt() + "," +
                        credential.getSecurityQuestion() + "," +
                        credential.getHashedSecurityAnswer() + "," +
                        credential.getLoginAttempts());
                writer.newLine();
            }
            if (!isDeleted) {
                System.out.println("Error, user not found, file is unchanged !");
            }
            else{
                System.out.println("User deleted from credentials file successfully!");
            }
        } catch (IOException e) {
            System.out.println("Error writing to Credentials file!");
            e.printStackTrace();
        }
    }

    /**
     * Adds a new {@code Credentials} to the bottom of the {@code Credentials}s file. Does not check if {@code Credentials} already exists
     * @param attributes
     */
    @Override
    public void createRecord(Object... attributes) {
        if (attributes.length == 1 && attributes[0] instanceof Credentials) {
            Credentials credentials = (Credentials) attributes[0];

            String userID = credentials.getUserID();
            String hashedPassword = credentials.getHashedPassword();
            String salt = credentials.getSalt();
            String securityQuestion = credentials.getSecurityQuestion();
            String hashedSecurityAnswer = credentials.getHashedSecurityAnswer();
            int loginAttempts = credentials.getLoginAttempts();

            String record = userID + "," + hashedPassword + "," + salt + "," + securityQuestion + "," + hashedSecurityAnswer+ "," + loginAttempts;

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(path, true))) {
                writer.write(record);
                writer.newLine();
                System.out.println("User added to credentials file successfully!");
            } catch (IOException e) {
                System.out.println("Error while writing to the credentials file.");
                e.printStackTrace();
            }
        } else {
            System.out.println("Error: Invalid data type or missing Credentials object for creating record.");
        }
    }

    /**
     * Updates the row with the corresponding {@code UserID} with the attributes given
     * @param record
     */
    @Override
    public void updateRecord(Credentials record) {
        List<Credentials> credentialsDatabase = new ArrayList<>();
        boolean isUpdated = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");

                Credentials credentials = new Credentials(
                        data[0],  // userID
                        data[1],  // hashedPassword
                        data[2],  // salt
                        data[3],  // securityQuestion
                        data[4],   // hashedSecurityAnswer
                        Integer.parseInt(data[5])
                );

                if (credentials.getUserID().equalsIgnoreCase(record.getUserID())) {
                    credentialsDatabase.add(record);
                    isUpdated = true; // Add the updated record instead
                } else {
                    credentialsDatabase.add(credentials);
                }
            }
        } catch (Exception e) {
            System.out.println("Error accessing MedicationInventory file !!");
            e.printStackTrace();
        }

        // Once the whole file is read, we copy the file back
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            for (Credentials credential : credentialsDatabase) {
                writer.write(credential.getUserID() + "," +
                        credential.getHashedPassword() + "," +
                        credential.getSalt() + "," +
                        credential.getSecurityQuestion() + "," +
                        credential.getHashedSecurityAnswer() + "," +
                        credential.getLoginAttempts());
                writer.newLine();
            }
            if (!isUpdated) {
                System.out.println("Error, user not found, file is unchanged !");
            }
        } catch (IOException e) {
            System.out.println("Error writing to Credentials file!");
            e.printStackTrace();
        }
    }


    /**
     * More specific update operation than updateRecord, which updates all. This only updates the hashedPassword and Salt
     * @param userID
     * @param hashedPassword
     * @param salt
     */
    public void updateHashedPassword(String userID, String hashedPassword, String salt) {
        List<Credentials> credentialsDatabase = new ArrayList<>();

        Credentials userRecord = readRecord(userID);

        if (userRecord != null) {
            // Update only the hashedPassword and salt fields in the record
            userRecord.setHashedPassword(hashedPassword);
            userRecord.setSalt(salt);

            // Use updateRecord to save the modified record back to the file
            updateRecord(userRecord);

            System.out.println("Password and salt updated successfully for user: " + userID);
        } else {
            System.out.println("Error! User not found, Credentials File unchanged");
        }
    }

    public int countUsersByType(Domain userDomain){
        int count = 0;
        try {
            String line;
            BufferedReader br = new BufferedReader(new FileReader(path));

            switch (userDomain) {
                case PATIENT:
                    while ((line = br.readLine()) != null) {
                        String[] credentials = line.split(",");
                        String username = credentials[0].trim();
                        if (username.startsWith(IUser.PATIENT_PREFIX)) {
                            count++;
                        }
                    }
                    break;

                case PHARMACIST:
                    while ((line = br.readLine()) != null) {
                        String[] credentials = line.split(",");
                        String username = credentials[0].trim();
                        if (username.startsWith(IUser.PHARMACIST_PREFIX)) {
                            count++;
                        }
                    }
                    break;

                case DOCTOR:
                    while ((line = br.readLine()) != null) {
                        String[] credentials = line.split(",");
                        String username = credentials[0].trim();
                        if (username.startsWith(IUser.DOCTOR_PREFIX)) {
                            count++;
                        }
                    }
                    break;

                case ADMINISTRATOR:
                    while ((line = br.readLine()) != null) {
                        String[] credentials = line.split(",");
                        String username = credentials[0].trim();
                        if (username.startsWith(IUser.ADMIN_PREFIX)) {
                            count++;
                        }
                    }
                    break;
            }

            br.close();
        }
        catch (IOException e) {
            System.out.println("Error reading the Credentials file.");
            e.printStackTrace();
        }
        return count;
    }

    public ArrayList<String> getAllUserIDs() {
        ArrayList<String> users = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] row = line.split(",");
                if (row.length > 0) {
                    users.add(row[0]);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading the Credentials file.");
            e.printStackTrace();
        }

        return users;
    }
}

