package Entity.Repository;

//import Services.CredentialsService;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CredentialsRepository implements IRepository {
    public final String path;

    public CredentialsRepository(String path) {
        this.path = path;
    }

    private List<String[]> credentialsDatabase = new ArrayList<>();

    @Override
    public void createRecord(Object... attributes) {
        if (attributes.length == 2) {
            // Expect (username, password)
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

    @Override
    public Object readRecord(Object identifier) {
        return null;
    }

    @Override
    public void updateRecord(Object record) {

    }

    @Override
    public void deleteRecord(Object record) {

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
    }

    //@Override
    public void deleteRecord(Object... attributes) {
        if (attributes.length == 1 && attributes[0] instanceof String) {
            // Expect (username) to delete the credential
            String username = (String) attributes[0];
            boolean found = false;
            for (String[] credential : credentialsDatabase) {
                if (credential[0].equals(username)) {
                    credentialsDatabase.remove(credential);
                    found = true;
                    System.out.println("Credential deleted for username: " + username);
                    break;
                }
            }
            if (!found) {
                System.out.println("Credential with username " + username + " not found.");
            }
        } else {
            System.out.println("Error: Invalid argument for deleting record.");
        }
    }
}

