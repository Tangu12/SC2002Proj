package Entity.Repository;

//import Services.CredentialsService;

import Entity.Medicine;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CredentialsRepository implements IRepository <String,String,String[],String[]> {
    public final String path;

    public CredentialsRepository(String path) {
        this.path = path;
    }

    private List<String[]> credentialsDatabase = new ArrayList<>();

    /*
    Returns the row corresponding to the UserID given
     */
    @Override
    public String[] readRecord(String userID) {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] row = line.split(",");
                if (row.length > 0 && row[0].equals(userID)) {
                    return row;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading the Credentials File.");
            e.printStackTrace();
        }

        // Return null if userID is not found
        return null;
    }

    /*
    Deletes the row corresponding to the UserID given. Looks through the credentials file and adds the rows into an array of strings.
    Then if the current row matches the userID given, it skips past it. When the end of the file is reached, a writer is used to write
    all the entries in the array of string back into the file
     */
    @Override
    public void deleteRecord(String userID) {
        boolean isDeleted = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");

                String fileUserID = data[0];

                // Ignore the record if it matches the name
                if (fileUserID.equalsIgnoreCase(userID)) {
                    isDeleted = true; // Do not add the record
                } else {
                    credentialsDatabase.add(data); // Add existing record unchanged
                }
            }
        } catch (Exception e) {
            System.out.println("Error accessing MedicationInventory file !!");
            e.printStackTrace();
        }

        // Once the whole file is read, we copy the file back
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            for (String[] credential : credentialsDatabase) {
                writer.write(credential[0] + "," + credential[1] + "," + credential[2] + "," + credential[3] + "," + credential[4] + "," + credential[5]);
                writer.newLine();
            }
            if (!isDeleted) {
                System.out.println("Error, user not found, file is unchanged !");
            }
        } catch (IOException e) {
            System.out.println("Error writing to Credentials file!");
            e.printStackTrace();
        }
    }

    /*
    Adds a new credential to the bottom of the credentials file. Does not check if credentials already exists
     */
    @Override
    public void createRecord(Object... attributes) {
        if (attributes.length == 6) {
            String userID = (String) attributes[0];
            String hashedPassword = (String) attributes[1];
            String salt = (String) attributes[2];
            String realPassword = (String) attributes[3];
            String securityQuestion = (String) attributes[4];
            String answerToSecurityQuestion = (String) attributes[5];

            String record = userID + "," + hashedPassword + "," + salt + "," + realPassword + "," + securityQuestion + "," + answerToSecurityQuestion;

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(path, true))) {
                writer.write(record);
                writer.newLine(); // Adds a newline to separate records
                System.out.println("Record added successfully!");

            } catch (IOException e) {
                System.out.println("Error while writing to the credentials file.");
                e.printStackTrace();
            }
        } else {
            System.out.println("Error: Invalid data types for creating record.");
        }
    }

    /*
    Updates the row corresponding to the UserID with the attributes given
     */
    @Override
    public void updateRecord(String[] record) {
        boolean isUpdated = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");

                String fileUserID = data[0];

                if (fileUserID.equalsIgnoreCase(record[0])) {
                    credentialsDatabase.add(record);
                    isUpdated = true; // Add the updated record instead
                } else {
                    credentialsDatabase.add(data);
                }
            }
        } catch (Exception e) {
            System.out.println("Error accessing MedicationInventory file !!");
            e.printStackTrace();
        }

        // Once the whole file is read, we copy the file back
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            for (String[] credential : credentialsDatabase) {
                writer.write(credential[0] + "," + credential[1] + "," + credential[2] + "," + credential[3] + "," + credential[4] + "," + credential[5]);
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

    /*
    public void createRecord(object... Attributes){

        if (attributes.length == 2) {
            if (attributes[0] instanceof String && attributes[1] instanceof String) {
                String username = (String) attributes[0];
                String password = (String) attributes[1];
                // Add the record to the database (a list for this example)
                credentialsDatabase.add(new String[]{username, password});
                System.out.println("Credential added: " + username);
            } else {
                System.out.println("Error: Invalid data types for creating record.");
            }
        } else if (attributes.length == 3) {
            // Expect (username, password, role)
            if (attributes[0] instanceof String && attributes[1] instanceof String && attributes[2] instanceof String) {
                String username = (String) attributes[0];
                String password = (String) attributes[1];
                String role = (String) attributes[2];
                // Add the record with the role to the database
                credentialsDatabase.add(new String[]{username, password, role});
                System.out.println("Credential added: " + username + ", Role: " + role);
            } else {
                System.out.println("Error: Invalid data types for creating record.");
            }
        } else {
            System.out.println("Error: Invalid number of arguments for creating record.");
        }
    }
    //@Override
    public void readRecord(Object... attributes) {
        if (attributes.length == 1 && attributes[0] instanceof String) {
            // Expect (username) to find the credential
            String username = (String) attributes[0];
            for (String[] credential : credentialsDatabase) {
                if (credential[0].equals(username)) {
                    System.out.println("Found credential: " + username + ", Password: " + credential[1]);
                    return;
                }
            }
            System.out.println("Credential with username " + username + " not found.");
        } else {
            System.out.println("Error: Invalid argument for reading record.");
        }
    }

    //@Override
    public void updateRecord(Object... attributes) {
        if (attributes.length == 2) {
            // Expect (username, new password)
            if (attributes[0] instanceof String && attributes[1] instanceof String) {
                String username = (String) attributes[0];
                String newPassword = (String) attributes[1];
                for (String[] credential : credentialsDatabase) {
                    if (credential[0].equals(username)) {
                        credential[1] = newPassword;  // Update the password
                        System.out.println("Password updated for username: " + username);
                        return;
                    }
                }
                System.out.println("Credential with username " + username + " not found.");
            } else {
                System.out.println("Error: Invalid data types for updating record.");
            }
        } else {
            System.out.println("Error: Invalid number of arguments for updating record.");
        }
    }*/

}

