package Entity.Repository;

import Entity.Enums.Department;
import Entity.Enums.Domain;
import Entity.Enums.Gender;
import Entity.User.Administrator;
import Entity.User.Doctor;
import Entity.User.Pharmacist;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The Repository that accesses and changes everything related to {@code HospitalStaff} in the {@code HospitalStaff} database
 */
public class HospitalStaffRepository implements IRepository<String,String,String[],String[]>{
    public static String path;

    /**
     * Constructor for the {@code HospitalStaffRepository} which initialises the path of which the {@code HospitalStaffRepository} reads from
     * @param path the file path to the database containing {@code HospitalStaff} records.
     */
    public HospitalStaffRepository(String path) {
    	HospitalStaffRepository.path = path;
    }

    /**
     * Updates the {@code HospitalStaff} file with the latest information of all the {@code HospitalStaff} by their roles
     * @param admList the list of {@code Administrator} objects to be added to the file.
     * @param docList the list of {@code Doctor} objects to be added to the file.
     * @param phList  the list of {@code Pharmacist} objects to be added to the file.
     */
    public static void updateHospitalStaffFile(List<Administrator> admList, List<Doctor> docList, List<Pharmacist> phList) {
        List<String[]> data = new ArrayList<>();
        
        String[] values = new String[5];
        values[0] = "Staff ID";
        values[1] = "Name";
        values[2] = "Role/Department(if Doctor)";
        values[3] = "Gender";
        values[4] = "Age";
        data.add(values);
        
        for(Administrator adm : admList) {
        	String[] row = new String[5];
        	row[0] = adm.getUserID();
            row[1] = adm.getName();
            row[2] = "Administrator";
            row[3] = adm.getGender().toString();
            row[4] = String.valueOf(adm.getAge());
            data.add(row);
        }
        
        for(Doctor doc : docList) {
        	String[] row = new String[5];
        	row[0] = doc.getUserID();
            row[1] = doc.getName();
            row[2] = doc.getDepartment().toString();
            row[3] = doc.getGender().toString();
            row[4] = String.valueOf(doc.getAge());
            data.add(row);
        }
        
        for(Pharmacist ph : phList) {
        	String[] row = new String[5];
        	row[0] = ph.getUserID();
            row[1] = ph.getName();
            row[2] = "Pharmacist";
            row[3] = ph.getGender().toString();
            row[4] = String.valueOf(ph.getAge());
            data.add(row);
        }
        updateFile(data);
    }

    /**
     * Updates the {@code HospitalStaff} file with the latest data from the {@code HospitalStaff}
     * @param data a list of {@code String[]} where each element represents a row of the file to be written.
     */
    private static void updateFile(List<String[]> data) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(path));
			for(String[] row : data) writer.write(String.join(",", row) + "\n");
			writer.close();
		} catch(IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}

    /**
     * Loads the data of all the {@code Doctors} in the {@code HospitalStaff}
     */
    public static void loadDoctorList() {
    	List<String[]> data = new ArrayList<>();

		//Read the CSV file
		try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
			String row;
			while ((row = reader.readLine()) != null) {
				String[] values = row.split(",");
				data.add(values);
			}
		} catch (IOException e) {
			//e.printStackTrace();
			System.out.println("File is not created yet!!");
		}
		//boolean headerRow = true;
		for(String[] row : data) {
			//if(headerRow) headerRow = false;
			if(row[0].charAt(0)=='D')
			{
				//String userID, String name, int role, Gender gender, age
                String userID = row[0];
                String name = row[1];
                Department department = Department.valueOf(row[2]);
                Gender gender= Gender.valueOf(row[3]);
                int age = Integer.parseInt(row[4]);

				Doctor doctor = new Doctor(userID,name,age,gender,department);

				//doctor.setDepartment(Department.valueOf(row[5]));
				Doctor.getDoctorList().add(doctor);
			}
		}
    }

    /**
     * Loads the data of all the {@code Pharmacists} in the {@code HospitalStaff}
     */
    public static void loadPharmacistList() {
    	List<String[]> data = new ArrayList<>();

		//Read the CSV file
		try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
			String row;
			while ((row = reader.readLine()) != null) {
				String[] values = row.split(",");
				data.add(values);
			}
		} catch (IOException e) {
			//e.printStackTrace();
			System.out.println("File is not created yet!!");
		}
		boolean headerRow = true;
		for(String[] row : data) {
			if(headerRow) headerRow = false;
			else if(row[0].charAt(0)=='R')
			{
				//String userID, String name, int age, Gender gender, Domain domain
				Pharmacist pharmacist = new Pharmacist(row[0], row[1], Integer.valueOf(row[4]), Gender.valueOf(row[3]), Domain.PHARMACIST);
				Pharmacist.getPharmacistList().add(pharmacist);
			}
		}
    }

    /**
     * Loads the data of all the {@code Administrators} in the {@code HospitalStaff}
     */
    public static void loadAdministrator() {
    	List<String[]> data = new ArrayList<>();

		//Read the CSV file
		try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
			String row;
			while ((row = reader.readLine()) != null) {
				String[] values = row.split(",");
				data.add(values);
			}
		} catch (IOException e) {
			//e.printStackTrace();
			System.out.println("File is not created yet!!");
		}
		boolean headerRow = true;
		for(String[] row : data) {
			if(headerRow) headerRow = false;
			else if(row[0].charAt(0)=='A')
			{
				//String UserID,String name,int age,Gender gender
				Administrator administrator = new Administrator(row[0], row[1], Integer.valueOf(row[4]), Gender.valueOf(row[3]));
				Administrator.getAdministratorList().add(administrator);
			}
		}
    }

    /**
     * Creates a record and takes in (Staff ID,Name,Role,Gender,Age)
     * @param attributes an array of objects representing the attributes of the hospital staff:
     *                   <ul>
     *                     <li>{@code attributes[0]} - the staff ID ({@code String}).</li>
     *                     <li>{@code attributes[1]} - the name ({@code String}).</li>
     *                     <li>{@code attributes[2]} - the role or department ({@code Department} or {@code Domain}).</li>
     *                     <li>{@code attributes[3]} - the gender ({@code Gender}).</li>
     *                     <li>{@code attributes[4]} - the age ({@code int}).</li>
     *                   </ul>
     */
    @Override
    public void createRecord(Object... attributes) {
        if(attributes.length != 5) {
            System.out.println("Error: Invalid data types for creating record.");
            return;
        }
        String staffID = (String) attributes[0];
        String name = (String) attributes[1];

        Object role = attributes[2];
        String roleString;
        if (role instanceof Department) {
            roleString = ((Department) role).toString();
        } else if (role instanceof Domain) {
            roleString = ((Domain) role).toString();
        } else {
            System.out.println("Error: Unsupported type for role attribute.");
            return;
        }

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

    /**
     * Returns a record based on StaffID String[]
     * @param staffID the unique identifier of the staff member whose record is being searched.
     * @return a {@code String[]} containing the record data, or {@code null} if the record is not found.
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

    /**
     * Updates a record based on input (Staff ID,Name,Role,Gender,Age)
     * @param record a {@code String[]} representing the updated record:
     *               <ul>
     *                 <li>{@code record[0]} - the staff ID ({@code String}).</li>
     *                 <li>{@code record[1]} - the name ({@code String}).</li>
     *                 <li>{@code record[2]} - the role or department ({@code String}).</li>
     *                 <li>{@code record[3]} - the gender ({@code String}).</li>
     *                 <li>{@code record[4]} - the age ({@code String}).</li>
     *               </ul>
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

    /**
     * Deletes a record based on input StaffID
     * @param staffID the unique identifier of the staff member whose record is to be deleted.
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
