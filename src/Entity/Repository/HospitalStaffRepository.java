package Entity.Repository;

import Entity.Enums.Department;
import Entity.Enums.Gender;

import java.io.*;
import java.util.ArrayList;

public class HospitalStaffRepository implements IRepository<String,String,String[],String[]>{
    public final String path;

    public HospitalStaffRepository(String path) {
        this.path = path;
    }

    /*
    Creates a record and takes in (Staff ID,Name,Role,Gender,Age)
    */
    @Override
    public void createRecord(Object... attributes) {
        if(attributes.length != 5) {
            System.out.println("Error: Invalid data types for creating record.");
            return;
        }
        String staffID = (String) attributes[0];
        String name = (String) attributes[1];
        Department role = (Department) attributes[2];
        Gender gender = (Gender) attributes[3];
        int age = (int) attributes[4];

        String record = staffID + "," + name + "," + role + "," + gender + "," + age;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path, true))) {
            writer.write(record);
            writer.newLine(); // Adds a newline to separate records
            System.out.println("Record added successfully!");

        } catch (IOException e) {
            System.out.println("Error while writing to the credentials file.");
            e.printStackTrace();
        }
    }

    /*
    Returns a record based on StaffID String[]
    */
    @Override
    public String[] readRecord(String staffID) {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] row = line.split(",");
                if (row.length > 0 && row[0].equals(staffID)) {
                    return row;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading the Credentials File.");
            e.printStackTrace();
        }
        // Return null if staffID is not found
        return null;
    }

    /*
    Updates a record based on input (Staff ID,Name,Role,Gender,Age)
    */
    @Override
    public void updateRecord(String[] record) {
        ArrayList<String[]> temp = new ArrayList<>();
        boolean isUpdated = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");

                String fileStaffID = data[0];

                if (fileStaffID.equalsIgnoreCase(record[0])) {
                    temp.add(record);
                    isUpdated = true; // Add the updated record instead
                } else {
                    temp.add(data);
                }
            }
        } catch (Exception e) {
            System.out.println("Error accessing MedicationInventory file !!");
            e.printStackTrace();
        }

        // Once the whole file is read, we copy the file back
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            writer.write("Name,CurrentStock,LowStockAlert,RequestAmount\n");
            writer.newLine();

            for (String[] row : temp) {
                writer.write(row[0] + "," + row[1] + "," + row[2] + "," + row[3] + "," + row[4]);
                writer.newLine();
            }
            if (!isUpdated) {
                System.out.println("Error, user not found, file is unchanged !");
            }
        } catch (IOException e) {
            System.out.println("Error writing to HospitalStaff file!");
            e.printStackTrace();
        }
    }

    /*
    Deletes a record based on input StaffID
    */
    @Override
    public void deleteRecord(String staffID) {
        ArrayList<String[]> temp = new ArrayList<>();
        boolean isDeleted = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");

                String fileUserID = data[0];

                // Ignore the record if it matches the name
                if (fileUserID.equalsIgnoreCase(staffID)) {
                    isDeleted = true; // Do not add the record
                } else {
                    temp.add(data); // Add existing record unchanged
                }
            }
        } catch (Exception e) {
            System.out.println("Error accessing HospitalStaff file !!");
            e.printStackTrace();
        }

        // Once the whole file is read, we copy the file back
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            for (String[] row : temp) {
                writer.write(row[0] + "," + row[1] + "," + row[2] + "," + row[3] + "," + row[4]);
                writer.newLine();
            }
            if (!isDeleted) {
                System.out.println("Error, user not found, file is unchanged !");
            }
        } catch (IOException e) {
            System.out.println("Error writing to HospitalStaff file!");
            e.printStackTrace();
        }
    }
}
